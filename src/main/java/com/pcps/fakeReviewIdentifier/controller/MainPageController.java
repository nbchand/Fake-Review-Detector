package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainPageController {
    @Autowired
    private ProductService productService;

    @GetMapping("/item-list")
    public String displayItemList(Model model, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("user")){
            return "redirect:/";
        }
        model.addAttribute("products",productService.getAllProducts());
        return "BrowseItem";
    }
}
