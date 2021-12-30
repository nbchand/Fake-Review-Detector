package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.model.Admin;
import com.pcps.fakeReviewIdentifier.pojo.UserPojo;
import com.pcps.fakeReviewIdentifier.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping("/admin/login")
    public String loginUser(@ModelAttribute UserPojo userPojo, RedirectAttributes redirectAttributes, HttpSession session){
        Admin admin = adminLoginService.getAdminByEmail(userPojo.getEmail());
        if(admin==null){
            redirectAttributes.addFlashAttribute("loginmessage","No user found");
            return "redirect:/admin";
        }
        if(!userPojo.getPassword().equals(admin.getPassword())){
            redirectAttributes.addFlashAttribute("loginmessage","Incorrect password");
            return "redirect:/admin";
        }

        session.setAttribute("userId",admin.getId());
        session.setAttribute("type","admin");
        session.setMaxInactiveInterval(1000);
        return "redirect:/admin-page";
    }
}
