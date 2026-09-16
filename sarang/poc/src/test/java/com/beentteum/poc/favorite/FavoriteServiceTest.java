package com.beentteum.poc.favorite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FavoriteServiceTest {

    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        favoriteService = new FavoriteService(new FavoriteMemoryRepository());
    }

    @Test
    void 정상적으로즐겨찾기를등록한다() {
        assertEquals(FavoriteService.SUCCESS_MESSAGE, favoriteService.addFavorite(1L, 101L));
    }

    @Test
    void 같은사용자와카페는중복등록하지않는다() {
        favoriteService.addFavorite(1L, 101L);

        assertEquals(FavoriteService.DUPLICATE_MESSAGE, favoriteService.addFavorite(1L, 101L));
    }

    @Test
    void 존재하지않는사용자는등록할수없다() {
        FavoriteNotFoundException exception = assertThrows(
                FavoriteNotFoundException.class,
                () -> favoriteService.addFavorite(999L, 101L)
        );

        assertEquals("존재하지 않는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 존재하지않는카페는등록할수없다() {
        FavoriteNotFoundException exception = assertThrows(
                FavoriteNotFoundException.class,
                () -> favoriteService.addFavorite(1L, 999L)
        );

        assertEquals("존재하지 않는 카페입니다.", exception.getMessage());
    }
}
