package com.kevin.spring_library_api.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 100)
    private String publisher;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int publicationYear;

    @Column(nullable = false)
    private int numberOfPages;

    @Column(nullable = false, unique = true, length = 17)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @ElementCollection
    @CollectionTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Set<Genre> genres = new HashSet<>();

    public Book(String title, String author, String publisher, BigDecimal price, int publicationYear, Set<Genre> genres, Language language, int numberOfPages, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.publicationYear = publicationYear;
        this.genres = genres;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres == null ? new HashSet<>() : new HashSet<>(genres);
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
