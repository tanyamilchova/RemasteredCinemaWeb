package com.example.kinoarenaproject.model.DTOs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CityInfo {
    private int id;
    private String name;
    private String postcode;

}
