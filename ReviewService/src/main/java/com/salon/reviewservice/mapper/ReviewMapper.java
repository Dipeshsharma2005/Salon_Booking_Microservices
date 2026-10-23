package com.salon.reviewservice.mapper;

import com.salon.reviewservice.model.Review;
import com.salon.reviewservice.payload.dto.ReviewDTO;
import com.salon.reviewservice.payload.dto.SalonDTO;
import com.salon.reviewservice.payload.dto.UserDTO;

public class ReviewMapper {

    public static ReviewDTO toDTO(Review review, UserDTO userDTO, SalonDTO salonDTO) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setUser(userDTO);
        reviewDTO.setCreatedAt(review.getCreatedAt());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setSalon(salonDTO);

        return reviewDTO;
    }
}
