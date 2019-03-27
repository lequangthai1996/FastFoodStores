package fastfood.config;

import fastfood.contant.Error;
import fastfood.domain.UserResponse;
import fastfood.exception.CustomException;
import fastfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationCustom = (UsernamePasswordAuthenticationToken) authentication;
        String username = authenticationCustom.getPrincipal().toString();
        String password = authenticationCustom.getCredentials().toString();
        UserResponse userResponse = userService.getUserByUserName(username);
        if(userResponse == null ){
            throw new CustomException(Error.BAD_CREDENTIALS.getCode(), Error.BAD_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        } else {
            if(bCryptPasswordEncoder.matches(password, userResponse.getPassword())) {
                Set<SimpleGrantedAuthority> authorities = new HashSet<>();
                userResponse.getAuthorities().forEach(roleDTO -> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleDTO.getName()));
                });

                return new CustomAuthenticationToken(userResponse, password, authorities);
            }
            throw new CustomException(Error.BAD_CREDENTIALS.getCode(), Error.BAD_CREDENTIALS.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
