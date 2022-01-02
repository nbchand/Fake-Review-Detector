package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.service.UserLoginService;
import com.pcps.fakeReviewIdentifier.service.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserSignupService userSignupService;

    @GetMapping("/user-list")
    public String listUsers(Model model, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        model.addAttribute("users",userLoginService.getAllUsers());
        return "admin/TotalUsers";
    }

    @PutMapping("/user/change-status/{id}")
    @ResponseBody
    public String changeStatus(@PathVariable int id, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("admin")){
            return "redirect:/";
        }
        User user = userLoginService.getUserById(id);
        if(user.isStatus()==false){
            user.setStatus(true);
        }
        else{
            user.setStatus(false);
        }
        userSignupService.saveUser(user);
        return "success";
    }
}
