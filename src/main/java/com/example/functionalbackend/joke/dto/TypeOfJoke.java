package com.example.functionalbackend.joke.dto;

import lombok.Getter;

@Getter
public enum TypeOfJoke {
    SINGLE("single"),
    TWO_PART("twopart");

    public final String type;

    TypeOfJoke(String type) {
        this.type = type;
    }
}
