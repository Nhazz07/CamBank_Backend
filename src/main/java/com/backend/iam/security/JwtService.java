package com.backend.iam.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }
    public String generateToken(CustomUserDetails userDetails){
        String role = userDetails
                .getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("");

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis() + expiration
                ))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    
    public String extractRole(String token){
        return extractClaim(
                token,
                claims -> claims.get("role", String.class)
        );
    }
    
    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ){
        String username = extractUsername(token);
        
        return username.equals(userDetails.getUsername())
        && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extractException(token).before(new Date());
    }

    private Date extractException(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T>  T extractClaim(
            String token,
            Function<Claims, T> claimsTFunction
    ) {
        Claims cLaims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsTFunction.apply(cLaims);
    }
}
