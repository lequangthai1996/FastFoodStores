package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.domain.*;
import fastfood.entity.ItemEntity;
import fastfood.service.CategoryService;
import fastfood.service.ItemService;
import fastfood.service.impl.FileStorageService;
import fastfood.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CategoryService categoryService;


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


            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            String base64Encode = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));
            return base64Encode;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //@PreAuthorize("hasAnyRole('ROLE_SALE')")
    @PostMapping("/create")
    public ResponseEntity<ResponseCommonAPI> addProduct(@RequestBody ItemDTO itemDTO) {

        //  itemDTO.setCurrentUserId(tokenProvider.getCurrentUserLogin() != null ? tokenProvider.getCurrentUserLogin().getId() : null);
        itemDTO.setSupplier_id(16l);
        itemDTO.setCurrentUserId("16");
        ResponseCommonAPI res = new ResponseCommonAPI();
        String avatar = null;

        try {

            if (itemService.addItem(itemDTO)) {
                res.setSuccess(true);

            } else {
                res.setSuccess(false);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }
        return ResponseEntity.ok().body(res);
    }

    @RequestMapping("/all")
    public ResponseEntity<ResponseCommonAPI> getAll(@RequestParam("page") int page,
                               @RequestParam("size") int size,
                               @RequestParam("sort") String sort,
                               @RequestParam(value = "category", required = false) String category
    ) {

        //String currentUserID = tokenProvider.getCurrentUserLogin().getId();
        String currentUserID = "16";
        ResponseCommonAPI res = new ResponseCommonAPI();
        Page<ItemVO>  resultPage = null;
        try {
            if (category == null) {
                resultPage = itemService.searchItems(page, size, sort, Long.parseLong(currentUserID));
                if (page > resultPage.getTotalPages()) {
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                res.setSuccess(true);
                res.setData(resultPage);
            } else {
                Integer categoryId = Integer.parseInt(category);
                resultPage = itemService.searchItemsByCategory(Long.parseLong(currentUserID), categoryId, page, size, sort);
                if (page > resultPage.getTotalPages()) {
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                res.setSuccess(true);
                res.setData(resultPage);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }


        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        }else{
            return ResponseEntity.badRequest().body(res);
        }
    }


}













