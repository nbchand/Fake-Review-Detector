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
    private float price;
    @Column
    private String description;
    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private List<User> users;
    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Review> reviews;
}
