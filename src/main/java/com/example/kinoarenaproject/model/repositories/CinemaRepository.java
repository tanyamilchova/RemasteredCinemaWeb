package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema,Integer> {


    List<Cinema> findByCity(City city);





}
