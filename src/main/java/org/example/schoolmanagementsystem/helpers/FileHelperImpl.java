package org.example.schoolmanagementsystem.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileHelperImpl implements FileHelper {

    @Override
    public String uploadFile(MultipartFile imageFile) {
        String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path uploadDir = Paths.get("uploads");
        try {
            Files.createDirectories(uploadDir);
            Path imagePath = uploadDir.resolve(filename);
            Files.write(imagePath, imageFile.getBytes());
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
    }
}
