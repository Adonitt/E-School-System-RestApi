package org.example.schoolmanagementsystem.helpers;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileHelperImpl implements FileHelper {
    @Override
    public String uploadFile(String folder, String fileName, byte[] fileContent) {
        try {
            var path = Paths.get(folder);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String newFileName = UUID.randomUUID() + "_" + fileName;
            Path pathToFile = Paths.get(path.toString(), newFileName);
            Files.write(pathToFile, fileContent);
            return newFileName;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
