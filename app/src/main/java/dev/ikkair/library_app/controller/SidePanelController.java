package dev.ikkair.library_app.controller;

import dev.ikkair.library_app.view.BooksPageView;
import dev.ikkair.library_app.view.BorrowPageView;
import dev.ikkair.library_app.view.MembersPageView;
import dev.ikkair.library_app.view.SidePanel;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class SidePanelController {
	private final SidePanel sidePanelView;
	private final BorderPane root;
	private final BooksPageView booksPageView;
	private final MembersPageView membersPageView;
	private final BorrowPageView borrowingPageView;
	private final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active");

	public SidePanelController(
		SidePanel view,
		BorderPane root,
		BooksPageView booksPageView,
		MembersPageView membersPageView,
		BorrowPageView borrowingPageView
	){
		this.sidePanelView = view;
		this.root = root;
		this.booksPageView = booksPageView;
		this.membersPageView = membersPageView;
		this.borrowingPageView = borrowingPageView;
		root.setCenter(this.booksPageView);
		setupNavigation();
		setActiveButton(sidePanelView.getBooksButton());
	}

	private void setActiveButton(Button button) {
	    sidePanelView.getBooksButton().pseudoClassStateChanged(ACTIVE, false);
	    sidePanelView.getMembersButton().pseudoClassStateChanged(ACTIVE, false);
	    sidePanelView.getBorrowingButton().pseudoClassStateChanged(ACTIVE, false);
	    button.pseudoClassStateChanged(ACTIVE, true);
	}

	private void setupNavigation(){
		sidePanelView.getBooksButton().setOnAction(event -> {
			root.setCenter(booksPageView);
			setActiveButton(sidePanelView.getBooksButton());
		});
		sidePanelView.getMembersButton().setOnAction(event -> {
			root.setCenter(membersPageView);
			setActiveButton(sidePanelView.getMembersButton());
		});
		sidePanelView.getBorrowingButton().setOnAction(event -> {
			root.setCenter(borrowingPageView);
			setActiveButton(sidePanelView.getBorrowingButton());
		});
	}
}
