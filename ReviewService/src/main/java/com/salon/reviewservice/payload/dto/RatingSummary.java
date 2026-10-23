package com.salon.reviewservice.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingSummary {
    private double averageRating;
    private long totalRatings;
    private List<RatingBreakdown> breakdown;

    @Data
    @AllArgsConstructor
    public static class RatingBreakdown {
        private String label;
        private long count;
        private double percentage;
    }
}
