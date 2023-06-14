package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.service.ProjectionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
public class ProjectionController extends AbstractController {
    @Autowired
    private ProjectionService projectionService;

//    @PostMapping("/projections")
//    public ProjectionDTO add(@RequestBody @Valid AddProjectionDTO addData, HttpSession session) {
//        int id = loggedId(session);
//        ProjectionDTO projection = projectionService.add(addData, id);
//        return projection;
//    }

//    @DeleteMapping("/projections/{id}")
//    public ProjectionDTO remove(@PathVariable int id, HttpSession session) {
//        int userId = loggedId(session);
//        return projectionService.remove(id, userId);
//    }

//    @PutMapping("/projections/{id}")
//    public ProjectionDTO edit(@RequestBody @Valid EditProjectionDTO editData, @PathVariable int id, HttpSession session) {
//        int userId = loggedId(session);
//        ProjectionDTO projection = projectionService.edit(editData, id, userId);
//        return projection;
//    }

//    @PostMapping("/projections/filter")
//    public List<FilterResponseDTO> filter(@RequestBody FilterRequestDTO filterRequest) {
//        if (filterRequest.getMovieId() == null && filterRequest.getCinemaId() == null) {
//            throw new BadRequestException("Either movieId or cinemaId must be provided");
//        } else if (filterRequest.getCinemaId() != null && filterRequest.getMovieId() == null) {
//            return projectionService.filterByCinema(filterRequest.getCinemaId());
//        } else if (filterRequest.getMovieId() != null && filterRequest.getCinemaId() == null) {
//            return projectionService.filterByMovie(filterRequest.getMovieId());
//        } else {
//            return projectionService.filterByCinemaAndMovie(filterRequest.getCinemaId(), filterRequest.getMovieId());
//        }
//    }

//    @GetMapping("/projections/{id}/seats")
//    public ProjectionAvailableSeatsDTO getAvailableSeats(@PathVariable int id) {
//        return projectionService.getAvailableSeats(id);
//    }

//    @GetMapping("/projections/{id}")
//    public ProjectionDTO getById(@PathVariable int id) {
//        return projectionService.getById(id);
//    }
    //-------------------------------------------------------------------------------------------------
    @PostMapping("projections")
    public ProjectionDTO add(@RequestBody AddProjectionDTO  addProjectionDTO,HttpSession session){
        int userId=loggedId(session);
        return projectionService.add(addProjectionDTO,userId);
    }
    @DeleteMapping("projections/{id}")
    public ProjectionDTO remove(@PathVariable int id, HttpSession session){
        int userId=loggedId(session);
        return projectionService.remove(id,userId);
    }
    @GetMapping("projections/{id}")
    public ProjectionDTO getProjectionById(@PathVariable int id){
        return projectionService.getById(id);
    }
    @PutMapping("projections/{id}")
    public ProjectionDTO edit(@RequestBody EditProjectionDTO editProjectionDTO,@PathVariable int id, HttpSession session){
        int userId=loggedId(session);
        return projectionService.edit(editProjectionDTO,id,userId);

    }
    @GetMapping("/projections/{id}/avSeats")
    public HashSet<List<Integer>> getAvSeats(@PathVariable int id){

        return projectionService.getAvSeats(id);
    }
    @PostMapping ("/projections/filter")
    public HashSet<ProjectionDTO> projectionsByCinemaAndMovie(@RequestBody ProjectionsFilterDTO projectionsFilterDTO){
        HashSet<ProjectionDTO>hashSet=projectionService.filterByCinemaAndMovie(projectionsFilterDTO.getCinemaId(),projectionsFilterDTO.getMovieId());
        return hashSet;
    }
    @GetMapping("projections/filter/{id}")
    public HashSet<ProjectionDTO> projectionByCinema(@PathVariable int id){

        HashSet<ProjectionDTO>projectionDTOS=projectionService.filterByCinema(id);
        return projectionDTOS;
    }
    @PostMapping ("/projections/filterDateTimeCinema")
    public HashSet<ProjectionDTO> projectionsByDateTimeAndCinema(@RequestBody ProjectionByDateTimeCinema projectionByDateTimeCinema){
        HashSet<ProjectionDTO>hashSet=projectionService.projectionsByDateTimeAndCinema(
                projectionByDateTimeCinema.getDate(),projectionByDateTimeCinema.getStartTime(),projectionByDateTimeCinema.getCinemaId());
        return hashSet;
    }


}
