package fastfood.controller;

import fastfood.config.CustomAuthenticationProvider;
import fastfood.config.CustomAuthenticationToken;
import fastfood.config.TokenProvider;
import fastfood.domain.LoginDTO;
import fastfood.domain.LoginResponse;
import fastfood.domain.ResponseCommonAPI;
import fastfood.domain.UserResponse;
import fastfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenController {
    @Autowired
    private CustomAuthenticationProvider authenticationManager;

    @Autowired
    private  TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        Object responseBody = null;
        HttpStatus httpStatus = null;

        final Authentication authentication = authenticationManager.authenticate(
                new CustomAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final  String token = tokenProvider.generateToken(authentication);

        UserResponse userResponse = (UserResponse) authentication.getPrincipal();

        responseBody = new LoginResponse(userResponse, token);

        ResponseCommonAPI res= new ResponseCommonAPI();
        res.setSuccess(true);
        res.setData(responseBody);
        return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
    }


}
