package com.example.functionalbackend.joke.dto;

import com.example.functionalbackend.infrastructure.joke.dto.JokeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MainJokeDto{
    List<JokeDto> jokes;
}
