package com.beenteum.recommendation.recommendation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService
    ) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<List<RecommendationResponse>> recommend(
            @RequestBody RecommendationRequest request
    ) {

        List<RecommendationResponse> result =
                recommendationService.recommend(request);

        return ResponseEntity.ok(result);
    }
}