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
public class AddProjectionDTO {
    private int id;
    @NotNull(message = "Start time is required")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @NotNull(message = "Date is required")
    @Future(message = "Date should be in the future")
    private LocalDate date;
    @NotNull(message = "Price is required")
    @Positive(message = "Price should be a positive value")
    private double price;
    @NotNull(message = "Hall ID is required")
    @Positive(message = "Hall ID should be a positive value")
    private int hallId;
    @NotNull(message = "Movie ID is required")
    @Positive(message = "Movie ID should be a positive value")
    private int movieId;
}
