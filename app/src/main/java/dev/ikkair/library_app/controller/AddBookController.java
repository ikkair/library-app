package dev.ikkair.library_app.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;

import dev.ikkair.library_app.model.Book;
import dev.ikkair.library_app.repository.BookRepository;
import dev.ikkair.library_app.view.AddBookPageView;

public class AddBookController {
	private final AddBookPageView view;
	private final BookRepository bookRepository;
	private final Runnable onSaved;
	private final Runnable onCancel;

	public AddBookController(
			AddBookPageView view,
			Runnable onSaved,
			Runnable onCancel) {
		this.view = view;
		this.bookRepository = new BookRepository();
		this.onSaved = onSaved;
		this.onCancel = onCancel;
		view.getSaveButton().setOnAction(
				event -> saveBook());
		view.getCancelButton().setOnAction(
				event -> onCancel.run());
	}

	private void saveBook() {
		String isbn = view.getIsbnField().getText().trim();
		String title = view.getTitleField().getText().trim();
		String author = view.getAuthorField().getText().trim();
		LocalDate publicationDate = view.getPublicationDateField().getValue();
		String publisher = view.getPublisherField().getText().trim();
		if (isbn.isEmpty() || title.isEmpty()) {
			showError("ISBN dan judul wajib diisi.");
			return;
		}
		Year publicationYear = publicationDate == null
				? null
				: Year.of(publicationDate.getYear());
		Book book = new Book(
				isbn,
				title,
				author.isEmpty() ? null : author,
				publicationYear,
				publisher.isEmpty() ? null : publisher);
		try {
			bookRepository.createBook(book);
			onSaved.run();
		} catch (SQLException e) {
			showError("Gagal menyimpan buku: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void showError(String message) {
		view.getStatusLabel().setText(message);
	}
}
