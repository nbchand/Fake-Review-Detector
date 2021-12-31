package com.pcps.fakeReviewIdentifier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPojo {
    private String name;
    private int price;
    private String category;
    private String description;
}
