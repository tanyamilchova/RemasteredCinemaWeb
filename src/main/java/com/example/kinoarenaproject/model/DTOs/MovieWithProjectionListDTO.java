package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovieWithProjectionListDTO {
    private int id;
    private String title;
    private CategoryInfoDTO category;
    private List<ProjectionDTO>projectionDTOS;
}
