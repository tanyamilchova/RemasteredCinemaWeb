package com.example.kinoarenaproject.model.DTOs;

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
public class UserWithoutPasswordDTO {

    private int id;
    private  String first_name;
    private String last_name;
    private String email;
    private LocalDate birth_date;
    private int city_id;
    private String gender;
    private String phone_number;
    private String role_name;
    private String profileImageUrl;

    }
