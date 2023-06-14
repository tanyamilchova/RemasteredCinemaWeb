package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.Hall;
import com.example.kinoarenaproject.model.entities.Movie;
import com.example.kinoarenaproject.model.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Integer> {
    Optional<Projection> findById(int id);

    List<Projection> findByMovie(Optional<Movie> movie);

    List<Projection> findByMovieId(int mofivie);

//    @Query(value = "SELECT p.id, p.movie_id, p.hall_id, p.date, p.price, p.start_time FROM projections AS p JOIN halls AS h ON p.hall_id = h.id WHERE h.cinema_id = :cinemaId", nativeQuery = true)
//    List<Projection> getProjectionsByCinema(@Param("cinemaId") int cinemaId);

//    @Query(value = "SELECT p.id, p.movie_id, p.hall_id, p.date, p.price, p.start_time FROM projections AS p JOIN halls AS h ON p.hall_id = h.id WHERE h.cinema_id = :cinemaId AND p.movie_id = :movieId", nativeQuery = true)
//    List<Projection> getProjectionsByCinemaAndMovie(@Param("cinemaId") int cinemaId, @Param("movieId") int movieId);



//    @Query(value =                       "SELECT * FROM projections AS p JOIN halls as h ON h.id=p.hall_id JOIN cinemas AS c ON h.cinema_id =c.id WHERE cinema_id=:cinemaId AND movie_id= :movieId",nativeQuery = true)
//    HashSet<Projection> getProjectionByCinemaAndMovie(@Param("cinemaId")int cinemaId, @Param("movieId")int movieId);

    @Query(value = "SELECT p.id AS projectionId, p.* FROM projections AS p JOIN halls AS h ON h.id = p.hall_id JOIN cinemas AS c ON h.cinema_id = c.id WHERE cinema_id = :cinemaId AND movie_id = :movieId", nativeQuery = true)
    HashSet<Projection> getProjectionByCinemaAndMovie(@Param("cinemaId") int cinemaId, @Param("movieId") int movieId);

    @Query(value = "SELECT          p.* FROM projections AS p JOIN halls  AS h ON p.hall_id=h.id JOIN cinemas AS c ON h.cinema_id=c.id WHERE cinema_id= :cinemaId",nativeQuery = true)
    HashSet<Projection>getProjectionByCinema(@Param("cinemaId") int cinemaId);

    @Query(value = "SELECT p.* FROM projections AS p JOIN halls AS h ON p.hall_id=h.id JOIN cinemas AS c ON h.cinema_id=c.id WHERE   h.cinema_id=1 AND p.date='2023-04-14'  AND 	p.start_time='18:00:00'",nativeQuery = true)
    HashSet<Projection>getProjectionByDateAndStartTimeAndCinema(@Param("date") LocalDate date,@Param( "start_time") LocalTime startTime,@Param("cinemaId") int cinemaId);


    @Query(value = "SELECT COUNT(*) FROM projections AS p JOIN halls AS h ON p.hall_id=h.id JOIN cinemas AS c ON h.cinema_id=c.id WHERE p.date=:date AND p.start_time= :startTime AND p.hall_id=:hallId AND c.id=:id", nativeQuery = true)
    int countByDateAndStartTimeAndHallAndCinema(@Param("date") LocalDate date, @Param("startTime") LocalTime time, @Param("hallId") int hallId, @Param("id") int cinemaId);
}
