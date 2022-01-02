package com.pcps.fakeReviewIdentifier.functionality;

import com.pcps.fakeReviewIdentifier.model.Review;

import java.text.DecimalFormat;
import java.util.List;

public class ReviewHelper {

    public static float calculateAvgRating(List<Review> reviews){
        int sum=0;
        for(Review review: reviews){
           sum=sum+review.getRating();
        }

        float val = (float) sum/reviews.size();

        //for representing rating with single decimal value(e.g 4.5)
        DecimalFormat df = new DecimalFormat("#.#");
        float rating = Float.valueOf(df.format(val));

        if(Float.isNaN(rating)){
            rating = 0.0f;
        }

        return rating;
    }
}
