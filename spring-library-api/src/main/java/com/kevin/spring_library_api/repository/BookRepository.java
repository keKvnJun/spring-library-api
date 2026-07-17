package com.kevin.spring_library_api.repository;
import com.kevin.spring_library_api.model.Book;
import com.kevin.spring_library_api.model.Genre;
import com.kevin.spring_library_api.model.Language;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findAll();

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByPublisher(String publisher);

    @Query("""
        SELECT b
        FROM Book b
        JOIN b.genres g
        WHERE g IN :genres
        GROUP BY b
        HAVING COUNT(DISTINCT g) = :genreCount
        """)
    List<Book> findByAllGenres(
            @Param("genres") Set<Genre> genres,
            @Param("genreCount") long genreCount
    );

    List<Book> findByLanguage(Language language);

    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.price < :price")
    List<Book> findByPriceLessThan(BigDecimal price);

    List<Book> findByPublicationYear(int publicationYear);

}
