package com.example.docsWebService.security.controller;


import com.example.docsWebService.security.dto.*;
import com.example.docsWebService.security.service.AuthService;
import com.example.docsWebService.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final  JwtService jwtService ;
    private final  AuthenticationManager authenticationManager;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addNewUser(@RequestBody UserRequest userRequest) {
        return authService.saveUser(userRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("v1/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequest authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
        } else {
            System.out.print("not working BITCH");

            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

//    @GetMapping("validate-token")
//    public ResponseEntity validateToken(@RequestParam("token") String token) {
//        if (authService.validateToken(token))
//            return ResponseEntity.ok().body(Map.of("Token-validation", true));
//
//        return ResponseEntity.badRequest().body(Map.of("Token-validation", false));
//    }
//
//    @GetMapping("generate-new-token")
//    public ResponseEntity generateNewToken(@RequestParam("refreshToken") String refreshToken, @RequestParam("username") String username) {
//        if (authService.validateRefreshToken(refreshToken))
//            return ResponseEntity.ok().body(Map.of("token", authService.generateToken(username)));
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "invalidate refresh token"));
//    }
}
