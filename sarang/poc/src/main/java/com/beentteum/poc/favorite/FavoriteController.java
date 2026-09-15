package com.beentteum.poc.favorite;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/cafes")
    public List<Cafe> cafes() {
        return favoriteService.findCafes();
    }

    @PostMapping("/favorites")
    public ResponseEntity<String> addFavorite(@RequestBody FavoriteRequest request) {
        return ResponseEntity.ok(favoriteService.addFavorite(request.userId(), request.cafeId()));
    }
}
