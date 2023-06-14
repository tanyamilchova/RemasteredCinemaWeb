package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoDTO {
    private int id;
    private UserWithoutPasswordDTO user;
    private ProjectionDTO projection;
    private int rowNumber;
    private int colNumber;
}
