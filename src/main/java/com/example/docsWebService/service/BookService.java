package com.example.docsWebService.service;

import com.example.docsWebService.dto.AuthorRequest;
import com.example.docsWebService.dto.AuthorResponse;
import com.example.docsWebService.dto.BookRequest;
import com.example.docsWebService.dto.BookResponse;
import com.example.docsWebService.entities.Author;
import com.example.docsWebService.entities.Book;
import com.example.docsWebService.repositories.AuthorRepository;
import com.example.docsWebService.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<BookResponse> findBooksByAuthorName(String authorName){
    Author authorExist = authorRepository.findAuthorByName(authorName);
    if (authorExist !=null)
    {
        List<Book> books = bookRepository.findBooksByAuthorName(authorName);
        return  books.stream()
                .map(this::convertBookToResponse)
                .collect(Collectors.toList());
    }
    else {
        return null;
    }
    }

    public AuthorResponse createAuthor(AuthorRequest authorRequest){
       Author author = buildAuthorFromRequest(authorRequest);
       Author builtAuthor = authorRepository.save(author);
       return convertAuthorToResponse(builtAuthor);
    }

    public BookResponse createBook(BookRequest bookRequest)
    {
        Book book = buildBookFromRequest(bookRequest);
        Book builtBook = bookRepository.save(book);
        return convertBookToResponse(builtBook);
    }

    public BookResponse updateBook(BookRequest bookRequest)
    {
        Book book = buildBookFromRequest(bookRequest);
        Book builtBook = bookRepository.save(book);
        return convertBookToResponse(builtBook);
    }


    public BookResponse convertBookToResponse(Book book)
    {
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .author(convertAuthorToResponse(book.getAuthor()))
                .build();
    }

    public AuthorResponse convertAuthorToResponse(Author author)
    {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    public Book buildBookFromRequest(BookRequest bookRequest)
    {
        Author authorById = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(EntityNotFoundException::new);
        return Book.builder()
                .name(bookRequest.getName())
                .description(bookRequest.getDescription())
                .author(authorById)
                .build();
    }

    public Author buildAuthorFromRequest(AuthorRequest authorRequest)
    {
        return Author.builder()
                .name(authorRequest.getName())
                .build();
    }

}
