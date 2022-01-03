package com.pcps.fakeReviewIdentifier.functionality;

import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageHelper {

    @Value("${delete.absolute.path}")
    static String imageParentPath;

    public static void deleteImage(String imgRelPath) throws Exception{

        if(!(imgRelPath.equals("/image/dummy.jpg"))){
            Files.deleteIfExists(Paths.get(imageParentPath+imgRelPath));
        }
    }
}
