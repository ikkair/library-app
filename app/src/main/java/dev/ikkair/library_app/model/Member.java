package dev.ikkair.library_app.model;

import java.time.LocalDate;

public class Member {
	private int id;
	private String name;
	private String email;
	private LocalDate registrationDate;

	public Member(int id, String name, String email, LocalDate registrationDate) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.registrationDate = registrationDate;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
}
