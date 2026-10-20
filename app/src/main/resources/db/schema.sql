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

CREATE TABLE IF NOT EXISTS borrows (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    member_id INTEGER NOT NULL,
    isbn TEXT NOT NULL,
    borrow_date TEXT NOT NULL,
    due_date TEXT NOT NULL,
    return_date TEXT,

    FOREIGN KEY (member_id)
        REFERENCES members(id)
        ON DELETE RESTRICT,

    FOREIGN KEY (isbn)
        REFERENCES books(isbn)
        ON DELETE RESTRICT,

    CHECK (due_date >= borrow_date),
    CHECK (
        return_date IS NULL
        OR return_date >= borrow_date
    )
);
