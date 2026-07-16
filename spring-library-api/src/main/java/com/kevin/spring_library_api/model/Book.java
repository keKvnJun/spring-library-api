package com.kevin.spring_library_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 100)
    private String publisher;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int publicationYear;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String idiom;

    @Column(nullable = false)
    private int numberOfPages;

    @Column(unique = true, length = 13)
    private long isbn;

    public Book() {}

    public Book(long id, String title, String author, String publisher, double price, int publicationYear, String genre, String idiom, int numberOfPages, long isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.idiom = idiom;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
    }


}
