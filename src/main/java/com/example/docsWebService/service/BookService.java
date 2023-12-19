package com.example.docsWebService.service;

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
    private final AuthorService authorService;

        public List<BookResponse> findBooksByAuthorId(Long id){
    Author authorExist = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    if (authorExist !=null)
    {
        List<Book> books = bookRepository.findBooksByAuthorId(id);
        return  books.stream()
                .map(this::convertBookToResponse)
                .collect(Collectors.toList());
    }
    else {
        return null;
    }
    }

    public List<BookResponse> findAllBooks()
    {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertBookToResponse)
                .collect(Collectors.toList());
    }

    public BookResponse createBook(BookRequest bookRequest)
    {
        Book book = buildBookFromRequest(bookRequest);
        Book builtBook = bookRepository.save(book);
        return convertBookToResponse(builtBook);
    }

    public BookResponse updateBook(Long id,BookRequest bookRequest)
    {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        updateBookFromRequest(book,bookRequest);
        Book updatedBook = bookRepository.save(book);
        return convertBookToResponse(updatedBook);
    }

    public void deleteBook(Long id)
    {
        bookRepository.deleteById(id);
    }

    private void updateBookFromRequest(Book book, BookRequest bookRequest)
    {
        Author authorById = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(EntityNotFoundException::new);
        book.setName(bookRequest.getName());
        book.setDescription(bookRequest.getDescription());
        book.setAuthor(authorById);
    }

    private BookResponse convertBookToResponse(Book book)
    {
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .author(authorService.convertAuthorToResponse(book.getAuthor()))
                .build();
    }

    private Book buildBookFromRequest(BookRequest bookRequest)
    {
        Author authorById = authorRepository.findById(bookRequest.getAuthorId()).orElseThrow(EntityNotFoundException::new);
        return Book.builder()
                .name(bookRequest.getName())
                .description(bookRequest.getDescription())
                .author(authorById)
                .build();
    }



}
