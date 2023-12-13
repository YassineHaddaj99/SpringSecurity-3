package com.example.docsWebService.security.service;

import com.example.docsWebService.security.config.CustomUserDetails;
import com.example.docsWebService.security.dto.AuthRequest;
import com.example.docsWebService.security.dto.AuthResponse;
import com.example.docsWebService.security.dto.UserRequest;
import com.example.docsWebService.security.dto.UserResponse;
import com.example.docsWebService.security.entity.UserCredential;
import com.example.docsWebService.security.exception.AuthenticationFailedException;
import com.example.docsWebService.security.repository.UserCredentialRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserResponse saveUser(UserRequest userRequest) {
        UserCredential user = new UserCredential();
        BeanUtils.copyProperties(userRequest, user);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        UserCredential savedUser = repository.save(user);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, userResponse);

        return userResponse;
    }

    public AuthResponse AuthenticateAndGetToken(@RequestBody AuthRequest authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUserId(((CustomUserDetails) authentication.getPrincipal()).getId());
            authResponse.setUsername(((CustomUserDetails) authentication.getPrincipal()).getUsername());
            authResponse.setEmail(((CustomUserDetails) authentication.getPrincipal()).getEmail());
            authResponse.setToken(generateToken(authRequestDTO.getUsername()));
            authResponse.setRefreshToken(generateRefreshToken(authRequestDTO.getUsername()));
            return authResponse;
        } else {
            throw new AuthenticationFailedException("invalid credentials");
        }
    }

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUserId(((CustomUserDetails) authenticate.getPrincipal()).getId());
            authResponse.setUsername(((CustomUserDetails) authenticate.getPrincipal()).getUsername());
            authResponse.setEmail(((CustomUserDetails) authenticate.getPrincipal()).getEmail());
           // authResponse.setToken(generateToken(authRequest.getUsername()));
           // authResponse.setRefreshToken(generateRefreshToken(authRequest.getUsername()));

            return authResponse;
        } else {
            throw new AuthenticationFailedException("invalid credentials");
        }
    }
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    private String generateRefreshToken(String username) {
        return jwtService.generateRefreshToken(username);
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public Boolean validateRefreshToken(String refreshToken) {
        return jwtService.validateRefreshToken(refreshToken);
    }


}
