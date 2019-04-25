package fastfood.service.impl;

import fastfood.exception.FileStorageException;
import fastfood.exception.MyFileNotFoundException;
import fastfood.utils.RenameFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class FileStorageService {

    @Value("${file.upload.dir}")
    private  String pathFile;

    private  Path fileStorageLocation;


    public String storeFile(MultipartFile file) {
        fileStorageLocation = Paths.get(pathFile).toAbsolutePath().normalize();
        String fileName = RenameFile.renameFile(StringUtils.cleanPath(file.getOriginalFilename()));

        try{
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence : " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return  fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file : "+ fileName + ". Please try again! ", ex);

        }
    }

    public Resource loadFileResource(String fileName) {
        fileStorageLocation = Paths.get(pathFile).toAbsolutePath().normalize();
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return  resource;
            } else {
                throw new MyFileNotFoundException("File not found: "+ fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found: " + fileName, ex);
        }
    }

    public String loadImageBase64(String fileName) {
        Resource resource = this.loadFileResource(fileName);
        InputStream inputStream = null;
        String mimeType = null;
        StringBuilder base64Encode = new StringBuilder();
        try {
            inputStream = resource.getInputStream();

            mimeType = URLConnection.guessContentTypeFromStream(inputStream);

            base64Encode = base64Encode.append("data:").append(mimeType).append(";base64,").append(Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream)));
            return base64Encode.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
