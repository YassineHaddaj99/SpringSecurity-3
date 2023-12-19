package com.example.docsWebService.controller;

import com.example.docsWebService.dto.BookRequest;
import com.example.docsWebService.dto.BookResponse;
import com.example.docsWebService.service.BookService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAllBooks()
    {
        List<BookResponse> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<BookResponse>> findBookByAuthorId(@PathVariable @Min(value = 1, message = "Invalid author ID") Long id)
    {
        List<BookResponse> books = bookService.findBooksByAuthorId(id);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest)
    {
        BookResponse createdBook = bookService.createBook(bookRequest);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable @Min(value = 1, message = "Invalid book ID") Long id, @RequestBody BookRequest bookRequest)
    {
        BookResponse updatedBook = bookService.updateBook(id,bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
