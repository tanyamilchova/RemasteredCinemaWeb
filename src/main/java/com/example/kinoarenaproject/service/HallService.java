package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.AddHallDTO;
import com.example.kinoarenaproject.model.DTOs.HallDTO;
import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.Hall;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CinemaRepository;
import com.example.kinoarenaproject.model.repositories.CityRepository;
import com.example.kinoarenaproject.model.repositories.HallRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HallService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    HallRepository hallRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    CityRepository cityRepository;

    public HallDTO add(AddHallDTO addData, int id) {

        if(! admin(id)){
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Cinema>opt=cinemaRepository.findById(addData.getCinema_id());
        if(!opt.isPresent()){
            throw new NotFoundException("Cinema not found");
        }
        Cinema cinema= opt.get();

        Hall hall = mapper.map(addData,Hall.class);
        hall.setCinema(cinema);
        hallRepository.save(hall);

        return mapper.map(hall,HallDTO.class);
    }

    public List<AddHallDTO> filterByCinema(int cinemaId) {
         List<Hall>halls=new ArrayList<>();
         Cinema cinema=cinemaRepository.findById(cinemaId).get();
         halls.addAll(hallRepository.findByCinema(cinema));
         return halls.stream()
                 .map(hall -> mapper.map(hall,AddHallDTO.class))
                .peek(addHallDTO -> addHallDTO.setCinema_id(cinemaId))
                .collect(Collectors.toList());
    }


    public HallDTO edit(HallDTO editData, int id, int userId) {

        User u = userById(userId);
        if(! admin(userId)){
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Hall> opt = hallRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        Hall h = opt.get();
        h.setType_id(editData.getType_id());
        h.setRows(editData.getRows());
        h.setColumns(editData.getColumns());
        hallRepository.save(h);
        return   mapper.map(h,HallDTO.class);

    }


    public HallDTO getById(int id) {
        Optional<Hall> opt = hallRepository.findById(id);
        if (opt.isPresent()) {
            Hall h = opt.get();
            return mapper.map(h, HallDTO.class);
        } else {
            throw new NotFoundException("Hall not found");
        }
    }


    public HallDTO remove(int id, int userId) {

        User u = userById(userId);
        if (! admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional <Hall>opt=hallRepository.findById(id);
        if(!opt.isPresent()){
            throw new NotFoundException("Hall not found");
        }
        Hall h=opt.get();
        hallRepository.delete(h);
        HallDTO hallDTO=mapper.map(h,HallDTO.class);
        return  hallDTO;
    }


}
