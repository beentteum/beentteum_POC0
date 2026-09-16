package com.beentteum.poc.favorite;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FavoriteMemoryRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Cafe> cafes = new ConcurrentHashMap<>();
    private final Set<FavoriteKey> favorites = ConcurrentHashMap.newKeySet();

    public FavoriteMemoryRepository() {
        users.put(1L, new User(1L, "김민수"));
        users.put(2L, new User(2L, "이서연"));
        cafes.put(101L, new Cafe(101L, "모닝브루", "서울 마포구 월드컵북로 10"));
        cafes.put(102L, new Cafe(102L, "라운드빈", "서울 성동구 서울숲길 22"));
        cafes.put(103L, new Cafe(103L, "페이지커피", "서울 종로구 북촌로 7"));
    }

    public Map<Long, Cafe> cafes() {
        return Map.copyOf(cafes);
    }

    public boolean userExists(Long userId) {
        return users.containsKey(userId);
    }

    public boolean cafeExists(Long cafeId) {
        return cafes.containsKey(cafeId);
    }

    public boolean addFavorite(Long userId, Long cafeId) {
        return favorites.add(new FavoriteKey(userId, cafeId));
    }
}
