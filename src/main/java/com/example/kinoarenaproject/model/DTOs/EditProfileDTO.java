package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDTO {
    private int id;
    private  String first_name;
    private String last_name;
    private String email;
    private LocalDate birth_date;
    private int city_id;
    private String gender;
    private String phone_number;


}
