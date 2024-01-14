package com.ccprojectauth.ccauth.controller;

import com.ccprojectauth.ccauth.model.*;
import com.ccprojectauth.ccauth.service.RoleService;
import com.ccprojectauth.ccauth.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private RoleService roleService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authenticationRequest) {
        try {
            // Authenticate credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token when  authentication is successful
            String token = jwtToken.generateToken(authentication);
            // Store token in session
            httpSession.setAttribute("jwtToken", token);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException ex) {
            logger.error("Error sign in user");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {
        try {
            // Check if username is already taken
            if (userService.findUserByUsername(request.getUsername()) != null ) {
                return ResponseEntity.badRequest().body("Username is already taken");
            }

            Set<Role> roleSet = new HashSet<>();

            if (isValidRole(request.getType())) {
                roleSet.add(roleService.findByName("ROLE_" + request.getType().toUpperCase()));
            }

            User user = User
                    .builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .firstName(request.getFirstname())
                    .lastName(request.getLastname())
                    .roles(roleSet)
                    .build();

            // Register the user
            userService.registerUser(user);

            // Return the registered user with a success message
            return ResponseEntity.ok().body("User registered successfully");
        } catch (Exception ex) {
            logger.error("error sign up");
            return ResponseEntity.internalServerError().body("error");
        }

    }

    private boolean isValidRole(String roleStr) {
        return roleStr.equalsIgnoreCase("USER") || roleStr.equalsIgnoreCase("ADMIN") || roleStr.equalsIgnoreCase("ORGANISER");
    }

}