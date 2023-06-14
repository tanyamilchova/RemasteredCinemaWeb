package com.example.kinoarenaproject.model.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddMovieDTO {
    private int id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Duration is required")
    @Positive(message = "Duration should be a positive number")
    private Integer duration;
    @Future(message = "Premiere date should be in the future")
    @NotNull(message = "Premiere is required")
    private LocalDate premiere;
    @Pattern(regexp = "^[\\p{L}\\p{P}\\s]*$", message = "Director must contain only letters, spaces, and punctuation marks")
    private String director;
    @Pattern(regexp = "^[\\p{L}\\p{P}\\s]*$", message = "Cast must contain only letters, spaces, and punctuation marks")
    private String cast;
    @NotNull(message = "Category is required")
    private Integer categoryId;
    @NotNull(message = "Genre is required")
    private Integer genreId;
}
