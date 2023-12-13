package com.example.docsWebService.security.repository;

import com.example.docsWebService.security.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    UserCredential findByUsername(String username);
}
