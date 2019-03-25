package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.domain.LoginDTO;
import fastfood.domain.UserResponse;
import fastfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private  TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    public ResponseEntity<Object> login(@Valid LoginDTO loginDTO) throws Exception {
        Object responseBody = null;
        HttpStatus httpStatus = null;

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassWord()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final  String token = tokenProvider.generateToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserResponse userResponse

        return new ResponseEntity<Object>(responseBody, httpStatus);
    }


}
