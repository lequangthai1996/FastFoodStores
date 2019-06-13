package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.domain.*;
import fastfood.entity.UserEntity;
import fastfood.service.UserService;
import fastfood.service.impl.FileStorageService;
import fastfood.utils.GenerateKeys;
import fastfood.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${key.store.dir}")
    private  String keyDir;

    @Autowired
    private FileStorageService fileStorageService;


    @Autowired
    private TokenProvider tokenProvider;

    @GetMapping("/getDetail")
    public ResponseEntity<ResponseCommonAPI> getUserDetail() {
        String currentUserID = tokenProvider.getCurrentUserLogin().getId();

        ResponseCommonAPI res = new ResponseCommonAPI();
        try {
            UserResponse userResponse = userService.getUserDetail(StringUtils.convertStringToLongOrNull(currentUserID));
            if(!StringUtils.isEmpty(userResponse.getAvatar())) {
                userResponse.setAvatar(fileStorageService.loadImageBase64(userResponse.getAvatar()));
            }

            res.setSuccess(true);
            res.setData(userResponse);
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseCommonAPI> updateUserInfo(UserInfoDTO userInfoDTO, @RequestParam(value = "avatar", required = false) MultipartFile avatar) {


        String currentUserId = tokenProvider.getCurrentUserLogin().getId();

        userInfoDTO.setUserId(currentUserId);
        ResponseCommonAPI res = new ResponseCommonAPI();
        try {
            UserResponse userResponse = userService.updateUserInfo(userInfoDTO, avatar);
            res.setSuccess(true);
            res.setData(userResponse);
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public UserResponse createUser(@Valid  @RequestBody LoginDTO loginDTO) {
        return  userService.addUser(loginDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/createSale", method = RequestMethod.POST)
    public UserResponse createSale(@Valid  @RequestBody LoginDTO loginDTO) {
        return  userService.addSale(loginDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
    public UserResponse createAdmin(@Valid  @RequestBody LoginDTO loginDTO) {
        return  userService.addADMIN(loginDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping("/generateKey")
    public String generatePairKey() {
        try{
            GenerateKeys.generatePrivatePublicKey(keyDir);
        }catch ( Exception e) {
            e.getMessage();
        }
        return "Success generekey in : " + keyDir;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SALE')")
    @RequestMapping(value = "/list", method =  RequestMethod.GET)
    public ResponseEntity<Object> getListUser() {
        try {
            List<UserEntity> list = userService.getAllUser();
            List<UserDTO>  listResponse = list.stream().map(userEntity -> userEntity.convertToUserDTO()).collect(Collectors.toList());
            return  ResponseEntity.ok(listResponse);
        } catch ( Exception e) {
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }

}
