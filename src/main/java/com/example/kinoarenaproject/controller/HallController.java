package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.AddHallDTO;
import com.example.kinoarenaproject.model.DTOs.HallDTO;
import com.example.kinoarenaproject.service.HallService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HallController extends AbstractController{
    @Autowired
    HallService hallService;


    @PostMapping("/halls")
    public HallDTO add(@RequestBody AddHallDTO addData, HttpSession session){
        int id=loggedId(session);
        HallDTO hall=hallService.add(addData,id);

        return hall;
    }

    @PostMapping("halls/by_cinema")
    public List<AddHallDTO> filter(@RequestBody int cinemaId){
        List<AddHallDTO>hallList=hallService.filterByCinema(cinemaId);
        return hallList;
    }


    @PutMapping("/halls/{id}")
    public HallDTO edit(@RequestBody HallDTO editData, @PathVariable int id, HttpSession session){
        int userId=loggedId(session);
        HallDTO hall=hallService.edit(editData,id,userId);
        return hall;
    }

    @GetMapping("/halls/{id}/delete")
    public HallDTO getById(@PathVariable int id){
        return hallService.getById(id);
    }

    @DeleteMapping("/halls/{id}")
    public HallDTO remove(@PathVariable int id, HttpSession session){
        int userId=loggedId(session);
        return  hallService.remove(id,userId);
    }



}
