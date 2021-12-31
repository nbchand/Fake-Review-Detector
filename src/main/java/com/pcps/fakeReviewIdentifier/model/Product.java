package com.pcps.fakeReviewIdentifier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private int price;
    @Column
    private String description;
    @Column
    private String image;
    @Column
    private String category;
    @Column
    private int totalPurchases;
    @Column
    private float ratings;
    @Column
    private int totalReviews;
    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private List<User> users;
    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Review> reviews;
}
