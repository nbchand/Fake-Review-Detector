package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.exception.StorageException;
import com.pcps.fakeReviewIdentifier.functionality.ImageHelper;
import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.pojo.ProductPojo;
import com.pcps.fakeReviewIdentifier.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserSignupService userSignupService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UploadService uploadService;

    @PutMapping("/purchase-product/{id}")
    @ResponseBody
    public String purchaseProduct(@PathVariable int id, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("user")){
            return "redirect:/";
        }
        User user = userLoginService.getUserById((int)session.getAttribute("userId"));
        Product product = productService.getProductById(id);
        product.setTotalPurchases(product.getTotalPurchases()+1);
        if(!product.getUsers().contains(user)){
            List<Product> products = user.getProducts();
            products.add(product);
            user.setProducts(products);
            userSignupService.saveUser(user);
        }
        productService.saveProduct(product);
        return "success";
    }

    @DeleteMapping("/product/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable int id, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "denied";
        }
        Product product = productService.getProductById(id);
        reviewService.deleteMultipleReviews(product.getReviews());
        try{
            productService.deleteProduct(product);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";
    }

    @PostMapping("/edit-product/{id}")
    public String editProduct(@PathVariable int id, @RequestParam("image") MultipartFile multipartFile, @ModelAttribute ProductPojo productPojo, RedirectAttributes redirectAttributes, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        Product product = productService.getProductById(id);

        if(productPojo.getName().equals("")){
            redirectAttributes.addFlashAttribute("msg","Provide the product name");
            return "redirect:/edit-product/"+product.getId();
        }
        if(productPojo.getCategory().equals("")){
            redirectAttributes.addFlashAttribute("msg","Provide the product category");
            return "redirect:/edit-product/"+product.getId();
        }

        if(productPojo.getDescription().equals("")){
            redirectAttributes.addFlashAttribute("msg","Provide the product description");
            return "redirect:/edit-product/"+product.getId();
        }

        product.setName(productPojo.getName());
        product.setPrice(productPojo.getPrice());
        product.setCategory(productPojo.getCategory());
        product.setDescription(productPojo.getDescription());

        if(!multipartFile.isEmpty()){

            try{
                String imgLocation = uploadService.store(multipartFile, UUID.randomUUID().toString());

                try{
                    ImageHelper.deleteImage(product.getImage());
                    product.setImage(imgLocation);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            catch(StorageException e){
                redirectAttributes.addFlashAttribute("msg2",e.getMessage());
            }

        }

        productService.saveProduct(product);

        redirectAttributes.addFlashAttribute("msg","Product edited successfully");
        return "redirect:/admin-page";
    }
}
