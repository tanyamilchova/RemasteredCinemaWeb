package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProjectionByDateTimeCinema {
    private int id;
    private int cinemaId;
    private LocalDate date;
    private LocalTime startTime;

}
