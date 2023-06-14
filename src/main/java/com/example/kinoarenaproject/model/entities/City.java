package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "cities")
@Table
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "postcode")
    private String postcode;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL,orphanRemoval = true)
    private  List<Cinema>cinemas;

}
