package org.auth.controller;

import org.auth.model.*;
import org.auth.service.RoleService;
import org.auth.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import org.service.customer.pojo.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
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
    @Autowired
    private RestClient restClient;


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authenticationRequest) {
        try {
            // Authenticate credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            User userByUsername = userService.findUserByUsername(authenticationRequest.getUsername());

            UsernamePasswordAuthenticationToken authenticationReq = new UsernamePasswordAuthenticationToken(
                    userByUsername, null, userByUsername.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationReq);

            // Generate token when  authentication is successful
            String token = jwtToken.generateToken(authentication);
            // Store token in session
            httpSession.setAttribute("jwtToken", token);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException ex) {
            logger.error("Error sign in user");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {
        try {
            // Check if username is already taken
            if (userService.findUserByUsername(request.getUsername()) != null) {
                return ResponseEntity.badRequest().body("Username is already taken");
            }

            Set<Role> roleSet = new HashSet<>();

            if (isValidRole(request.getType())) {
                roleSet.add(roleService.findByName(request.getType().toUpperCase()));
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
            //TODO: generate token and store in session here
            userService.registerUser(user);
            CustomerDto customerDto = CustomerDto
                    .builder()
                    .build();

            restClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(customerDto);

            return ResponseEntity.ok(new SignupResponse(true, "User registered successfully", null));
        } catch (Exception ex) {
            logger.error("error sign up");
            return ResponseEntity.internalServerError().body("error");
        }

    }

    private boolean isValidRole(String roleStr) {
        return roleStr.equalsIgnoreCase("USER") || roleStr.equalsIgnoreCase("ADMIN") || roleStr.equalsIgnoreCase("ORGANISER");
    }
}
