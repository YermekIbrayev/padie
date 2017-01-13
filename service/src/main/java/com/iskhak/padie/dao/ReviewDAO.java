package com.iskhak.padie.dao;

import java.util.List;

import com.iskhak.padie.model.Review;

public interface ReviewDAO {
	boolean addReview(Review review);
	List<Review> getReviews(int pid);
}
