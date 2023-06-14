package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.UserWithoutPasswordDTO;
import com.example.kinoarenaproject.service.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
public class MediaController extends AbstractController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/media")
    public UserWithoutPasswordDTO upload(@RequestParam("file")MultipartFile file, HttpSession s){
        return mediaService.upload(file, loggedId(s));
    }

    @SneakyThrows
    @GetMapping("/media/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        File f = mediaService.download(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
    }
}
