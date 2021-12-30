package com.pcps.fakeReviewIdentifier.service;

import com.pcps.fakeReviewIdentifier.model.Admin;
import com.pcps.fakeReviewIdentifier.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginService {
    @Autowired
    private AdminRepo adminRepo;

    public Admin getAdminByEmail(String email){
        return adminRepo.findByEmail(email);
    }
}
