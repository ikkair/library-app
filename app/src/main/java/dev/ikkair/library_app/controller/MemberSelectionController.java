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

    private static final int PAGE_SIZE = 25;
    private final MemberSelectionPageView view;
    private final Book selectedBook;
    private final Runnable onCancel;
    private final Runnable onComplete;
    private final MemberRepository memberRepository;
    private final BorrowRepository borrowRepository;
    private int currentPage = 0;
    private int totalPages = 1;
    private String searchQuery = "";
    private String searchColumn = "Nama";
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
        view.getConfirmButton().setOnAction(
                event -> confirmBorrow());
        view.getCancelButton().setOnAction(
                event -> onCancel.run());
        view.getSearchButton().setOnAction(
                event -> searchMembers());
        view.getSearchField().setOnAction(
                event -> searchMembers());
        view.getSearchColumn().setOnAction(
                event -> searchMembers());
        view.getPreviousButton().setOnAction(event -> {
            if (currentPage > 0) {
                currentPage--;
                loadMembers();
            }
        });
        view.getNextButton().setOnAction(event -> {
            if (currentPage + 1 < totalPages) {
                currentPage++;
                loadMembers();
            }
        });
    }

    private void searchMembers() {
        searchQuery = view.getSearchField().getText().trim();

        String selectedColumn = view.getSearchColumn().getValue();
        if (selectedColumn != null) {
            searchColumn = selectedColumn;
        }

        currentPage = 0;
        loadMembers();
    }

    private void loadMembers() {
        try {
            int totalMembers;
            if (searchQuery.isBlank()) {
                totalMembers = memberRepository.countMembers();
            } else {
                totalMembers = memberRepository.countSearchResults(
                        searchColumn,
                        searchQuery);
            }

            totalPages = Math.max(
                    1,
                    (int) Math.ceil((double) totalMembers / PAGE_SIZE));

            // Keep current page valid if the result count changes.
            if (currentPage >= totalPages) {
                currentPage = totalPages - 1;
            }

            if (searchQuery.isBlank()) {
                view.setMembers(
                        memberRepository.findPage(
                                currentPage,
                                PAGE_SIZE));
            } else {
                view.setMembers(
                        memberRepository.searchPage(
                                searchColumn,
                                searchQuery,
                                currentPage,
                                PAGE_SIZE));
            }

            view.setPaginationState(
                    currentPage + 1,
                    totalPages,
                    currentPage > 0,
                    currentPage + 1 < totalPages);

            view.resetTableScroll();
            view.getStatusLabel().setText(
                    totalMembers + " anggota ditemukan.");

        } catch (SQLException | IllegalArgumentException e) {
            view.getStatusLabel().setText(
                    "Gagal memuat anggota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void confirmBorrow() {
        Member selectedMember = view.getSelectedMember();

        if (selectedMember == null) {
            view.getStatusLabel().setText(
                    "Silakan pilih anggota terlebih dahulu.");
            return;
        }

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Pinjam buku \"" + selectedBook.getTitle()
                        + "\" untuk " + selectedMember.getName() + "?",
                ButtonType.CANCEL,
                ButtonType.OK);

        confirmation.setTitle("Konfirmasi Peminjaman");
        confirmation.setHeaderText(
                "Konfirmasi transaksi peminjaman");

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
                        "Buku berhasil dipinjam.\n"
                                + "Jatuh tempo: " + dueDate);

                success.setTitle("Peminjaman Berhasil");
                success.setHeaderText(
                        "Transaksi peminjaman berhasil");

                success.showAndWait();
                onComplete.run();

            } catch (SQLException | IllegalArgumentException e) {
                view.getStatusLabel().setText(
                        "Gagal meminjam buku: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
