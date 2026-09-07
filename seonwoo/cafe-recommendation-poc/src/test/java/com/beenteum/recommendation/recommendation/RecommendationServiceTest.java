package com.beenteum.recommendation.recommendation;

import com.beenteum.recommendation.cafe.NoiseLevel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {

    private final RecommendationService recommendationService =
            new RecommendationService();

    @Test
    void 공부목적_추천점수가_높은_카페를_우선추천한다() {

        RecommendationRequest request =
                new RecommendationRequest(
                        Purpose.STUDY,
                        true,
                        true,
                        NoiseLevel.LOW
                );

        List<RecommendationResponse> result =
                recommendationService.recommend(request);

        assertEquals(3, result.size());

        assertEquals(
                "빈틈 카페",
                result.get(0).getCafeName()
        );

        assertEquals(
                7,
                result.get(0).getScore()
        );
    }

    @Test
    void 대화목적_추천점수가_높은_카페를_우선추천한다() {

        RecommendationRequest request =
                new RecommendationRequest(
                        Purpose.TALK,
                        false,
                        true,
                        NoiseLevel.MEDIUM
                );

        List<RecommendationResponse> result =
                recommendationService.recommend(request);

        assertEquals(3, result.size());

        assertEquals(
                "캠퍼스 카페",
                result.get(0).getCafeName()
        );

        assertEquals(
                4,
                result.get(0).getScore()
        );
    }

    @Test
    void 일부조건을_충족하지_못한_카페도_추천결과에_포함한다() {

        RecommendationRequest request =
                new RecommendationRequest(
                        Purpose.STUDY,
                        true,
                        true,
                        NoiseLevel.LOW
                );

        List<RecommendationResponse> result =
                recommendationService.recommend(request);

        boolean containsLoungeCafe =
                result.stream()
                        .anyMatch(
                                response ->
                                        response.getCafeName()
                                                .equals("라운지 카페")
                        );

        assertTrue(containsLoungeCafe);
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