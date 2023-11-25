package com.example.filehandler.utils;

import com.example.coreweb.utils.FileIO;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public final class ImageValidator {
    public static boolean isImageValid(MultipartFile multipartFile) {
        if (isSvgFile(multipartFile)) return true;
        try {
            Image image = ImageIO.read(FileIO.convertToFile(multipartFile));
            if (image != null)
                return true;
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public static boolean isSvgFile(MultipartFile file) {
        return hasSvgExtension(file) && isSvgMimeType(file);
    }

    private static boolean isSvgMimeType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("image/svg+xml");
    }

    private static boolean hasSvgExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename != null && originalFilename.toLowerCase().endsWith(".svg");
    }
}
