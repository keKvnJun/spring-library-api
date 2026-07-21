package com.kevin.spring_library_api.service;

import com.kevin.spring_library_api.dto.BookRequestDTO;
import com.kevin.spring_library_api.dto.BookResponseDTO;
import com.kevin.spring_library_api.exception.BookNotFoundException;
import com.kevin.spring_library_api.exception.IsbnDuplicateException;
import com.kevin.spring_library_api.exception.IsbnNotFoundException;
import com.kevin.spring_library_api.model.Book;
import com.kevin.spring_library_api.model.Genre;
import com.kevin.spring_library_api.model.Language;
import com.kevin.spring_library_api.repository.BookRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookResponseDTO create (BookRequestDTO dto) {

        if (bookRepository.findByIsbn(dto.isbn()).isPresent()) {
            throw new IsbnDuplicateException(dto.isbn());
        }

        Book book = new Book();
        applyDtoToBook(dto, book);

        return toDTO(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long id) {
        return bookRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByGenres(Set<Genre> genres) {

        if (genres == null || genres.isEmpty()) {
            return findAll();
        }

        return bookRepository.findByAllGenres(genres, genres.size())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByLanguage(Language language) {
        return bookRepository.findByLanguage(language)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(this::toDTO)
                .orElseThrow(() -> new IsbnNotFoundException(isbn));
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByPriceLessThan(BigDecimal price) {
        return bookRepository.findByPriceLessThan(price)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByPublicationYear(int publicationYear) {
        return bookRepository.findByPublicationYear(publicationYear)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public BookResponseDTO update(Long id, BookRequestDTO dto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.findByIsbn(dto.isbn())
                .filter(foundBook -> !foundBook.getId().equals(id))
                .ifPresent(foundBook -> {
                    throw new IsbnDuplicateException(dto.isbn());
                });

        applyDtoToBook(dto, book);
        return toDTO(bookRepository.save(book));
    }

    @Transactional
    public void deleteById(Long id) {

        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundException(id);
        }

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
