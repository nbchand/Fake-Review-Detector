package com.pcps.fakeReviewIdentifier.service;

import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginService {
    @Autowired
    private UserRepo userRepo;

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public User getUserById(int id){
        return userRepo.getById(id);
    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
}
