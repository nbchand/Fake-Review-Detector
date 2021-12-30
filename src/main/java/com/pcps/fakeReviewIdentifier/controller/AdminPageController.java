package com.pcps.fakeReviewIdentifier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AdminPageController {

    @GetMapping("/admin-page")
    public String displayAdminPage(HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        return "admin/MainPage";
    }
}
