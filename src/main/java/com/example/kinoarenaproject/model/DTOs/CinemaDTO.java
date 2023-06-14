package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CinemaDTO {
    private int id;
    private String name;
    private String address;
    private String phone_number;
    private CityInfo city;
}
