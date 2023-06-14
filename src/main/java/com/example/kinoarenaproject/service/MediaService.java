package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.UserWithoutPasswordDTO;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class MediaService extends com.example.kinoarenaproject.service.Service {


    private static List allowedImageFormats = List.of("image/jpeg", "image/png", "image/pjpeg");

    public static boolean validFileFormat(MultipartFile file){
        if (file.isEmpty()) {
            throw new BadRequestException("The file is not attached. Please try again.");
        }
        if (! allowedImageFormats.contains(file.getContentType())) {
            throw new BadRequestException("Invalid file format. Only image files are allowed.");
        }
        return true;
    }

    @SneakyThrows
    public UserWithoutPasswordDTO upload(MultipartFile file, int userId) {
        User u = userById(userId);
        if (validFileFormat(file)) {

            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String name = UUID.randomUUID().toString() + "." + ext;
            File dir = new File("uploads");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(dir, name);
            Files.copy(file.getInputStream(), f.toPath());
            String url = dir.getName() + File.separator + f.getName();

            String oldUrl = u.getProfileImageUrl();
            if (oldUrl != null) {
                String fileName = oldUrl.substring(oldUrl.lastIndexOf(File.separator) + 1);
                File oldPhoto = new File(dir, fileName);
                if (oldPhoto.exists()) {
                    oldPhoto.delete();
                }
            }
            u.setProfileImageUrl(url);
            userRepository.save(u);
        }
            return mapper.map(u, UserWithoutPasswordDTO.class);

    }

    public File download(String fileName) {
        File dir = new File("uploads");
        File f = new File(dir, fileName);
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }
}

