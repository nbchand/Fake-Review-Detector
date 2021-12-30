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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private boolean status;
    @ManyToMany
    @JoinTable(
            name = "user_product",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @ToString.Exclude
    private List<Product> products;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Review> reviews;
}
