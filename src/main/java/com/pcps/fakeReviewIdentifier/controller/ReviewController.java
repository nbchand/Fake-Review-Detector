package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.functionality.PatternMatcher;
import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.Review;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.pojo.ReviewPojo;
import com.pcps.fakeReviewIdentifier.service.ProductService;
import com.pcps.fakeReviewIdentifier.service.ReviewService;
import com.pcps.fakeReviewIdentifier.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/create-review/{id}")
    @ResponseBody
    public String createReview(@PathVariable int id, @RequestBody ReviewPojo reviewPojo, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("user")){
            return "redirect:/";
        }
        if(reviewPojo.getContent().equals("")){
            return "Review can't be empty";
        }
        if(reviewPojo.getRating()<1&&reviewPojo.getRating()>5){
            return "Invalid rating";
        }
        if(!PatternMatcher.checkIpPattern(reviewPojo.getIp())){
            return "Your ip address is invalid";
        }
        Product product = productService.getProductById(id);
        User user = userLoginService.getUserById((int)session.getAttribute("userId"));

        Review review = new Review();

        review.setContent(reviewPojo.getContent());
        review.setRating(reviewPojo.getRating());

        //ip comparison
        if(!reviewService.getReviewByIp(reviewPojo.getIp()).isEmpty()){
            //in our app 'true' or 1 will represent fake
            //whereas 'false' and 0 will represent real
            review.setType(true);
        }


        review.setIp(reviewPojo.getIp());
        review.setUser(user);

        product.setRatings(
                ((product.getRatings()*(product.getTotalReviews()))+(float) reviewPojo.getRating())/(product.getTotalReviews()+1)
        );
        product.setTotalReviews(product.getTotalReviews()+1);
        productService.saveProduct(product);

        review.setProduct(product);

        reviewService.saveReview(review);


        return "success";
    }
}
