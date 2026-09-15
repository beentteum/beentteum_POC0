package com.beentteum.poc.favorite;

public class FavoriteNotFoundException extends RuntimeException {

    public FavoriteNotFoundException(String message) {
        super(message);
    }
}
