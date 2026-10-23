package com.salon.reviewservice.controller;

import com.salon.reviewservice.mapper.ReviewMapper;
import com.salon.reviewservice.model.Review;
import com.salon.reviewservice.payload.dto.*;
import com.salon.reviewservice.service.ReviewService;
import com.salon.reviewservice.service.client.SalonFeignClient;
import com.salon.reviewservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final UserFeignClient userFeignClient;

    private final SalonFeignClient salonFeignClient;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest,@PathVariable Long salonId, @RequestHeader("Authorization")String jwt) throws Exception {

        System.out.println(jwt);
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        System.out.println(user);

        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        Review review = reviewService.createReview(reviewRequest,user,salon);

        return ResponseEntity.ok(review);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsBySalonId(@PathVariable Long salonId,@RequestHeader("Authorization")String jwt) throws Exception {


        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        List<Review> reviews = reviewService.getReviewsBySalonId(salon.getId());

        List<ReviewDTO> reviewDTOs = reviews.stream().map(review -> {
            UserDTO user=null;
            try {
                user = userFeignClient.getUserById(review.getUserId()).getBody();
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
            return ReviewMapper.toDTO(review,user,salon);
        }).toList();

        return ResponseEntity.ok(reviewDTOs);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@RequestBody ReviewRequest reviewRequest,@PathVariable Long reviewId, @RequestHeader("Authorization")String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        Review review = reviewService.updateReview(reviewRequest,reviewId,user.getId());

        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId, @RequestHeader("Authorization")String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        reviewService.deleteReview(reviewId,user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/salons/{salonId}/ratings")
    public ResponseEntity<RatingSummary> getSalonRatings(@PathVariable Long salonId) {
        RatingSummary summary = reviewService.getRatingSummary(salonId);
        return ResponseEntity.ok(summary);
    }

}
