package com.kevin.spring_library_api.service;

import com.kevin.spring_library_api.dto.BookRequestDTO;
import com.kevin.spring_library_api.dto.BookResponseDTO;
import com.kevin.spring_library_api.model.Book;
import com.kevin.spring_library_api.model.Genre;
import com.kevin.spring_library_api.model.Language;
import com.kevin.spring_library_api.repository.BookRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponseDTO create (BookRequestDTO dto) {
        Book book = new Book();
        applyDtoToBook(dto, book);

        return toDTO(bookRepository.save(book));
    }

    public List<BookResponseDTO> findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<BookResponseDTO> findByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<BookResponseDTO> findByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<BookResponseDTO> findByGenres(Set<Genre> genres) {
        return bookRepository.findByAllGenres(genres, genres.size())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<BookResponseDTO> findByLanguage(Language language) {
        return bookRepository.findByLanguage(language)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public BookResponseDTO findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
    }

    public List<BookResponseDTO> findByPriceLessThan(BigDecimal price) {
        return bookRepository.findByPriceLessThan(price)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<BookResponseDTO> findByPublicationYear(int publicationYear) {
        return bookRepository.findByPublicationYear(publicationYear)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public BookResponseDTO update(Long id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        applyDtoToBook(dto, book);

        return toDTO(bookRepository.save(book));
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private BookResponseDTO toDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPrice(),
                book.getPublicationYear(),
                book.getNumberOfPages(),
                book.getIsbn(),
                book.getLanguage(),
                book.getGenres()
        );
    }

    private void applyDtoToBook(BookRequestDTO dto, Book book) {
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setPublisher(dto.publisher());
        book.setPrice(dto.price());
        book.setPublicationYear(dto.publicationYear());
        book.setNumberOfPages(dto.numberOfPages());
        book.setIsbn(dto.isbn());
        book.setLanguage(dto.language());
        book.setGenres(dto.genres());
    }
}
