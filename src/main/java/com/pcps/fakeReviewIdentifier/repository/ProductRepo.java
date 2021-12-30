package com.pcps.fakeReviewIdentifier.repository;

import com.pcps.fakeReviewIdentifier.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
}
