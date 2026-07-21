package com.kevin.spring_library_api.controller;

import com.kevin.spring_library_api.dto.BookRequestDTO;
import com.kevin.spring_library_api.dto.BookResponseDTO;
import com.kevin.spring_library_api.service.BookService;

import com.kevin.spring_library_api.controller.swagger.BookControllerApi;
import com.kevin.spring_library_api.model.Language;
import com.kevin.spring_library_api.model.Genre;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/books")
public class BookController implements BookControllerApi {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO dto) {
        BookResponseDTO bookCreated = service.create(dto);
        URI location = URI.create(String.format("/api/books/%s", bookCreated.id()));
        return ResponseEntity.created(location).body(bookCreated);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = service.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable long id) {
        BookResponseDTO book = service.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByTitle(@PathVariable String title) {
        List<BookResponseDTO> books = service.findByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByAuthor(@PathVariable String author) {
        List<BookResponseDTO> books = service.findByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/publisher/{publisher}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByPublisher(@PathVariable String publisher) {
        List<BookResponseDTO> books = service.findByPublisher(publisher);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByLanguage(@PathVariable Language language) {
        List<BookResponseDTO> books = service.findByLanguage(language);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponseDTO> getBooksByIsbn(@PathVariable String isbn) {
        BookResponseDTO book = service.findByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByPriceLessThan(@PathVariable BigDecimal price) {
        List<BookResponseDTO> books = service.findByPriceLessThan(price);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/publicationYear/{year}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByPublicationYear(@PathVariable int year) {
        List<BookResponseDTO> books = service.findByPublicationYear(year);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<BookResponseDTO>> getBooksByGenres(@RequestParam(required = false) Set<Genre> genre) {
        List<BookResponseDTO> books = service.findByGenres(genre);
        return ResponseEntity.ok(books);
    }
    // URL example: /api/books/genres?genre=FICTION&genre=SCIENCE_FICTION

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable long id, @Valid @RequestBody BookRequestDTO dto) {
        BookResponseDTO updatedBook = service.update(id, dto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
