package com.example.docsWebService.service;

import com.example.docsWebService.dto.AuthorRequest;
import com.example.docsWebService.dto.AuthorResponse;
import com.example.docsWebService.entities.Author;
import com.example.docsWebService.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorResponse createAuthor(AuthorRequest authorRequest)
    {
        Author author = buildAuthorFromRequest(authorRequest);
        Author builtAuthor = authorRepository.save(author);
        return convertAuthorToResponse(builtAuthor);
    }

    public AuthorResponse updateAuthor(Long Id, AuthorRequest authorRequest)
    {
        Author author = authorRepository.findById(Id).orElseThrow(EntityNotFoundException::new);
        updateAuthorFromRequest(author,authorRequest);
        Author updatedAuthor = authorRepository.save(author);
        return convertAuthorToResponse(updatedAuthor);
    }

    public void deleteAuthor(Long id)
    {
        authorRepository.deleteById(id);
    }


    private void updateAuthorFromRequest(Author author,AuthorRequest authorRequest)
    {
        author.setName(authorRequest.getName());
    }

    private Author buildAuthorFromRequest(AuthorRequest authorRequest)
    {
        return Author.builder()
                .name(authorRequest.getName())
                .build();
    }

    protected AuthorResponse convertAuthorToResponse(Author author)
    {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }
}
