package com.example.kinoarenaproject.model.DTOs;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditMovieDTO {
    private String title;
    private String description;
    @Positive(message = "Duration must be a positive integer")
    private Integer duration;
    @Future(message = "Premiere date must be in the future")
    private LocalDate premiere;
    private String director;
    private String cast;
    @Positive(message = "Duration must be a positive integer")
    private int category;
    @Positive(message = "Duration must be a positive integer")
    private int genre;
}
