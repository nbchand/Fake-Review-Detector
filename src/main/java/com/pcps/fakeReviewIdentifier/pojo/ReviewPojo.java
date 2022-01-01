package com.pcps.fakeReviewIdentifier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPojo {
    private String ip;
    private String content;
    private int rating;
}
