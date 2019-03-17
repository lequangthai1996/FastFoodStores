package fastfood.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.CustomException;
import fastfood.domain.ResponeCommonAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenProvider.resolveToken(request);
        try {
            if (token != null && tokenProvider.checkTokenIsValid(token)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(tokenProvider.getUsernameFromToken(token));
                Authentication auth = tokenProvider.getAuthentication(token, userDetails);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException ex ) {
            SecurityContextHolder.clearContext();
            ResponeCommonAPI responeCommonAPI = new ResponeCommonAPI();
            responeCommonAPI.setError(Collections.singletonList(new CustomException(null, ex.getMessage(), ex.getHttpStatus())));
            responeCommonAPI.setSuccess(false);

            response.setStatus(ex.getHttpStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(responeCommonAPI));
            return;
        }
        filterChain.doFilter(request,response);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}