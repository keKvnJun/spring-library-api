package com.kevin.spring_library_api.repository;
import com.kevin.spring_library_api.model.Book;
import com.kevin.spring_library_api.model.Genre;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findAll();

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);

    Optional<Book> findByAuthor(String author);

    List<Book> findByPublisher(String publisher);

    List<Book> findByGenres(Set<Genre> genres);

    List<Book> findByLanguage(String language);

    List<Book> findByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.price < :price")
    List<Book> findByPriceLessThan(BigDecimal price);

    List<Book> findByPublicationYear(int publicationYear);

}
