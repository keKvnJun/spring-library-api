package com.kevin.spring_library_api.service;

import com.kevin.spring_library_api.dto.BookRequestDTO;
import com.kevin.spring_library_api.dto.BookResponseDTO;
import com.kevin.spring_library_api.exception.BookNotFoundException;
import com.kevin.spring_library_api.exception.IsbnDuplicateException;
import com.kevin.spring_library_api.model.Book;
import com.kevin.spring_library_api.model.Genre;
import com.kevin.spring_library_api.model.Language;
import com.kevin.spring_library_api.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private static final Long BOOK_ID = 1L;
    private static final String ISBN = "9780060652937";

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Should create a book when isbn is available")
    void shouldCreateBookWhenIsbnIsAvailable() {
        BookRequestDTO request = validBookRequest();
        Book savedBook = book(BOOK_ID, ISBN);

        when(bookRepository.findByIsbn(ISBN)).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookResponseDTO response = bookService.create(request);

        assertEquals(BOOK_ID, response.id());
        assertEquals(request.title(), response.title());
        assertEquals(request.isbn(), response.isbn());
        assertEquals(request.genres(), response.genres());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw exception when isbn already exists")
    void shouldThrowExceptionWhenIsbnAlreadyExists() {
        BookRequestDTO request = validBookRequest();

        when(bookRepository.findByIsbn(ISBN)).thenReturn(Optional.of(book(BOOK_ID, ISBN)));

        assertThrows(IsbnDuplicateException.class, () -> bookService.create(request));

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw exception when book id does not exist")
    void shouldThrowExceptionWhenBookIdDoesNotExist() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.update(BOOK_ID, validBookRequest()));
    }

    @Test
    @DisplayName("Should update book when isbn belongs to the same book")
    void shouldUpdateBookWhenIsbnBelongsToTheSameBook() {
        BookRequestDTO request = validBookRequest();
        Book existingBook = book(BOOK_ID, "9780000000002");
        Book sameBookWithNewIsbn = book(BOOK_ID, ISBN);

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(existingBook));
        when(bookRepository.findByIsbn(ISBN)).thenReturn(Optional.of(sameBookWithNewIsbn));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookResponseDTO response = bookService.update(BOOK_ID, request);

        assertEquals(BOOK_ID, response.id());
        assertEquals(ISBN, response.isbn());
        verify(bookRepository).save(existingBook);
    }

    @Test
    @DisplayName("Should throw exception when updating to an isbn owned by another book")
    void shouldThrowExceptionWhenUpdatingToAnIsbnOwnedByAnotherBook() {
        BookRequestDTO request = validBookRequest();

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book(BOOK_ID, "9780000000002")));
        when(bookRepository.findByIsbn(ISBN)).thenReturn(Optional.of(book(2L, ISBN)));

        assertThrows(IsbnDuplicateException.class, () -> bookService.update(BOOK_ID, request));

        verify(bookRepository, never()).save(any(Book.class));
    }

    private BookRequestDTO validBookRequest() {
        return new BookRequestDTO(
                "The Screwtape Letters",
                "C.S. Lewis",
                "HarperOne",
                new BigDecimal("25.50"),
                2001,
                224,
                ISBN,
                Language.ENGLISH,
                Set.of(Genre.FICTION, Genre.CHRISTIAN_LITERATURE)
        );
    }

    private Book book(Long id, String isbn) {
        Book book = new Book(
                "The Screwtape Letters",
                "C.S. Lewis",
                "HarperOne",
                new BigDecimal("25.50"),
                2001,
                Set.of(Genre.FICTION, Genre.CHRISTIAN_LITERATURE),
                Language.ENGLISH,
                224,
                isbn
        );

        try {
            var idField = Book.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(book, id);
            return book;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Could not set the book id for the test", exception);
        }
    }
}
