package com.example.docsWebService.controller;


import com.example.docsWebService.dto.AuthorRequest;
import com.example.docsWebService.dto.AuthorResponse;
import com.example.docsWebService.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorRequest authorRequest)
    {
        AuthorResponse createdAuthor = authorService.createAuthor(authorRequest);
        return ResponseEntity.ok(createdAuthor);
    }
}
