package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.service.CinemaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
public class CinemaController extends AbstractController{

    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/admin/cinemas")
    public CinemaDTO add(@RequestBody AddCinemaDTO addData){

        CinemaDTO cinema = cinemaService.add(addData);
        return cinema;
    }

   @PostMapping("cinemas/filter")
    public List<AddCinemaDTO> filter(@RequestBody int cityId){
        List<AddCinemaDTO>cinemaList=cinemaService.filterByCity(cityId);
        return cinemaList;
   }


   @PutMapping("/admin/cinemas/{id}")
   public CinemaDTO edit(@RequestBody CinemaDTO editData,@PathVariable int id){

        return cinemaService.edit(editData,id);

   }

   @GetMapping("/cinemas/{id}")
    public CinemaDTO getById(@PathVariable int id){
        return cinemaService.getById(id);
   }



   @GetMapping("/cinemas/all")
    public HashSet<CinemaDTO> getAll(){
        HashSet<CinemaDTO>cinemas=cinemaService.getAll();
        return cinemas;
   }

   @DeleteMapping("/admin/cinemas/{id}")
   public CinemaDTO remove(@PathVariable int id){

       return  cinemaService.remove(id);
   }


}



