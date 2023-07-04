package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City>findById(int id);
}
