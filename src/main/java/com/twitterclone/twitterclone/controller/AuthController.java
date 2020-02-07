package com.twitterclone.twitterclone.controller;

import com.twitterclone.twitterclone.db.models.User;
import com.twitterclone.twitterclone.db.repositories.UserRepository;
import com.twitterclone.twitterclone.security.JwtTokenProvider;
import com.twitterclone.twitterclone.security.payload.ApiResponse;
import com.twitterclone.twitterclone.security.payload.JwtAuthenticationResponse;
import com.twitterclone.twitterclone.security.payload.LoginRequest;
import com.twitterclone.twitterclone.security.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmailAddress(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), 0, 0);

        user.setCreatedAt(LocalDateTime.now());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
