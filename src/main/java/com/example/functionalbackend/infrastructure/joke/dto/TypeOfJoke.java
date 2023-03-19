package com.example.functionalbackend.infrastructure.joke.dto;

public enum TypeOfJoke {
    SINGLE("single"),
    TWO_PART("twopart");

    public final String type;

    TypeOfJoke(String type) {
        this.type = type;
    }
}
