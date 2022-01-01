package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.service.ProductService;
import com.pcps.fakeReviewIdentifier.service.UserLoginService;
import com.pcps.fakeReviewIdentifier.service.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserSignupService userSignupService;

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
}
