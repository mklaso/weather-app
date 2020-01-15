package controller;

import javafx.event.EventHandler;
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
	
	@Override
	public void handle(MouseEvent arg0) {
		Label newSavedLocation = new Label();
   	 	newSavedLocation.setText(searchedLocation.getText());
   	 	newSavedLocation.setStyle("-fx-font-size: 18; -fx-font-weight: 500;");
   	 	newSavedLocation.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
   	 		
		     @Override
		     public void handle(MouseEvent event) {
		    	 searchButton.fire();
		     }
		});
   	 	this.savedLocations.getChildren().add(newSavedLocation);
	}
}
