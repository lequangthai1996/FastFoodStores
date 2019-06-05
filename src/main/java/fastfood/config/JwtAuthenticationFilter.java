package fastfood.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fastfood.domain.ErrorCustom;
import fastfood.exception.CustomException;
import fastfood.domain.ResponseCommonAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenProvider.resolveToken(request);

        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Cache-Control, X-Requested-With, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS");

        try {
            if (token != null && tokenProvider.checkTokenIsValid(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException ex ) {
            SecurityContextHolder.clearContext();
            ResponseCommonAPI responseCommonAPI = new ResponseCommonAPI();
            responseCommonAPI.setError(Collections.singletonList(new ErrorCustom("", ex.getCode(), ex.getMessage()) ) );
            responseCommonAPI.setSuccess(false);

            response.setStatus(ex.getHttpStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(responseCommonAPI));
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
