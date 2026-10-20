package dev.ikkair.library_app.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import dev.ikkair.library_app.model.Book;
import dev.ikkair.library_app.model.Member;
import dev.ikkair.library_app.repository.BorrowRepository;
import dev.ikkair.library_app.repository.MemberRepository;
import dev.ikkair.library_app.view.MemberSelectionPageView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MemberSelectionController {

	private final MemberSelectionPageView view;
	private final Book selectedBook;
	private final Runnable onCancel;
	private final Runnable onComplete;

	private final MemberRepository memberRepository;
	private final BorrowRepository borrowRepository;

	public MemberSelectionController(
			MemberSelectionPageView view,
			Book selectedBook,
			Runnable onCancel,
			Runnable onComplete) {
		this.view = view;
		this.selectedBook = selectedBook;
		this.onCancel = onCancel;
		this.onComplete = onComplete;
		this.memberRepository = new MemberRepository();
		this.borrowRepository = new BorrowRepository();
		view.setBook(selectedBook);
		setupActions();
		loadMembers();
	}

	private void setupActions() {
		view.getConfirmButton().setOnAction(event -> confirmBorrow());
		view.getCancelButton().setOnAction(event -> onCancel.run());
	}

	private void loadMembers() {
		try {
			view.setMembers(memberRepository.findAll());
		} catch (SQLException e) {
			view.getStatusLabel().setText(
					"Failed to load members: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void confirmBorrow() {
		Member selectedMember = view.getSelectedMember();
		if (selectedMember == null) {
			view.getStatusLabel().setText(
					"Please select a member first.");
			return;
		}
		Alert confirmation = new Alert(
				Alert.AlertType.CONFIRMATION,
				"Borrow \"" + selectedBook.getTitle()
						+ "\" for " + selectedMember.getName() + "?",
				ButtonType.CANCEL,
				ButtonType.OK);
		confirmation.setTitle("Confirm Borrow");
		confirmation.setHeaderText("Confirm borrowing transaction");
		confirmation.showAndWait().ifPresent(response -> {
			if (response != ButtonType.OK) {
				return;
			}
			try {
				LocalDate borrowDate = LocalDate.now();
				LocalDate dueDate = borrowDate.plusDays(14);
				borrowRepository.createBorrow(
						selectedMember.getId(),
						selectedBook.getIsbn(),
						borrowDate,
						dueDate);
				Alert success = new Alert(
						Alert.AlertType.INFORMATION,
						"Book borrowed successfully.\n"
								+ "Due date: " + dueDate);
				success.setHeaderText("Borrow successful");
				success.showAndWait();
				onComplete.run();
			} catch (SQLException | IllegalArgumentException e) {
				view.getStatusLabel().setText(
						"Failed to borrow book: " + e.getMessage());
				e.printStackTrace();
			}
		});
	}
}
