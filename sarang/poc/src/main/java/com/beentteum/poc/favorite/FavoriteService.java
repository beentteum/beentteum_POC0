package com.beentteum.poc.favorite;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    public static final String SUCCESS_MESSAGE = "즐겨찾기 등록 완료";
    public static final String DUPLICATE_MESSAGE = "이미 즐겨찾기에 등록된 카페입니다.";

    private final FavoriteMemoryRepository repository;

    public FavoriteService(FavoriteMemoryRepository repository) {
        this.repository = repository;
    }

    public String addFavorite(Long userId, Long cafeId) {
        if (!repository.userExists(userId)) {
            throw new FavoriteNotFoundException("존재하지 않는 사용자입니다.");
        }
        if (!repository.cafeExists(cafeId)) {
            throw new FavoriteNotFoundException("존재하지 않는 카페입니다.");
        }
        return repository.addFavorite(userId, cafeId) ? SUCCESS_MESSAGE : DUPLICATE_MESSAGE;
    }

    public List<Cafe> findCafes() {
        return repository.cafes().values().stream()
                .sorted((left, right) -> left.id().compareTo(right.id()))
                .toList();
    }
}
