package fastfood.controller;

import fastfood.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private FileStorageService fileStorageService;


    //@PreAuthorize("hasAnyRole('ROLE_SALE')")
    @RequestMapping(value = "/images", method = { RequestMethod.POST })
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {

        String imageName = null;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-type", "text/plain");
        if (file.isEmpty()) {
            request.setAttribute("message",
                    "Please select a file to upload");
            String message = "Please select a file to upload";
            return new ResponseEntity(message,responseHeaders, HttpStatus.BAD_REQUEST);
        }

        try {
            imageName = fileStorageService.storeFile(file);
            request.setAttribute("message",
                    "You have successfully uploaded '"
                            + file.getOriginalFilename() + "'");
            return new ResponseEntity(imageName,HttpStatus.OK);

        } catch (Exception e) {
            request.setAttribute("message",
                    "Upload fails");
            String message = "Upload fails";
            return new ResponseEntity(message,responseHeaders, HttpStatus.BAD_REQUEST);
        }

    }
}
