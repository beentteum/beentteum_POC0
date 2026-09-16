package com.beenteum.recommendation.recommendation;

import java.util.List;

public class RecommendationResponse {

    private final String cafeName;
    private final int score;
    private final List<String> reasons;

    public RecommendationResponse(
            String cafeName,
            int score,
            List<String> reasons
    ) {
        this.cafeName = cafeName;
        this.score = score;
        this.reasons = reasons;
    }

    public String getCafeName() {
        return cafeName;
    }

    public int getScore() {
        return score;
    }

    public List<String> getReasons() {
        return reasons;
    }
}