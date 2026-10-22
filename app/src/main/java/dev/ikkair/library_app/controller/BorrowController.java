package dev.ikkair.library_app.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import dev.ikkair.library_app.model.Borrow;
import dev.ikkair.library_app.repository.BorrowRepository;
import dev.ikkair.library_app.view.BorrowPageView;

public class BorrowController {
	private static final int PAGE_SIZE = 25;
	private final BorrowPageView view;
	private final BorrowRepository borrowRepository;
	private int currentPage = 0;
	private int totalPages = 1;
	private String activeSearchQuery = "";
	private String activeSearchColumn = "Anggota";
	private Runnable onBookReturned = () -> {
	};

	public BorrowController(BorrowPageView view) {
		this.view = view;
		this.borrowRepository = new BorrowRepository();
		setupActions();
		setupSearch();
		refreshBorrow();
	}

	private void setupActions() {
		view.getReturnButton().setOnAction(event -> returnBook());
		view.getPreviousButton().setOnAction(event -> {
			if (currentPage > 0) {
				currentPage--;
				refreshBorrow();
			}
		});
		view.getNextButton().setOnAction(event -> {
			if (currentPage + 1 < totalPages) {
				currentPage++;
				refreshBorrow();
			}
		});
	}

	private void setupSearch() {
		view.getSearchButton().setOnAction(event -> {
			activeSearchQuery = view.getSearchField().getText().trim();
			String column = view.getSearchColumn().getValue();
			if (column != null) {
				activeSearchColumn = column;
			}
			currentPage = 0;
			refreshBorrow();
		});
		view.getSearchField().setOnAction(
				event -> view.getSearchButton().fire());
		view.getSearchColumn().setOnAction(
				event -> view.getSearchButton().fire());
	}

	public void refreshBorrow() {
		try {
			int totalRecords;
			if (activeSearchQuery.isBlank()) {
				totalRecords = borrowRepository.countBorrows();
			} else {
				totalRecords = borrowRepository.countSearchResults(
						activeSearchColumn,
						activeSearchQuery);
			}
			totalPages = Math.max(
					1,
					(int) Math.ceil(
							(double) totalRecords / PAGE_SIZE));
			if (currentPage >= totalPages) {
				currentPage = totalPages - 1;
			}
			if (activeSearchQuery.isBlank()) {
				view.setBorrows(
						borrowRepository.findPage(
								currentPage,
								PAGE_SIZE));
			} else {
				view.setBorrows(
						borrowRepository.searchPage(
								activeSearchColumn,
								activeSearchQuery,
								currentPage,
								PAGE_SIZE));
			}
			view.setPaginationState(
					currentPage + 1,
					totalPages,
					currentPage > 0,
					currentPage + 1 < totalPages);
			view.getStatusLabel().setText(
					totalRecords + " transaksi ditemukan.");
		} catch (SQLException | IllegalArgumentException e) {
			view.getStatusLabel().setText(
					"Gagal memuat transaksi: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void returnBook() {
		Borrow borrow = view.getSelectedBorrow();
		if (borrow == null) {
			showError("Silakan pilih transaksi peminjaman.");
			return;
		}
		if (borrow.getReturnDate() != null) {
			showError("Buku ini sudah dikembalikan.");
			return;
		}
		try {
			borrowRepository.markReturned(
					borrow.getId(),
					LocalDate.now());
			view.getStatusLabel().setText(
					"Buku berhasil ditandai sebagai dikembalikan.");
			refreshBorrow();
			onBookReturned.run();
		} catch (SQLException e) {
			showError(
					"Gagal mengembalikan buku: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void showError(String message) {
		view.getStatusLabel().setText(message);
	}

	public void setOnBookReturned(Runnable callback) {
		this.onBookReturned = callback;
	}
}
