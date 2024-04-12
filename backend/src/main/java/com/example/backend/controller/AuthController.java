package com.example.backend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid ;

import com.example.backend.DTO.MessageResponse;
import com.example.backend.DTO.RefreshTokenRequest;
import com.example.backend.DTO.SigninRequest;
import com.example.backend.DTO.SignupRequest;
import com.example.backend.DTO.TokenRefreshResponse;
import com.example.backend.DTO.UserInfoResponse;
import com.example.backend.models.ERole;
import com.example.backend.models.RefreshToken;
import com.example.backend.models.Role;
import com.example.backend.models.User;
import com.example.backend.security.JwtUtils;
import com.example.backend.security.RefreshTokenService;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserDetailsImpl;



@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest data) {
        
        if (authService.existsByUsername(data.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authService.existsByEmail(data.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(data.getUsername(),  encoder.encode(data.getPassword()), data.getEmail());

        Set<String> strRoles = data.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = authService.findRoleByName(ERole.ROLE_USER);

            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = authService.findRoleByName(ERole.ROLE_ADMIN);
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = authService.findRoleByName(ERole.ROLE_MODERATOR);
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = authService.findRoleByName(ERole.ROLE_USER);
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        authService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid SigninRequest data) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(
                    jwtCookie.toString(),
                    refreshToken.getToken(),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles
                ));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(requestRefreshToken);

        if (!refreshTokenOptional.isPresent()) {
            return ResponseEntity.ok(new TokenRefreshResponse("Unkown Refresh token...!", requestRefreshToken));
        }

        RefreshToken refreshToken = refreshTokenOptional.get();
        refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();
        String token = jwtUtils.generateTokenFromUsername(user.getUsername());

        // Invalidate the refresh token after it's used
        refreshTokenService.invalidateRefreshToken(requestRefreshToken);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, token).body(new TokenRefreshResponse(token, requestRefreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody String refreshToken ,HttpServletRequest request) {
        // Invalidate the user's session
        request.getSession().invalidate();

        // Clear the authentication context
        SecurityContextHolder.getContext().setAuthentication(null);

        // Invalidate the refresh token
        refreshTokenService.invalidateRefreshToken(refreshToken);

        // Clean up the JWT cookie
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new MessageResponse("Logout successful!"));
    }
}
