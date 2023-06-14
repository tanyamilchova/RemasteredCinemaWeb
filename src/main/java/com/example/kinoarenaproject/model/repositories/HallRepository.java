package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.Hall;
import com.example.kinoarenaproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall,Integer> {
    Optional<Hall>findById(int id);
    List<Hall> findByCinema(Cinema cinema);
    Optional<Hall> findByCinema(int cinemaId);

}
