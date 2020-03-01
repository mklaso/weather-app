package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import view.WeatherSystemView;

public class SetFavouriteController implements EventHandler<ActionEvent> {

	private TextField searchResult;
	private WeatherSystemView view;
	
	public SetFavouriteController(WeatherSystemView view, TextField searchResult) {
		this.searchResult = searchResult;
		this.view = view;
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		// sets favourite location in application and database
		String location = searchResult.getText();
		this.view.database.setFavouriteLocation(location);
		System.out.println("Favourite location updated!");
		
	}
}
