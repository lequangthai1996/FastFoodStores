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
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS, TRACE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Cache-Control, X-Requested-With, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addIntHeader("Access-Control-Max-Age", 3600);

        String token = tokenProvider.resolveToken(request);
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
