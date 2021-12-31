package com.pcps.fakeReviewIdentifier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String content;
    @Column
    private String ip;
    @Column
    private boolean type;
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    @ToString.Exclude
    private Product product;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    @ToString.Exclude
    private User user;
}
