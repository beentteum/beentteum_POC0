package com.beenteum.recommendation.recommendation;

import com.beenteum.recommendation.cafe.NoiseLevel;

public class RecommendationRequest {

    private Purpose purpose;
    private boolean needOutlet;
    private boolean needWifi;
    private NoiseLevel preferredNoise;

    public RecommendationRequest() {
    }

    public RecommendationRequest(
            Purpose purpose,
            boolean needOutlet,
            boolean needWifi,
            NoiseLevel preferredNoise
    ) {
        this.purpose = purpose;
        this.needOutlet = needOutlet;
        this.needWifi = needWifi;
        this.preferredNoise = preferredNoise;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public boolean isNeedOutlet() {
        return needOutlet;
    }

    public boolean isNeedWifi() {
        return needWifi;
    }

    public NoiseLevel getPreferredNoise() {
        return preferredNoise;
    }
}