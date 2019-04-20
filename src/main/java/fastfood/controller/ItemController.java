package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.domain.ItemDTO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.domain.UploadFileResponse;
import fastfood.service.ItemService;
import fastfood.service.impl.FileStorageService;
import fastfood.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TokenProvider tokenProvider;


    @PreAuthorize("hasAnyRole('ROLE_SALE')")
    @PostMapping("/upload")
    public UploadFileResponse uploadImage(MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/items/downloadFile/").path(fileName).toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public String downLoadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileResource(fileName);
        InputStream inputStream = null;
        String contentType = null;
        try {
            inputStream = resource.getInputStream();
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());


            if(contentType == null) {
                contentType = "application/octet-stream";
            }

            String base64Encode = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));
            return base64Encode;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_SALE')")
    @PostMapping("/add")
    public ResponseEntity<ResponseCommonAPI> addProduct(ItemDTO itemDTO, MultipartFile file) {

        itemDTO.setCurrentUserId(tokenProvider.getCurrentUserLogin() != null ? tokenProvider.getCurrentUserLogin().getId() : null);
        ResponseCommonAPI res = new ResponseCommonAPI();
        String avatar = null;

        try {
            if (file != null && !file.isEmpty()) {
                avatar = fileStorageService.storeFile(file);
            }
            itemDTO.setAvatar(!StringUtils.isEmpty(avatar) ? avatar : null);
            if (itemService.addItem(itemDTO)) {
                res.setSuccess(true);

            } else {
                res.setSuccess(false);
            }
        } catch (Exception e) {
            res.setSuccess(false);
        }
        return ResponseEntity.ok().body(res);
    }
}
