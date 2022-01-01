package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.service.ProductService;
import com.pcps.fakeReviewIdentifier.service.ReviewService;
import com.pcps.fakeReviewIdentifier.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class ProductPageController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/display-product/{id}")
    public String displayProduct(@PathVariable("id") int id, Model model, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("user")){
            return "redirect:/";
        }
        User user = userLoginService.getUserById((int)session.getAttribute("userId"));
        Product product = productService.getProductById(id);

        if(!product.getUsers().contains(user)){
            model.addAttribute("var1",true);
        }
        else if(reviewService.getUserReviewOnProduct(product,user)!=null){
            model.addAttribute("var2",true);
        }
        else if(!product.getReviews().isEmpty()) {
            model.addAttribute("reviews",product.getReviews());
        }
        model.addAttribute("product",product);
        return "ProductDetail";
    }
}
