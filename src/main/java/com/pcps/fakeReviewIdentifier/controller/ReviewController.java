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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;

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
        if(!reviewService.getReviewByProductAndIp(product, reviewPojo.getIp()).isEmpty()){
            //in our app 'true' or 1 will represent fake
            //whereas 'false' and 0 will represent real
            review.setType(true);
        }

        review.setIp(reviewPojo.getIp());
        review.setUser(user);

        //for representing rating with single decimal value(e.g 4.5)
        //calculate rating
        float f = ((product.getRatings()*(product.getTotalReviews()))+(float) reviewPojo.getRating())/(product.getTotalReviews()+1);
        DecimalFormat df = new DecimalFormat("#.#");
        float rating = Float.valueOf(df.format(f));

        product.setRatings(rating);
        product.setTotalReviews(product.getTotalReviews()+1);
        productService.saveProduct(product);

        review.setProduct(product);

        reviewService.saveReview(review);


        return "success";
    }

    @DeleteMapping("/review/{id}")
    @ResponseBody
    public String deleteReview(@PathVariable int id, HttpSession session){
        if(session.getAttribute("userId")==null){
            return "redirect:/";
        }
        reviewService.deleteReview(reviewService.getReviewById(id));
        return "success";
    }
}
