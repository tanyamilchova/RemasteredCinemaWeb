package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfoDTO {
    private int id;
    private String title;
    private String description;
    private int duration;
    private LocalDate premiere;
    private String director;
    private String cast;
    private CategoryInfoDTO category;
    private GenreInfoDTO genre;
}
