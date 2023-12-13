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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
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

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUserId(((CustomUserDetails) authenticate.getPrincipal()).getId());
            authResponse.setUsername(((CustomUserDetails) authenticate.getPrincipal()).getUsername());
           // authResponse.setEmail(((CustomUserDetails) authenticate.getPrincipal()).getEmail());
           // authResponse.setToken(generateToken(authRequest.getUsername()));
           // authResponse.setRefreshToken(generateRefreshToken(authRequest.getUsername()));

            return authResponse;
        } else {
            throw new AuthenticationFailedException("invalid credentials");
        }
    }


}
