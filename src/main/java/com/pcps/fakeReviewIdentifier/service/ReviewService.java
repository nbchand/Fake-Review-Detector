package com.pcps.fakeReviewIdentifier.service;

import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.Review;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.repository.ProductRepo;
import com.pcps.fakeReviewIdentifier.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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
        Product product = review.getProduct();

        float f = ((product.getRatings()*product.getTotalReviews())-review.getRating())/(product.getTotalReviews()-1);
        DecimalFormat df = new DecimalFormat("#.#");
        float rating = Float.valueOf(df.format(f));

        if(Float.isNaN(rating)){
            rating=0.0f;
        }

        product.setRatings(rating);
        product.setTotalReviews(product.getTotalReviews()-1);
        productRepo.save(product);

        reviewRepo.delete(review);
    }
}
