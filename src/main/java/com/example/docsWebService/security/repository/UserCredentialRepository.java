package com.example.docsWebService.security.repository;

import com.example.docsWebService.security.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    UserCredential findByUsername(String username);
}
