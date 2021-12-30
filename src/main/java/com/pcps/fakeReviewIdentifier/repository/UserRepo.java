package com.pcps.fakeReviewIdentifier.repository;

import com.pcps.fakeReviewIdentifier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
}
