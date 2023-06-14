package com.example.kinoarenaproject.model.DTOs;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProjectionDTO {
    @Future
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @Future(message = "Date should be in the future")
    @DateTimeFormat
    private LocalDate date;
    @NotNull
    @Positive(message = "Price should be a positive value")
    private double price;
    private int hallId;
    private int movieId;
}
