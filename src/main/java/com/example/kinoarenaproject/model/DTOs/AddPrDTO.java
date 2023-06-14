package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.Hall;
import com.example.kinoarenaproject.model.entities.Movie;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class AddPrDTO {
    private int id;
    @Future
    private LocalTime startTime;
    @Future
    private LocalDate date;
    @Positive
    @NotNull
    private double price;

    private int hallId;

    private int movieId;
}
