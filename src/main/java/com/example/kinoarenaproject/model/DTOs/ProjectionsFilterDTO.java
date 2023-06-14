package com.example.kinoarenaproject.model.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionsFilterDTO {
    @NotNull
    @Positive
    private Integer cinemaId;
    @NotNull
    @Positive
    private Integer movieId;
}
