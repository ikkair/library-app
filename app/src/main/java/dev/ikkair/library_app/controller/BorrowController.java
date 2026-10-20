package dev.ikkair.library_app.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import dev.ikkair.library_app.model.Borrow;
import dev.ikkair.library_app.repository.BorrowRepository;
import dev.ikkair.library_app.view.BorrowPageView;

public class BorrowController {

	private final BorrowPageView view;
	private final BorrowRepository borrowRepository;
	private String activeSearchQuery = "";
	private String activeSearchColumn = "Anggota";
	private Runnable onBookReturned = () -> {
	};

	public BorrowController(BorrowPageView view) {
		this.view = view;
		this.borrowRepository = new BorrowRepository();
		setupActions();
		setupSearch();
		loadData();
	}

	private void setupActions() {
		view.getReturnButton().setOnAction(event -> returnBook());
	}

	private void loadData() {
		refreshBorrow();
	}

	private void returnBook() {
		Borrow borrow = view.getSelectedBorrow();
		if (borrow == null) {
			showError("Please select a borrow record.");
			return;
		}
		if (borrow.getReturnDate() != null) {
			showError("This book has already been returned.");
			return;
		}
		try {
			borrowRepository.markReturned(
					borrow.getId(),
					LocalDate.now());
			view.getStatusLabel().setText(
					"Book returned successfully.");
			refreshBorrow();
			onBookReturned.run();
		} catch (SQLException e) {
			showError("Failed to return book: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void refreshBorrow() {
		try {
			if (activeSearchQuery.isBlank()) {
				view.setBorrows(borrowRepository.findAll());
			} else {
				view.setBorrows(
						borrowRepository.search(
								activeSearchColumn,
								activeSearchQuery));
			}
		} catch (SQLException e) {
			showError(
					"Failed to refresh borrowing records: "
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	private void showError(String message) {
		view.getStatusLabel().setText(message);
	}

	public void setOnBookReturned(Runnable callback) {
		this.onBookReturned = callback;
	}

	private void setupSearch() {
		view.getSearchButton().setOnAction(event -> {
			activeSearchQuery = view.getSearchField().getText().trim();
			activeSearchColumn = view.getSearchColumn().getValue();
			refreshBorrow();
		});
		view.getSearchField().setOnAction(
				event -> view.getSearchButton().fire());
	}
}
