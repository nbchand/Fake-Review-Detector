package com.pcps.fakeReviewIdentifier.repository;

import com.pcps.fakeReviewIdentifier.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {
    public Admin findByEmail(String email);
}
