package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionDTO {
    private int id;
    private LocalTime startTime;
    private LocalDate date;
    private double price;
    private HallDTO hall;
    private MovieInfoDTO movie;
}
