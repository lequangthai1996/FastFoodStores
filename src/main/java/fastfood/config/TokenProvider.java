package fastfood.config;

import exception.CustomException;
import fastfood.contant.Error;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements Serializable {
    private PrivateKey PRIVATE_KEY;
    private PublicKey PUBLIC_KEY;
    private String KEY_STORE_DIR;
    private String TOKEN_EXPIRATTION;
    private String HEADER_STRING;
    private String AUTHORITY_KEY;
    private String TOKEN_PREFIX;

    public String generateToken(Authentication authentication) throws  Exception {
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITY_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(TOKEN_EXPIRATTION)))
                .compact();
    }

    UsernamePasswordAuthenticationToken getAuthentication(final  String token, final UserDetails userDetails) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(PUBLIC_KEY);

        final Jws<Claims> claimsJws  = jwtParser.parseClaimsJws(token);

        final  Claims claims = claimsJws.getBody();


        final Collection<? extends  GrantedAuthority> authorities  = Arrays.stream(
                claims.get(AUTHORITY_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean checkTokenIsValid(final String token) {
        try {
            Jwts.parser().setSigningKey(PUBLIC_KEY).parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e ) {
            throw  new CustomException(Error.TOKEN_INVALID.getCode(), Error.TOKEN_INVALID.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(PUBLIC_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public UserDetails getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            return (UserDetails) principal;
        }
        return null;
    }
}
