package com.example.functionalbackend.joke.model;

import com.example.functionalbackend.joke.dto.TypeOfJoke;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "joke_generator")
    @SequenceGenerator(name = "joke_generator", sequenceName = "joke_sequence",
    allocationSize = 1, initialValue = 1)
    private Long id;
    private String category;
    @Enumerated(EnumType.STRING)
    private TypeOfJoke type;
    private String joke;
    @Column(name = "id_of_joke")
    private Long id0fJoke;
}
