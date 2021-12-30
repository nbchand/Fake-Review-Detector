package com.pcps.fakeReviewIdentifier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainPageController {
    @GetMapping("/item-list")
    public String displayItemList(HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("user")){
            return "redirect:/";
        }
        return "ItemList";
    }
}
