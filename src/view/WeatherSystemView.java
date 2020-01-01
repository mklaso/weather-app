package view;

import javafx.stage.Stage;
import controller.EnterKeypressHandler;
import controller.LoadWeatherController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import model.Observable;
import model.Observer;

public class WeatherSystemView extends FlowPane implements Observer {
	
	private Stage stage;
	public Button goBackButton = new Button("Go Back");
	public TextField locationField = new TextField();
	
	public WeatherSystemView(Stage stage) {
		this.stage = stage;
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(40, 10, 40, 10));
		this.setStyle("-fx-background-color:"
				+ "linear-gradient(from 25% 25% to 100% 100%, rgb(177, 249, 254), rgb(226, 166, 255));"
				+ " -fx-border-color:black;");
		stage.setTitle("Cloudy Skies");
		
		HBox searchBox = new HBox(5);
		
		locationField.setPrefColumnCount(17);
		locationField.setMinSize(30,  39);
		locationField.setPromptText("enter a location (e.g: Oakville, CA)");
		locationField.setStyle(ButtonHighlighter.TEXT);
		
		Button searchButton = new Button("Get Weather");
		searchButton.setMinSize(30, 30);
		searchButton.setStyle(ButtonHighlighter.REGULAR);
		goBackButton.setMinSize(30, 30);
		goBackButton.setStyle(ButtonHighlighter.REGULAR);
		
		//sets up event handler for loading weather on search
		searchButton.setOnAction(new LoadWeatherController(locationField));
		
		//allows enter button to be used for making a search
		locationField.setOnKeyPressed(new EnterKeypressHandler(searchButton));
		
		searchBox.getChildren().addAll(locationField, searchButton, goBackButton);
		
		this.getChildren().addAll(searchBox);
		
		ButtonHighlighter.modifyColour(searchButton, ButtonHighlighter.REGULAR, ButtonHighlighter.HIGHLIGHT);
		ButtonHighlighter.modifyColour(goBackButton, ButtonHighlighter.REGULAR, ButtonHighlighter.HIGHLIGHT);
	}
	
	/**
	 * Sets the preferred height and width of a region.
	 * Also sets the style of the region.
	 */
	public static void setAttributes(Region region, double height, double width, String style) {
		if (style != null) {
			region.setPrefSize(width, height);
			region.setStyle(style);
		} else {
			region.setPrefSize(width, height);
		}
	}
	
	/**
	 * Sets the position of the node on the window.
	 */
	public static void setNodePositionAttributes(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}

	@Override
	public void update(Observable o) {
		// TODO Auto-generated method stub
		
	}
}
