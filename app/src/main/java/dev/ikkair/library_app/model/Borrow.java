package dev.ikkair.library_app.model;

import java.time.LocalDate;

public class Borrow {

	private final int id;
	private final int memberId;
	private final String memberName;
	private final String isbn;
	private final String bookTitle;
	private final LocalDate borrowDate;
	private final LocalDate dueDate;
	private final LocalDate returnDate;

	public Borrow(
			int id,
			int memberId,
			String memberName,
			String isbn,
			String bookTitle,
			LocalDate borrowDate,
			LocalDate dueDate,
			LocalDate returnDate) {
		this.id = id;
		this.memberId = memberId;
		this.memberName = memberName;
		this.isbn = isbn;
		this.bookTitle = bookTitle;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
		this.returnDate = returnDate;
	}

	public int getId() {
		return id;
	}

	public int getMemberId() {
		return memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public String getStatus() {
		if (returnDate != null) {
			return "Returned";
		}

		if (dueDate.isBefore(LocalDate.now())) {
			return "Overdue";
		}

		return "Borrowed";
	}
}
