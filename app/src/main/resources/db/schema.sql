CREATE TABLE IF NOT EXISTS books (
    isbn TEXT PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    author TEXT,
    publication_year INTEGER,
    publisher TEXT
);

CREATE TABLE IF NOT EXISTS members (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE,
    registration_date TEXT NOT NULL
);
