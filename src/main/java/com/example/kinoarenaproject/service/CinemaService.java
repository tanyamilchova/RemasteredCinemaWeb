package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.AddCinemaDTO;
import com.example.kinoarenaproject.model.DTOs.CinemaDTO;
import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.City;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CinemaRepository;
import com.example.kinoarenaproject.model.repositories.CityRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CinemaService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    CityRepository cityRepository;


    public CinemaDTO add(AddCinemaDTO addCinema, int id) {
        User u = userById(id);
        if (! admin(id)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Cinema cin = mapper.map(addCinema, Cinema.class);
        City city=ifPresent(cityRepository.findById(addCinema.getCity_id()));
        cin.setCity(city);
        cinemaRepository.save(cin);

        return mapper.map(cin, CinemaDTO.class);
    }


    public CinemaDTO edit(CinemaDTO cinemaDto, int id,int userId) {
        User u = userById(userId);
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Cinema> opt = cinemaRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        Cinema c = opt.get();
        c.setName(cinemaDto.getName());
        c.setAddress(cinemaDto.getAddress());
        c.setPhone_number(cinemaDto.getPhone_number());

        cinemaRepository.save(c);
        return   mapper.map(c,CinemaDTO.class);

    }
    @Transactional
    public CinemaDTO remove(int cinemaId, int userId) {
        User u = userById(userId);
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional <Cinema>opt=cinemaRepository.findById(cinemaId);
        if(!opt.isPresent()){
            throw new NotFoundException("Cinema not found");
        }
        Cinema c=opt.get();
        cinemaRepository.delete(c);

        CinemaDTO cDto=mapper.map(c,CinemaDTO.class);
        return  cDto;
    }

    public HashSet<CinemaDTO> getAll() {
        HashSet<Cinema>cinemas=new HashSet<>();
        cinemas.addAll(cinemaRepository.findAll());
        HashSet<CinemaDTO>cinemaDTOS=new HashSet<>();
        for (Cinema c:cinemas){
            cinemaDTOS.add( mapper.map(c,CinemaDTO.class));
        }
        return cinemaDTOS;
    }

    public CinemaDTO getById(int id) {
        Optional<Cinema> opt = cinemaRepository.findById(id);
            if (opt.isPresent()) {
                Cinema u = opt.get();
                return    mapper.map(u, CinemaDTO.class);

            } else {
                throw new NotFoundException("Cinema not found");
            }
    }


    public List<AddCinemaDTO>filterByCity(int cityId) {
        List<Cinema>cinemas=new ArrayList<>();
        City city=cityRepository.findById(cityId).get();
        cinemas.addAll(cinemaRepository.findByCity(city));

        return cinemas.stream()
                .map(cinema -> mapper.map(cinema, AddCinemaDTO.class))
                .peek(addCinemaDTO -> addCinemaDTO.setCity_id(cityId))
                .collect(Collectors.toList());
    }
}

