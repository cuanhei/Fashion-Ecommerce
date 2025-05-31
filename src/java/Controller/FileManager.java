/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.servlet.http.Part;
/**
 *
 * @author LIM CUAN HEI
 */
public class FileManager {
    
    // Upload an image
    public static String uploadImage(String uploadDir, Part filePart) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalName = filePart.getSubmittedFileName();
        String fileType = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();

        if (!(fileType.equals("jpg") || fileType.equals("png") || fileType.equals("jpeg"))) {
            return null;
        }

        String newFileName = UUID.randomUUID().toString() + "." + fileType;
        String fullPath = uploadDir + File.separator + newFileName;

        try (InputStream input = filePart.getInputStream();
             FileOutputStream output = new FileOutputStream(fullPath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        }

        return newFileName;
    }


    // Delete a file
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    
}
