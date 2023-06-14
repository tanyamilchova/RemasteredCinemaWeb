package com.example.kinoarenaproject.model.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketBookDTO {
    @NotNull(message = "Projection ID is required")
    @Positive
    private int projectionId;
    @NotNull(message = "Number of row is required")
    @Positive(message = "Row should be a positive value")
    private int rowNumber;
    @NotNull(message = "Number of column is required")
    @Positive(message = "Column should be a positive value")
    private int colNumber;
    //-------------------------------
    private ArrayList<Integer>seat;

}
