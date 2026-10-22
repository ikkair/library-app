package dev.ikkair.library_app.controller;

import java.sql.SQLException;
import java.time.LocalDate;

import dev.ikkair.library_app.model.Member;
import dev.ikkair.library_app.repository.MemberRepository;
import dev.ikkair.library_app.view.AddMemberPageView;

public class AddMemberController {

	private final AddMemberPageView view;
	private final MemberRepository memberRepository;
	private final Runnable onSaved;
	private final Runnable onCancel;

	public AddMemberController(
			AddMemberPageView view,
			MemberRepository memberRepository,
			Runnable onSaved,
			Runnable onCancel) {

		this.view = view;
		this.memberRepository = memberRepository;
		this.onSaved = onSaved;
		this.onCancel = onCancel;

		view.getSaveButton().setOnAction(event -> saveMember());
		view.getCancelButton().setOnAction(event -> onCancel.run());
	}

	public void showForm() {
		view.clearForm();
	}

	private void saveMember() {
		String name = view.getNameField().getText().trim();
		String email = view.getEmailField().getText().trim();
		LocalDate registrationDate = view.getRegistrationDatePicker().getValue();

		if (name.isEmpty()) {
			showError("Nama anggota wajib diisi.");
			return;
		}

		if (registrationDate == null) {
			showError("Tanggal registrasi wajib diisi.");
			return;
		}

		Member member = new Member(
				0,
				name,
				email.isEmpty() ? null : email,
				registrationDate);

		try {
			memberRepository.createMember(member);
			onSaved.run();
		} catch (SQLException e) {
			showError("Gagal menyimpan member: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void showError(String message) {
		view.getStatusLabel().setText(message);
	}
}
