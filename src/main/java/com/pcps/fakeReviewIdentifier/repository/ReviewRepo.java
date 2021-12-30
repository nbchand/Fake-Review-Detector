package com.pcps.fakeReviewIdentifier.repository;

import com.pcps.fakeReviewIdentifier.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Integer> {
}
