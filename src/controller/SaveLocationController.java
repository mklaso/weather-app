package controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.StyleSetter;
import view.WeatherSystemView;

public class SaveLocationController implements EventHandler<MouseEvent>{

	private VBox savedLocations;
	private TextField searchedLocation;
	private Button searchButton;
	private WeatherSystemView view;
	
	public SaveLocationController(WeatherSystemView view, TextField searchedLocation, 
			Button searchButton) {
		this.view = view;
		this.savedLocations = view.savedLocations;
		this.searchedLocation = searchedLocation;
		this.searchButton = searchButton;
	}
	
	public boolean isLocationAlreadySaved(Label locationToAdd) {
		for (Node location: this.savedLocations.getChildren()) {
			HBox holder = (HBox) location;
			HBox locationBox = (HBox) holder.getChildren().get(0);
   	 		Label savedLocation = (Label) locationBox.getChildren().get(1);
   	 		if (savedLocation.getText().toLowerCase().equals(locationToAdd.getText().toLowerCase())) {
   	 			return true;
   	 		}
   	 	}
		return false;
	}
	
	@Override
	public void handle(MouseEvent arg0) {
		boolean valid = this.view.dfs.isValidSearch;
		boolean searchOccurred = this.view.dfs.hasSearchOccurred;
		Label locationToSave = new Label();
		HBox saved = new HBox(3);
		HBox holder = new HBox(10);
		HBox deleteBox = new HBox();
		deleteBox.setAlignment(Pos.CENTER_RIGHT);

		saved.setAlignment(Pos.CENTER_LEFT);
		holder.setStyle("-fx-background-color:rgba(211,211,211, 0.5); -fx-border-color: black;");

		
		ImageView pinpointImage = new ImageView();
		view.setImage(pinpointImage, "marker.png", 20, 20);
		
   	 	locationToSave.setText(searchedLocation.getText());
   	 	
   	 	Button deleteLocation = new Button();
   	 	this.view.setExitButton(deleteLocation, 17.5, 17.5, 25, 25);
   	 	
   	 	
   	 	deleteLocation.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
   	 		@Override
		     public void handle(MouseEvent event) {
		    	 if (savedLocations.getChildren().contains(holder)) {
		    		 savedLocations.getChildren().remove(holder);
		    	 }
   	 		}
	   	 });
   	 	
   	 	//add location only if it doesnt already exist and the list isn't already full
   	 	if (!isLocationAlreadySaved(locationToSave) && savedLocations.getChildren().size() <= 10 
   	 			&& valid && searchOccurred && !locationToSave.getText().equals("")) { 
	   	 	locationToSave.setStyle(StyleSetter.averageFontSize + "-fx-font-weight: 500;");
	   	 	locationToSave.setCursor(Cursor.HAND);
	   	 	locationToSave.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			     @Override
			     public void handle(MouseEvent event) {
			    	 searchedLocation.setText((locationToSave.getText()));
			    	 searchButton.fire();
			    	 searchedLocation.clear();
			    	 searchedLocation.setText((locationToSave.getText()));
			     }
			});
	   	 	deleteBox.getChildren().add(deleteLocation);
	   	 	saved.getChildren().addAll(pinpointImage, locationToSave);
	   	 	holder.getChildren().addAll(saved, deleteBox);
	   	 	this.savedLocations.getChildren().add(holder);
   	 	} else {
   	 		System.out.println("Location is either already saved or invalid.");
   	 	}
	}
}
