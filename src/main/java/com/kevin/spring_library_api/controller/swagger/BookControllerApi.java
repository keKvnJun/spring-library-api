package com.kevin.spring_library_api.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kevin.spring_library_api.model.Genre;
import com.kevin.spring_library_api.model.Language;
import com.kevin.spring_library_api.dto.BookRequestDTO;
import com.kevin.spring_library_api.dto.BookResponseDTO;
import com.kevin.spring_library_api.handler.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import jakarta.validation.Valid;

@Tag(name = "Book Controller", description = "API for managing books in the library")
public interface BookControllerApi {

    @Operation(summary = "Add a book", description = "Add a new book to the library")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book added successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "409", description = "ISBN already exists", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
    })
    ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO dto);

    @Operation(summary = "Get all books", description = "Show all books in the library")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getAllBooks();

    @Operation(summary = "Get a book by ID", description = "Search a book from the library using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
    })
    ResponseEntity<BookResponseDTO> getBookById(@PathVariable long id);

    @Operation(summary = "Get a book by title", description = "Search a book from the library using its title")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getBooksByTitle(@PathVariable String title);

    @Operation(summary = "List books by author", description = "Search books using their author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getBooksByAuthor(@PathVariable String author);

    @Operation(summary = "List books by publisher", description = "Search books using their publisher")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getBooksByPublisher(@PathVariable String publisher);

    @Operation(summary = "List books by genre", description = "Search books using their genre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getBooksByGenres(@RequestParam(required = false) Set<Genre> genre);

    @Operation(summary = "List books by language", description = "Search books using their language")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getBooksByLanguage(@PathVariable Language language);

    @Operation(summary = "Get a book by ISBN", description = "Search a book using its ISBN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
    })
    ResponseEntity<BookResponseDTO> getBooksByIsbn(@PathVariable String isbn);

    @Operation(summary = "List all books by price less than a specified amount", description = "List all books in the library filtered by price")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getBooksByPriceLessThan(@PathVariable BigDecimal price);

    @Operation(summary = "List all books by publication year", description = "List all books in the library filtered by publication year")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
    })
    ResponseEntity<List<BookResponseDTO>> getBooksByPublicationYear(@PathVariable int year);

    @Operation(summary = "Update a book", description = "Update an existing book in the library using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully", content = {@Content(schema = @Schema(implementation = BookResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "409", description = "ISBN already exists", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
    })
    ResponseEntity<BookResponseDTO> updateBook(@PathVariable long id, @Valid @RequestBody BookRequestDTO dto);

    @Operation(summary = "Delete a book", description = "Delete an existing book from the library using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = {@Content(schema = @Schema(implementation = ApiError.class))}),
    })
    ResponseEntity<Void> deleteBook(@PathVariable long id);
}
