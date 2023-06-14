package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

}
