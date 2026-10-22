package dev.ikkair.library_app.controller;

import java.sql.SQLException;
import java.util.List;

import dev.ikkair.library_app.model.Book;
import dev.ikkair.library_app.repository.BookRepository;
import dev.ikkair.library_app.repository.BorrowRepository;
import dev.ikkair.library_app.view.BooksPageView;

public class BooksController {
	private final BooksPageView booksPageView;
	private final BookRepository bookRepository;
	private String activeSearchQuery = "";
	private String activeSearchColumn = "Judul";
	private static final int PAGE_SIZE = 25;
	private int currentPage = 0;
	private int totalPages = 1;
	private final BorrowRepository borrowRepository;

	public BooksController(BooksPageView booksPageView) {
		this.booksPageView = booksPageView;
		this.bookRepository = new BookRepository();
		this.borrowRepository = new BorrowRepository();
		setupPagination();
		setupSearch();
		loadPage(0);
	}

	private void setupPagination() {
		booksPageView.getPreviousButton().setOnAction(event -> {
			if (currentPage > 0) {
				loadPage(currentPage - 1);
			}
		});
		booksPageView.getNextButton().setOnAction(event -> {
			if (currentPage + 1 < totalPages) {
				loadPage(currentPage + 1);
			}
		});
	}

	private void loadPage(int page) {
		try {
			int totalBooks;
			List<Book> books;
			if (activeSearchQuery.isBlank()) {
				totalBooks = bookRepository.countBooks();
			} else {
				totalBooks = bookRepository.countSearchResults(
						activeSearchColumn,
						activeSearchQuery);
			}
			totalPages = Math.max(
					1,
					(int) (((long) totalBooks + PAGE_SIZE - 1)
							/ PAGE_SIZE));
			currentPage = Math.max(
					0,
					Math.min(page, totalPages - 1));
			if (activeSearchQuery.isBlank()) {
				books = bookRepository.findPage(
						currentPage,
						PAGE_SIZE);
			} else {
				books = bookRepository.searchPage(
						activeSearchColumn,
						activeSearchQuery,
						currentPage,
						PAGE_SIZE);
			}
			booksPageView.setBooks(books);
			booksPageView.setBorrowedIsbns(
					borrowRepository.findActiveBorrowedIsbns());
			booksPageView.resetTableScroll();
			booksPageView.setPaginationState(
					currentPage,
					totalPages,
					currentPage > 0,
					currentPage < totalPages - 1);
			booksPageView.getSearchStatus().setText(
					totalBooks + " buku ditemukan");
		} catch (SQLException e) {
			booksPageView.getSearchStatus().setText(
					"Failed to load books: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void setupSearch() {
		booksPageView.getSearchButton().setOnAction(event -> {
			activeSearchQuery = booksPageView.getSearchField().getText().trim();
			activeSearchColumn = booksPageView.getSearchColumn().getValue();
			loadPage(0);
		});
		booksPageView.getSearchField().setOnAction(event -> booksPageView.getSearchButton().fire());
	}

	public void setOnBorrowRequested(
			java.util.function.Consumer<Book> callback) {
		booksPageView.setOnBorrowRequested(callback);
	}

	public void refreshBorrowedBooks() {
		try {
			booksPageView.setBorrowedIsbns(
					borrowRepository.findActiveBorrowedIsbns());
		} catch (SQLException e) {
			booksPageView.getSearchStatus().setText(
					"Failed to refresh borrowed books: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void refreshBooks() {
		loadPage(0);
	}
}
