package com.example.kinoarenaproject.model.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieFilterDateDTO {

    private int id;

    @NotNull
    private LocalDate date;
    @NotNull
    @Positive
    private int cinemaId;
//    private ProjectionDTO projectionDTO;
}
