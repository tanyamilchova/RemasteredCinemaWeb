package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "categories")
@Table
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "category")
    private String category;
    @Column(name = "min_age")
    private int minAge;
}
