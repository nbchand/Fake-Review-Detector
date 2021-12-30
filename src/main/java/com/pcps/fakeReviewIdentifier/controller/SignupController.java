package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.functionality.PatternMatcher;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.service.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

    @Autowired
    private UserSignupService userSignupService;


    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        if(userSignupService.isEmailTaken(user.getEmail())){
            redirectAttributes.addFlashAttribute("signupmessage","Account already exists");
            return "redirect:/signup";
        }
        if(!PatternMatcher.checkEmailPattern(user.getEmail())){
            redirectAttributes.addFlashAttribute("signupmessage","Invalid email");
            return "redirect:/signup";
        }
        if(!PatternMatcher.checkNamePattern(user.getFirstname())){
            redirectAttributes.addFlashAttribute("signupmessage","Firstname invalid");
            return "redirect:/signup";
        }
        if(!PatternMatcher.checkNamePattern(user.getLastname())){
            redirectAttributes.addFlashAttribute("signupmessage","Lastname invalid");
            return "redirect:/signup";
        }
        if(!PatternMatcher.checkPasswordPattern(user.getPassword())){
            redirectAttributes.addFlashAttribute("signupmessage","Password invalid");
            return "redirect:/signup";
        }

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userSignupService.saveUser(user);

        redirectAttributes.addFlashAttribute("loginmessage","Signup successful");
        return "redirect:/";
    }
}
