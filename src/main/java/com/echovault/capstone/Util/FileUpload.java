package com.echovault.capstone.Util;

import com.echovault.capstone.models.Echo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileUpload {
    @Value("${file-upload-path}")
    private String uploadPath;
    public static void savedFile(MultipartFile uploadedFile, Echo echo, @Value("${file-upload-path}") String uploadPath) {
        if (uploadedFile != null) {
            String filename = uploadedFile.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                uploadedFile.transferTo(destinationFile);
                echo.setProfileImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public static void backgroundFile(MultipartFile uploadedFile, Echo echo, @Value("${file-upload-path}") String uploadPath) {
        if (uploadedFile != null) {
            String filename = uploadedFile.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                uploadedFile.transferTo(destinationFile);
                echo.setBackgroundImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
