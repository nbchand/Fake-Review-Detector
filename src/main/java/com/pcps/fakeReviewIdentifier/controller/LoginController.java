package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.pojo.UserPojo;
import com.pcps.fakeReviewIdentifier.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserPojo userPojo, RedirectAttributes redirectAttributes, HttpSession session){
        User user = userLoginService.getUserByEmail(userPojo.getEmail());
        if(user==null){
            redirectAttributes.addFlashAttribute("loginmessage","No user found");
            return "redirect:/";
        }
        if(!DigestUtils.md5DigestAsHex(userPojo.getPassword().getBytes()).equals(user.getPassword())){
            redirectAttributes.addFlashAttribute("loginmessage","Incorrect password");
            return "redirect:/";
        }

        session.setAttribute("userId",user.getId());
        session.setAttribute("type","user");
        session.setMaxInactiveInterval(1000);
        return "redirect:/item-list";
    }
}
