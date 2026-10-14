package dev.ikkair.library_app.model;

import java.time.Year;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private Year publicationYear;
    private String publisher;

    public Book(String isbn, String title, String author, Year publicationYear, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Year getPublicationYear() {
        return publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }
}
