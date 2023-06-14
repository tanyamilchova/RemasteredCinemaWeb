package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    private  String first_name;
    private String last_name;
    private String email;
    private String password;
    private String confirmPassword;
    private LocalDate birth_date;
    private int city_id;
    private String gender;
    private String phone_number;
    private String role_name;
    private String errorMassage;

}
