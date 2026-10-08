package dev.ikkair.library_app.controller;

import dev.ikkair.library_app.view.BooksPageView;
import dev.ikkair.library_app.view.BorrowingPageView;
import dev.ikkair.library_app.view.DashboardPageView;
import dev.ikkair.library_app.view.MembersPageView;
import dev.ikkair.library_app.view.SidePanel;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class SidePanelController {
	private final SidePanel sidePanelView;
	private final BorderPane root;
	private final DashboardPageView dashboardPageView;
	private final BooksPageView booksPageView;
	private final MembersPageView membersPageView;
	private final BorrowingPageView borrowingPageView;
	private final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active");

	public SidePanelController(SidePanel view,
		BorderPane root,
		DashboardPageView dashboardPageView,
		BooksPageView booksPageView,
		MembersPageView membersPageView,
		BorrowingPageView borrowingPageView
	){
		this.sidePanelView = view;
		this.root = root;
		this.dashboardPageView = dashboardPageView;
		this.booksPageView = booksPageView;
		this.membersPageView = membersPageView;
		this.borrowingPageView = borrowingPageView;
		root.setCenter(this.dashboardPageView);
		setupNavigation();
		setActiveButton(sidePanelView.getDashboardButton());
	}

	private void setActiveButton(Button button) {
	    sidePanelView.getDashboardButton().pseudoClassStateChanged(ACTIVE, false);
	    sidePanelView.getBooksButton().pseudoClassStateChanged(ACTIVE, false);
	    sidePanelView.getMembersButton().pseudoClassStateChanged(ACTIVE, false);
	    sidePanelView.getBorrowingButton().pseudoClassStateChanged(ACTIVE, false);
	    button.pseudoClassStateChanged(ACTIVE, true);
	}

	private void setupNavigation(){
		sidePanelView.getDashboardButton().setOnAction(event -> {
			root.setCenter(dashboardPageView);
			setActiveButton(sidePanelView.getDashboardButton());
		});
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
