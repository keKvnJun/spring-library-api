package com.kevin.spring_library_api.controller;

import com.kevin.spring_library_api.dto.BookRequestDTO;
import com.kevin.spring_library_api.dto.BookResponseDTO;
import com.kevin.spring_library_api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO dto) {
        BookResponseDTO bookCreated = service.create(dto);
        return ResponseEntity.ok(bookCreated);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = service.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@RequestParam long id) {
        BookResponseDTO book = service.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<BookResponseDTO>> getBooksByTitle(@RequestParam String title) {
        List<BookResponseDTO> books = service.findByTitle(title);
        return ResponseEntity.ok(books);
    }

}
