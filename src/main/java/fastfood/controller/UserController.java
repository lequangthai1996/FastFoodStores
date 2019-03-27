package fastfood.controller;

import fastfood.domain.LoginDTO;
import fastfood.domain.UserResponse;
import fastfood.service.UserService;
import fastfood.utils.GenerateKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${key.store.dir}")
    private  String keyDir;


    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public UserResponse createUser(@Valid  @RequestBody LoginDTO loginDTO) {
        return  userService.addUser(loginDTO);
    }

    @RequestMapping(value = "/createSale", method = RequestMethod.POST)
    public UserResponse createSale(@Valid  @RequestBody LoginDTO loginDTO) {
        return  userService.addSale(loginDTO);
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
    public UserResponse createAdmin(@Valid  @RequestBody LoginDTO loginDTO) {
        return  userService.addADMIN(loginDTO);
    }

    @RequestMapping("/generateKey")
    public String generatePairKey() {
        try{
            GenerateKeys.generatePrivatePublicKey(keyDir);
        }catch ( Exception e) {
            e.getMessage();
        }
        return "Success generekey in : " + keyDir;
    }

}
