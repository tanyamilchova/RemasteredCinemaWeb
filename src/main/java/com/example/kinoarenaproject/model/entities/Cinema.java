package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity(name = "cinemas")
@Table
@Setter
@Getter
public class Cinema {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String phone_number;



    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "cinema",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Hall>halls=new ArrayList<>();

}
