package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.exception.StorageException;
import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.pojo.ProductPojo;
import com.pcps.fakeReviewIdentifier.service.ProductService;
import com.pcps.fakeReviewIdentifier.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UploadService uploadService;

    @GetMapping("/admin-page")
    public String displayAdminPage(Model model, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        model.addAttribute("products",productService.getAllProducts());
        return "admin/AdminProduct";
    }

    @GetMapping("/admin/add-product")
    public String showProductForm(HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        return "admin/ProductForm";
    }

    @PostMapping("/create-product")
    public String registerProduct(@RequestParam("image") MultipartFile multipartFile, @ModelAttribute ProductPojo productPojo, RedirectAttributes redirectAttributes, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }

        Product product = new Product();

        if(productPojo.getName().equals("")){
            redirectAttributes.addFlashAttribute("productMessage","Provide the product name");
            return "redirect:/admin/add-product";
        }
        if(productPojo.getCategory().equals("")){
            redirectAttributes.addFlashAttribute("productMessage","Provide the product category");
            return "redirect:/admin/add-product";
        }

        if(productPojo.getDescription().equals("")){
            redirectAttributes.addFlashAttribute("productMessage","Provide the product description");
            return "redirect:/admin/add-product";
        }

        product.setName(productPojo.getName());
        product.setPrice(productPojo.getPrice());
        product.setCategory(productPojo.getCategory());
        product.setDescription(productPojo.getDescription());

        try{
            String imgLocation = uploadService.store(multipartFile, UUID.randomUUID().toString());
            product.setImage(imgLocation);
        }
        catch(StorageException e){
            product.setImage("/image/dummy.jpg");
            redirectAttributes.addFlashAttribute("msg2",e.getMessage());
        }
        productService.saveProduct(product);

        redirectAttributes.addFlashAttribute("msg","Product added successfully");
        return "redirect:/admin-page";
    }

    @GetMapping("/edit-product/{id}")
    public String showEditForm(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        model.addAttribute("product",productService.getProductById(id));
        return "admin/EditProduct";
    }
}
