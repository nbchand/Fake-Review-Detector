package com.pcps.fakeReviewIdentifier.controller;

import com.pcps.fakeReviewIdentifier.functionality.PatternMatcher;
import com.pcps.fakeReviewIdentifier.functionality.ReviewHelper;
import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.Review;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.pojo.ReviewPojo;
import com.pcps.fakeReviewIdentifier.service.ProductService;
import com.pcps.fakeReviewIdentifier.service.ReviewService;
import com.pcps.fakeReviewIdentifier.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            return "denied";
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

        review.setProduct(product);
        reviewService.saveReview(review);
        Product product1 = productService.getProductById(id);
        product1.setTotalReviews(product1.getTotalReviews()+1);
        product1.setRatings(ReviewHelper.calculateAvgRating(product1.getReviews()));
        productService.saveProduct(product1);
        return "success";
    }

    @DeleteMapping("/review/{id}")
    @ResponseBody
    public String deleteReview(@PathVariable int id, HttpSession session){
        if(session.getAttribute("userId")==null){
            return "denied";
        }
        reviewService.deleteReview(reviewService.getReviewById(id));
        return "success";
    }

    @GetMapping("/edit-review/{id}")
    public String showEditReviewForm(@PathVariable int id, Model model, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("user")){
            return "redirect:/";
        }
        Review review = reviewService.getReviewById(id);
        model.addAttribute("review",review);
        return "EditReview";
    }

    @PostMapping("/edit-review/{id}")
    public String updateReview(@PathVariable int id, @ModelAttribute ReviewPojo reviewPojo, RedirectAttributes redirectAttributes, HttpSession session){
        if(session.getAttribute("userId")==null||!session.getAttribute("type").equals("user")){
            return "redirect:/";
        }
        if(reviewPojo.getContent().equals("")){
            redirectAttributes.addFlashAttribute("msg","Review can't be empty");
            return "redirect:/edit-review/"+id;
        }
        if(reviewPojo.getRating()<1&&reviewPojo.getRating()>5){
            redirectAttributes.addFlashAttribute("msg","Invalid rating");
            return "redirect:/edit-review/"+id;
        }
        Review review = reviewService.getReviewById(id);
        review.setContent(reviewPojo.getContent());
        review.setRating(reviewPojo.getRating());
        reviewService.saveReview(review);

        Product product = review.getProduct();
        product.setRatings(ReviewHelper.calculateAvgRating(product.getReviews()));
        productService.saveProduct(product);

        return "redirect:/display-product/"+review.getProduct().getId();
    }
}
