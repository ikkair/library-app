package dev.ikkair.library_app.controller;

import dev.ikkair.library_app.view.BooksPageView;

public class BooksController {
	private final BooksPageView booksPageView;

	public BooksController(BooksPageView booksPageView){
		this.booksPageView = booksPageView;
		setupSearch();
	}

	private void setupSearch(){
		booksPageView.getSearchField().textProperty().addListener(
			(observable, oldValue, newValue) -> {
				if (newValue.isBlank()){
					booksPageView.getSearchStatus().setText("");
				} else {
					booksPageView.getSearchStatus().setText(newValue);
				}
			}
		);
	}
}
