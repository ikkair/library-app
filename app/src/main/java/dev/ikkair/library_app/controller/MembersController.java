package dev.ikkair.library_app.controller;

import java.sql.SQLException;
import java.util.List;

import dev.ikkair.library_app.model.Member;
import dev.ikkair.library_app.repository.MemberRepository;
import dev.ikkair.library_app.view.MembersPageView;

public class MembersController {

	private final MembersPageView membersPageView;
	private final MemberRepository memberRepository;
	private String activeSearchQuery = "";
	private String activeSearchColumn = "Nama";
	private static final int PAGE_SIZE = 25;
	private int currentPage = 0;
	private int totalPages = 1;

	public MembersController(MembersPageView membersPageView) {
		this.membersPageView = membersPageView;
		this.memberRepository = new MemberRepository();
		setupPagination();
		setupSearch();
		loadPage(0);
	}

	private void setupPagination() {
		membersPageView.getPreviousButton().setOnAction(event -> {
			if (currentPage > 0) {
				loadPage(currentPage - 1);
			}
		});
		membersPageView.getNextButton().setOnAction(event -> {
			if (currentPage + 1 < totalPages) {
				loadPage(currentPage + 1);
			}
		});
	}

	private void loadPage(int page) {
		try {
			int totalMembers;
			List<Member> members;
			if (activeSearchQuery.isBlank()) {
				totalMembers = memberRepository.countMembers();
			} else {
				totalMembers = memberRepository.countSearchResults(
						activeSearchColumn,
						activeSearchQuery);
			}
			totalPages = Math.max(
					1,
					(int) (((long) totalMembers + PAGE_SIZE - 1)
							/ PAGE_SIZE));
			currentPage = Math.max(
					0,
					Math.min(page, totalPages - 1));
			if (activeSearchQuery.isBlank()) {
				members = memberRepository.findPage(
						currentPage,
						PAGE_SIZE);
			} else {
				members = memberRepository.searchPage(
						activeSearchColumn,
						activeSearchQuery,
						currentPage,
						PAGE_SIZE);
			}
			membersPageView.setMembers(members);
			membersPageView.resetTableScroll();
			membersPageView.setPaginationState(
					currentPage,
					totalPages,
					currentPage > 0,
					currentPage < totalPages - 1);
			membersPageView.getSearchStatus().setText(
					totalMembers + " anggota ditemukan");
		} catch (SQLException e) {
			membersPageView.getSearchStatus().setText(
					"Failed to load members: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void setupSearch() {
		membersPageView.getSearchButton().setOnAction(event -> {
			activeSearchQuery = membersPageView.getSearchField()
					.getText().trim();
			activeSearchColumn = membersPageView.getSearchColumn()
					.getValue();
			loadPage(0);
		});
		membersPageView.getSearchField().setOnAction(event -> membersPageView.getSearchButton().fire());
	}

	public void refreshMembers() {
		activeSearchQuery = "";
		membersPageView.getSearchField().clear();
		membersPageView.getSearchColumn().setValue("Nama");
		loadPage(0);
	}
}
