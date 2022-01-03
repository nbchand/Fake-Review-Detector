package com.pcps.fakeReviewIdentifier.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.pcps.fakeReviewIdentifier.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.relative.path}")
    private String relativePath;

    private Path rootLocation;


    public String store(MultipartFile file, String id) {

        String storedLocation;
        rootLocation = Paths.get(uploadPath);

        //new file name
        String filename = id+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        System.out.println("Type of file "+file.getContentType());

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            if (!file.getContentType().equals("image/jpeg")&&!file.getContentType().equals("image/png")){
                throw new StorageException("Failed to upload \""+file.getContentType()+"\" file");
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            try{
                Thread.sleep(3000);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

            storedLocation = relativePath+filename;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return storedLocation;
    }
}
