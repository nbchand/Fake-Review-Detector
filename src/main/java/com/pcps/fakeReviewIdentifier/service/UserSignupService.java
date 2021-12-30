package com.pcps.fakeReviewIdentifier.service;

import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSignupService {
    @Autowired
    private UserRepo userRepo;

    public boolean isEmailTaken(String email){
        return userRepo.existsByEmail(email);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }
}
