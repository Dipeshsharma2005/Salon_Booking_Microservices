package com.salon.reviewservice.service;

import com.salon.reviewservice.model.Review;
import com.salon.reviewservice.payload.dto.RatingSummary;
import com.salon.reviewservice.payload.dto.ReviewRequest;
import com.salon.reviewservice.payload.dto.SalonDTO;
import com.salon.reviewservice.payload.dto.UserDTO;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest reviewRequest, UserDTO userDTO, SalonDTO salonDTO);

    List<Review> getReviewsBySalonId(Long salonId);

    Review updateReview(ReviewRequest reviewRequest,Long reviewId, Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;

    RatingSummary getRatingSummary(Long salonId);
}
