package com.pcps.fakeReviewIdentifier.repository;

import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.Review;
import com.pcps.fakeReviewIdentifier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Integer> {
    public Review findReviewByProductAndUser(Product product, User user);
    public List<Review> findReviewByProductAndIp(Product product, String ip);
}
