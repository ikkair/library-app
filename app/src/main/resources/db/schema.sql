CREATE TABLE IF NOT EXISTS books (
    isbn TEXT PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    author TEXT,
    publication_year INTEGER,
    publisher TEXT,
    cover TEXT
);
