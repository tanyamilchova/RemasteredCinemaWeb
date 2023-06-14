package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddHallDTO {
    private int id;
    private int type_id;
    private int rows;
    private int columns;
    private int cinema_id;
}
