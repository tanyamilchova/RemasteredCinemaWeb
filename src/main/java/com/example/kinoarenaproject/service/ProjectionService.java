package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.*;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectionService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    CinemaRepository cinemaRepository;



    public HashSet<List<Integer>> getAvSeats(int projectionId) {
        Projection projection=ifPresent(projectionRepository.findById(projectionId));
        HashSet<Ticket>takenTickets=ticketRepository.findAllByProjection(projection);

        HashSet<TicketAvSeatsDTO>ticketAvSeatsDTOS=new HashSet<>();
        for (Ticket t:takenTickets){
            TicketAvSeatsDTO seatsDTO=mapper.map(t,TicketAvSeatsDTO.class);
            ticketAvSeatsDTOS.add(seatsDTO);
        }
        HashSet<List<Integer>>hashSet=new HashSet<>();
        for (int i = 1; i <= projection.getHall().getRows(); i++) {
            for (int j = 1; j <= projection.getHall().getColumns(); j++) {
                List<Integer> rowSeat = new ArrayList<>();
                rowSeat.add(i);
                rowSeat.add(j);
                if( !ticketAvSeatsDTOS.contains(rowSeat)){
                    hashSet.add(rowSeat);
                }
            }
        }
       return hashSet;
    }

    public ProjectionDTO add(AddProjectionDTO addData) {

        Hall hall=ifPresent(hallRepository.findById(addData.getHallId()));
        Movie movie=ifPresent(movieRepository.findById(addData.getMovieId()));
        Cinema cinema=ifPresent(cinemaRepository.findById(hall.getCinema().getId()));

        int availableProjecton=projectionRepository.countByDateAndStartTimeAndHallAndCinema(addData.getDate(),addData.getStartTime(),addData.getHallId(),hall.getCinema().getId());
        if(availableProjecton!=0){
            throw new BadRequestException("The hall is taken at that date and time");
        }
        Projection p=mapper.map(addData,Projection.class);
        p.setDate(addData.getDate());
        p.setStartTime(addData.getStartTime());
        p.setPrice(addData.getPrice());
        p.setHall(hall);
        p.setMovie(movie);
        projectionRepository.save(p);

        return mapper.map(p,ProjectionDTO.class);
    }

    public ProjectionDTO edit(EditProjectionDTO editData, int id) {

        Hall hall=(ifPresent(hallRepository.findById(editData.getHallId())));
        Movie movie=(ifPresent(movieRepository.findById(editData.getMovieId())));
        Projection projection=ifPresent(projectionRepository.findById(id));

        projection.setPrice(editData.getPrice());
        projection.setDate(editData.getDate());
        projection.setStartTime(editData.getStartTime());
        projection.setHall(hall);
        projection.setMovie(movie);

        projectionRepository.save(projection);
        return mapper.map(projection,ProjectionDTO.class);


    }

    public ProjectionDTO remove(int id) {

        Projection projection=ifPresent(projectionRepository.findById(id));
        projectionRepository.delete(projection);
        return mapper.map(projection,ProjectionDTO.class);
    }

    public ProjectionDTO getById(int id) {
        Projection projection=ifPresent(projectionRepository.findById(id));
        return mapper.map(projection,ProjectionDTO.class);
    }

    public HashSet<ProjectionDTO> filterByCinema(Integer cinemaId) {
        Cinema cinema=ifPresent(cinemaRepository.findById(cinemaId));
        HashSet<Projection>projectionHashSet=projectionRepository.getProjectionByCinema(cinemaId);
        HashSet<ProjectionDTO>projectionDTOHashSet=new HashSet<>();
        for (Projection p:projectionHashSet){
            ProjectionDTO pr=mapper.map(p,ProjectionDTO.class);
            projectionDTOHashSet.add(pr);
        }
        return projectionDTOHashSet;
    }

    public HashSet<ProjectionDTO> filterByCinemaAndMovie(Integer cinemaId, Integer movieId) {
        Cinema cinema=ifPresent(cinemaRepository.findById(cinemaId));
        Movie movie=ifPresent(movieRepository.findById(movieId));
        HashSet<Projection>projectionHashSet=projectionRepository.  getProjectionByCinemaAndMovie(cinemaId,movieId);
       HashSet<ProjectionDTO>projectionDTOHashSet=new HashSet<>();
        for (Projection p:projectionHashSet){
            ProjectionDTO projectionDTO=mapper.map(p,ProjectionDTO.class);
            projectionDTOHashSet.add(projectionDTO);
        }
        return projectionDTOHashSet;
    }
    public HashSet<ProjectionDTO> projectionsByDateTimeAndCinema(LocalDate date, LocalTime time,int cinemaId) {
        Cinema cinema=ifPresent(cinemaRepository.findById(cinemaId));

        HashSet<Projection>projectionHashSet=projectionRepository.getProjectionByDateAndStartTimeAndCinema(date,time,cinemaId);
        HashSet<ProjectionDTO>projectionDTOHashSet=new HashSet<>();
        for (Projection p:projectionHashSet){
            ProjectionDTO projectionDTO=mapper.map(p,ProjectionDTO.class);
            projectionDTOHashSet.add(projectionDTO);
        }
        return projectionDTOHashSet;
    }
}
