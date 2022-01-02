package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class AdminReviewController {

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/product-reviews/{id}")
    public String showProductReviews(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        Product product = productService.getProductById(id);
        model.addAttribute("productName",product.getName());
        model.addAttribute("reviews",product.getReviews());
        return "admin/ProductReviews";
    }
}
