package dev.ikkair.library_app.model;

import java.net.URI;
import java.time.Year;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private Year publicationYear;
    private String publisher;
    private URI cover;

    public Book(String isbn, String title, String author, Year publicationYear, String publisher, URI cover) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.cover = cover;
    }

    public URI getCover() {
        return cover;
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
