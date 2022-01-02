package com.pcps.fakeReviewIdentifier.service;

import com.pcps.fakeReviewIdentifier.functionality.ReviewHelper;
import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.Review;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.repository.ProductRepo;
import com.pcps.fakeReviewIdentifier.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private ProductRepo productRepo;

    public void saveReview(Review review){
        reviewRepo.save(review);
    }

    public Review getUserReviewOnProduct(Product product, User user){
        return reviewRepo.findReviewByProductAndUser(product,user);
    }

    public List<Review> getReviewByProductAndIp(Product product, String ip){
        return reviewRepo.findReviewByProductAndIp(product,ip);
    }

    public Review getReviewById(int id){
        return reviewRepo.getById(id);
    }

    public void deleteReview(Review review){

        int productId = review.getProduct().getId();
        reviewRepo.delete(review);
        Product product = productRepo.getById(productId);
        product.setTotalReviews(product.getTotalReviews()-1);
        product.setRatings(ReviewHelper.calculateAvgRating(product.getReviews()));
        productRepo.save(product);
    }
}
