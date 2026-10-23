package com.salon.reviewservice.service.implementation;

import com.salon.reviewservice.model.Review;
import com.salon.reviewservice.payload.dto.RatingSummary;
import com.salon.reviewservice.payload.dto.ReviewRequest;
import com.salon.reviewservice.payload.dto.SalonDTO;
import com.salon.reviewservice.payload.dto.UserDTO;
import com.salon.reviewservice.repository.ReviewRepository;
import com.salon.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewRequest reviewRequest, UserDTO userDTO, SalonDTO salonDTO) {

        Review review = new Review();
        review.setReviewText(reviewRequest.getReviewText());
        review.setRating(reviewRequest.getRating());
        review.setUserId(userDTO.getId());
        review.setSalonId(salonDTO.getId());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsBySalonId(Long salonId) {
        return reviewRepository.findBySalonId(salonId);
    }

    private Review getReviewById(Long reviewId) throws Exception {

        return reviewRepository.findById(reviewId).orElseThrow(()->new Exception("Review does not exist"));
    }

    @Override
    public Review updateReview(ReviewRequest reviewRequest, Long reviewId, Long userId) throws Exception {

        Review review = getReviewById(reviewId);

        if (!review.getUserId().equals(userId)) {
            throw new Exception("You don't have permission to update review");
        }

        review.setReviewText(reviewRequest.getReviewText());
        review.setRating(reviewRequest.getRating());

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review = getReviewById(reviewId);

        if (!review.getUserId().equals(userId)) {
            throw new Exception("You don't have permission to delete review");
        }

        reviewRepository.delete(review);
    }

    @Override
    public RatingSummary getRatingSummary(Long salonId) {
        List<Review> reviews = reviewRepository.findBySalonId(salonId);

        long totalRatings = reviews.size();
        double average = reviews.stream().mapToDouble(Review::getRating).average().orElse(0);

        long count5 = reviews.stream().filter(r -> r.getRating() >= 4.5).count();
        long count4 = reviews.stream().filter(r -> r.getRating() >= 3.5 && r.getRating() < 4.5).count();
        long count3 = reviews.stream().filter(r -> r.getRating() >= 2.5 && r.getRating() < 3.5).count();
        long count2 = reviews.stream().filter(r -> r.getRating() >= 1.5 && r.getRating() < 2.5).count();
        long count1 = reviews.stream().filter(r -> r.getRating() < 1.5).count();

        List<RatingSummary.RatingBreakdown> breakdown = List.of(
                new RatingSummary.RatingBreakdown("Excellent", count5, totalRatings == 0 ? 0 : (count5 * 100.0 / totalRatings)),
                new RatingSummary.RatingBreakdown("Very Good", count4, totalRatings == 0 ? 0 : (count4 * 100.0 / totalRatings)),
                new RatingSummary.RatingBreakdown("Good", count3, totalRatings == 0 ? 0 : (count3 * 100.0 / totalRatings)),
                new RatingSummary.RatingBreakdown("Average", count2, totalRatings == 0 ? 0 : (count2 * 100.0 / totalRatings)),
                new RatingSummary.RatingBreakdown("Poor", count1, totalRatings == 0 ? 0 : (count1 * 100.0 / totalRatings))
        );

        RatingSummary summary = new RatingSummary();
        summary.setAverageRating(average);
        summary.setTotalRatings(totalRatings);
        summary.setBreakdown(breakdown);

        return summary;
    }

}
