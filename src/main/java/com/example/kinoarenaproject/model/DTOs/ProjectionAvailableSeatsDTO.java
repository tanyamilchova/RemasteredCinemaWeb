package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionAvailableSeatsDTO {
    private int rows;
    private int cols;
    private int countTakenTickets;
    private boolean[][] isTaken = new boolean[rows][cols];

    private ArrayList<ArrayList<Integer>>plan=new ArrayList<>();
}
