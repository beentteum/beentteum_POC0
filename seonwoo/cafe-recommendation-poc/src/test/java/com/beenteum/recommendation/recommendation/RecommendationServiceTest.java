package com.beenteum.recommendation.recommendation;

import com.beenteum.recommendation.cafe.NoiseLevel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {

    private final RecommendationService recommendationService =
            new RecommendationService();

    @Test
    void 공부목적_조건에_맞는_카페를_추천한다() {

        RecommendationRequest request =
                new RecommendationRequest(
                        Purpose.STUDY,
                        true,
                        true,
                        NoiseLevel.LOW
                );

        List<RecommendationResponse> result =
                recommendationService.recommend(request);

        assertEquals(1, result.size());
        assertEquals("빈틈 카페", result.get(0).getCafeName());
        assertEquals(7, result.get(0).getScore());
    }

    @Test
    void 대화목적_조건에_맞는_카페를_추천한다() {

        RecommendationRequest request =
                new RecommendationRequest(
                        Purpose.TALK,
                        false,
                        true,
                        NoiseLevel.MEDIUM
                );

        List<RecommendationResponse> result =
                recommendationService.recommend(request);

        assertEquals(1, result.size());
        assertEquals("캠퍼스 카페", result.get(0).getCafeName());
    }

    @Test
    void 방문목적이_없으면_예외가_발생한다() {

        RecommendationRequest request =
                new RecommendationRequest(
                        null,
                        true,
                        true,
                        NoiseLevel.LOW
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> recommendationService.recommend(request)
                );

        assertEquals(
                "방문 목적을 선택해야 합니다.",
                exception.getMessage()
        );
    }
}