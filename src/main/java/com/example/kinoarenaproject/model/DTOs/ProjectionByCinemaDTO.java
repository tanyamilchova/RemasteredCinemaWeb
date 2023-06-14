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
public class ProjectionByCinemaDTO {
        private int id;
        private String movieTitle;
        private String cinemaName;
//        private int cinemaId;
        private LocalDate date;
        private LocalTime startTime;
}
