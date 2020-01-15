package controller;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SaveLocationController implements EventHandler<MouseEvent>{

	private VBox savedLocations;
	private TextField searchedLocation;
	private Button searchButton;
	
	public SaveLocationController(VBox savedLocations, TextField searchedLocation, 
			Button searchButton) {
		this.savedLocations = savedLocations;
		this.searchedLocation = searchedLocation;
		this.searchButton = searchButton;
	}
	
	public boolean isLocationAlreadySaved(Label locationToAdd) {
		for (Node location: this.savedLocations.getChildren()) {
   	 		Label savedLocation = (Label) location;
   	 		if (savedLocation.getText().equals(locationToAdd.getText())) {
   	 			return true;
   	 		}
   	 	}
		return false;
	}
	
	@Override
	public void handle(MouseEvent arg0) {
		Label locationToSave = new Label();
   	 	locationToSave.setText(searchedLocation.getText());
   	 	
   	 	
   	 	if (!isLocationAlreadySaved(locationToSave)) { 
	   	 	locationToSave.setStyle("-fx-font-size: 18; -fx-font-weight: 500;");
	   	 	locationToSave.setCursor(Cursor.HAND);
	   	 	locationToSave.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			     @Override
			     public void handle(MouseEvent event) {
			    	 searchedLocation.setText((locationToSave.getText()));
			    	 searchButton.fire();
			    	 searchedLocation.clear();
			     }
			});
	   	 	this.savedLocations.getChildren().add(locationToSave);
   	 	} else {
   	 		System.out.println("Location already saved.");
   	 	}
	}
}
