package com.beenteum.recommendation.recommendation;

import com.beenteum.recommendation.cafe.Cafe;
import com.beenteum.recommendation.cafe.NoiseLevel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationService {

    private final List<Cafe> cafes = List.of(
            new Cafe(1L, "빈틈 카페", true, true, NoiseLevel.LOW),
            new Cafe(2L, "캠퍼스 카페", true, true, NoiseLevel.MEDIUM),
            new Cafe(3L, "라운지 카페", false, true, NoiseLevel.HIGH)
    );

    public List<RecommendationResponse> recommend(
            RecommendationRequest request
    ) {

        validateRequest(request);

        List<RecommendationResponse> results = new ArrayList<>();

        for (Cafe cafe : cafes) {

            if (!matchesRequiredConditions(cafe, request)) {
                continue;
            }

            List<String> reasons = new ArrayList<>();

            int score = calculateScore(
                    cafe,
                    request,
                    reasons
            );

            results.add(
                    new RecommendationResponse(
                            cafe.getName(),
                            score,
                            reasons
                    )
            );
        }

        results.sort(
                Comparator.comparingInt(
                        RecommendationResponse::getScore
                ).reversed()
        );

        return results;
    }

    private void validateRequest(
            RecommendationRequest request
    ) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "추천 요청 정보가 필요합니다."
            );
        }

        if (request.getPurpose() == null) {
            throw new IllegalArgumentException(
                    "방문 목적을 선택해야 합니다."
            );
        }
    }

    private boolean matchesRequiredConditions(
            Cafe cafe,
            RecommendationRequest request
    ) {

        if (request.isNeedOutlet()
                && !cafe.hasOutlet()) {
            return false;
        }

        if (request.isNeedWifi()
                && !cafe.hasWifi()) {
            return false;
        }

        if (request.getPreferredNoise() != null
                && cafe.getNoiseLevel()
                != request.getPreferredNoise()) {
            return false;
        }

        return true;
    }

    private int calculateScore(
            Cafe cafe,
            RecommendationRequest request,
            List<String> reasons
    ) {

        int score = 0;

        switch (request.getPurpose()) {

            case STUDY -> {
                if (cafe.getNoiseLevel() == NoiseLevel.LOW) {
                    score += 2;
                    reasons.add("조용한 환경");
                }

                if (cafe.hasOutlet()) {
                    score += 1;
                    reasons.add("콘센트 사용 가능");
                }

                if (cafe.hasWifi()) {
                    score += 1;
                    reasons.add("와이파이 사용 가능");
                }
            }

            case WORK -> {
                if (cafe.hasOutlet()) {
                    score += 2;
                    reasons.add("업무에 필요한 콘센트 사용 가능");
                }

                if (cafe.hasWifi()) {
                    score += 2;
                    reasons.add("업무에 필요한 와이파이 사용 가능");
                }
            }

            case TALK -> {
                if (cafe.getNoiseLevel() == NoiseLevel.MEDIUM) {
                    score += 2;
                    reasons.add("대화하기 적합한 소음 수준");
                }
            }

            case REST -> {
                if (cafe.getNoiseLevel() == NoiseLevel.LOW) {
                    score += 2;
                    reasons.add("휴식하기 좋은 조용한 환경");
                }
            }
        }

        if (request.isNeedOutlet()
                && cafe.hasOutlet()) {
            score += 1;
        }

        if (request.isNeedWifi()
                && cafe.hasWifi()) {
            score += 1;
        }

        if (request.getPreferredNoise() != null
                && cafe.getNoiseLevel()
                == request.getPreferredNoise()) {
            score += 1;
        }

        return score;
    }
}