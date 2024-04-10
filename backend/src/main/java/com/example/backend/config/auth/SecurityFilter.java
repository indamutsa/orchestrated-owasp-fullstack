package com.example.backend.config.auth;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);
        try { 
            if (token != null) {
                var username = tokenProvider.validateToken(token);
                var user = userRepository.findByUsername(username);
                System.out.println(user);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        // Retrieve "secret" cookie from request
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
                    System.out.println("Cookies: " + cookies);
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("secret")) {
                    System.out.println("Secret cookie: " + cookie.getValue());
                }
            }
        }
  
            Cookie cookie = getCookie("secret");
            response.addCookie(cookie);

            filterChain.doFilter(request, response);
        
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
    
    private Cookie getCookie(String secret) throws NoSuchAlgorithmException {

        // Create hash
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(secret.getBytes(StandardCharsets.UTF_8));

        // Convert hash to hex string
        String hashedSecret = String.format("%064x", new BigInteger(1, hash));

        // Create cookie with hashed secret
        Cookie cookie = new Cookie("secret", hashedSecret);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60);
        cookie.setDomain("localhost");

        return cookie;
    }
}
