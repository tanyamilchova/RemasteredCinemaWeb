package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Genre;
import com.example.kinoarenaproject.model.entities.Movie;
import com.example.kinoarenaproject.model.entities.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findById(int id);

    List<Movie> findByGenre(Genre genre);
    @Query(value = "SELECT * FROM movies  ",nativeQuery = true)

    Page<Movie>findAll(Pageable pageable);

    @Query(value = "SELECT p.id,p.start_time,p.date,p.hall_id,p.movie_id FROM projections AS p JOIN movies AS m ON p.movie_id=m.id JOIN halls AS h ON p.hall_id=h.id JOIN cinemas AS c ON h.cinema_id=c.id WHERE c.id=1 AND date ='2023-04-14' AND m.id=1 ",nativeQuery = true)
    List<Projection>findAllByCinemaDateMovie(@Param("cinemaId") int cinemaId,@Param("date")LocalDate localDate,@Param("movieId") int movieId);


}
