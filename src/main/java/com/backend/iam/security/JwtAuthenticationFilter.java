package com.backend.iam.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger =  LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String AUTH_ERROR_HEADER = "X-AUTH-ERROR";
    private static final String TOKEN_EXPIRED = "token_expired";

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();
        return path.equals("");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7).trim();

        try{
            String username = jwtService.extractUsername(jwt);

            if(username != null
            && SecurityContextHolder.getContext().getAuthentication() == null
            ){
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if(userDetails.isEnabled() && jwtService.isTokenValid(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else if(!userDetails.isEnabled()){
                    logger.warn("Disabled account rejected for {} {}", request.getMethod(), request.getServletPath());
                    SecurityContextHolder.clearContext();
                }else  {
                    logger.warn("Invalid JWT rejected for {} {}", request.getMethod(), request.getServletPath());
                }
            }

        }catch(ExpiredJwtException e){

        }

    }
}
