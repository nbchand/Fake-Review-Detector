package com.pcps.fakeReviewIdentifier.service;

import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.Review;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    public void saveReview(Review review){
        reviewRepo.save(review);
    }

    public Review getUserReviewOnProduct(Product product, User user){
        return reviewRepo.findReviewByProductAndUser(product,user);
    }

    public List<Review> getReviewByIp(String ip){
        return reviewRepo.findReviewByIp(ip);
    }
}
