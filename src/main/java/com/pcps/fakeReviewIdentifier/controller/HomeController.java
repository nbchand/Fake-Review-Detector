package com.pcps.fakeReviewIdentifier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping("/")
    public String displayLoginPage(){
        return "Login";
    }
    @GetMapping("/signup")
    public String displaySignupPage(){
        return "Signup";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }
    @GetMapping("/admin")
    public String displayAdminLogin(){
        return "admin/Login";
    }
}
