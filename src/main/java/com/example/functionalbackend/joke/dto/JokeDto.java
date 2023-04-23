package com.example.functionalbackend.joke.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@EqualsAndHashCode

public class JokeDto implements Serializable {

    @NotNull(message = "{category.not.null}")
    @NotEmpty(message = "{category.not.empty}")
    private final String category;
    @NotNull(message = "{type.not.null}")
    @NotEmpty(message = "{type.not.empty}")
    private final String type;
    @NotNull(message = "{joke.not.null}")
    @NotEmpty(message = "{joke.not.empty}")
    private final String joke;
    @Min(message = "{id.bigger.than.zero}", value = 1)
//    @NotEmpty(message = "{id.not.empty}")
    private final Long id;

}
