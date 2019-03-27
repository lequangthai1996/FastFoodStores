package fastfood.controller;

import fastfood.domain.LoginDTO;
import fastfood.domain.UserDTO;
import fastfood.domain.UserResponse;
import fastfood.entity.UserEntity;
import fastfood.service.UserService;
import fastfood.utils.GenerateKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
