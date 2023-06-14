package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "halls")
@Table
@Setter
@Getter
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "type_id")
    private int type_id;
    @Column(name = "hall_rows")
    private int rows;
    @Column(name = "hall_columns")
    private int columns;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}
