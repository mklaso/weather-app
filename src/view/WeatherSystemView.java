package view;

import javafx.stage.Stage;
import controller.EnterKeypressHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.*;

public class WeatherSystemView extends FlowPane implements Observer {
	
	private Stage stage;
	public Button goBackButton = new Button("Go Back");
	public TextField locationField = new TextField();
	private DayForecastSystem dfs;
	private VBox wholeBox = new VBox(10);
	public Button searchButton = new Button("Get Weather");
	private HBox forecastBox = new HBox(5);
	private VBox weatherBox = new VBox(10);
	
	public WeatherSystemView(Stage stage, DayForecastSystem dfs) {
		this.stage = stage;
		this.dfs = dfs;
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(40, 10, 40, 10));
		this.setStyle("-fx-background-color:"
				+ "linear-gradient(from 25% 25% to 100% 100%, rgb(177, 249, 254), rgb(226, 166, 255));"
				+ " -fx-border-color:black;");
		
		HBox searchBox = new HBox(5);
		
		locationField.setPrefColumnCount(17);
		locationField.setMinSize(30,  39);
		locationField.setPromptText("enter a location (e.g: Oakville, CA)");
		locationField.setStyle(ButtonHighlighter.TEXT);
		
		searchButton.setMinSize(30, 30);
		searchButton.setStyle(ButtonHighlighter.REGULAR);
		goBackButton.setMinSize(30, 30);
		goBackButton.setStyle(ButtonHighlighter.REGULAR);
		
		//allows enter button to be used for making a search
		locationField.setOnKeyPressed(new EnterKeypressHandler(searchButton));
		
		searchBox.getChildren().addAll(locationField, searchButton, goBackButton);
		wholeBox.getChildren().add(searchBox);
		
		this.getChildren().addAll(wholeBox);
		
		ButtonHighlighter.modifyColour(searchButton, ButtonHighlighter.REGULAR, ButtonHighlighter.HIGHLIGHT);
		ButtonHighlighter.modifyColour(goBackButton, ButtonHighlighter.REGULAR, ButtonHighlighter.HIGHLIGHT);
	}
	
	public VBox getCurrentWeather() {
		WeatherSystem ws = this.dfs.getWeatherSystem();
		VBox currentWeather = new VBox();
		
		//if maps are empty return an empty vbox
		if ((ws.getTempMap() == null) || (ws.getAllDataMap() == null) || (ws.getSysMap() == null)) {
			return currentWeather;
		}
		
		//otherwise return a vbox with all the weather data
		Label city = new Label("Local time in " + ws.getCityName() + " is: " + ws.getLocalTime());
		Label location = new Label("Here are the stats for " + ws.getLocation() + ".\n");
		Label currentTemp = new Label("Current temperature: " + ws.getCurrentTemp(ws.getTempMap()));
		Label feelsTemp = new Label("It feels like: " + ws.getFeelsLikeTemp(ws.getTempMap()));
		Label minTemp = new Label("Min temperature: " + ws.getMinTemp(ws.getTempMap()));
		Label maxTemp = new Label("Max temperature: " + ws.getMaxTemp(ws.getTempMap()) + "\n");
		Label humidity = new Label("Humidity of: " + ws.getHumidity());
		Label wind = new Label("Wind speeds of: " + ws.getWindConditions());
		Label weather = new Label("Current weather conditions: " + ws.getWeatherStatus(ws.getAllDataMap()) + "\n");
		Label sunrise = new Label("Sunrise at: " + ws.getSunriseTime());
		Label sunset = new Label("Sunset at: " + ws.getSunsetTime());
		
		currentWeather.getChildren().addAll(city, location, currentTemp, feelsTemp,
				minTemp, maxTemp, humidity, wind, weather, sunrise, sunset);
		
		return currentWeather;
	}
	
	public VBox getForecastDay(int n) {
		VBox dayForecast = new VBox();
		Label day = new Label(this.dfs.getDay());
		Label temp = new Label(this.dfs.getTemp());
		Label weather = new Label(this.dfs.getWeather());
		dayForecast.getChildren().addAll(day, temp, weather);
		
		return dayForecast;
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
		
		//handles weather
		VBox weather = new VBox(getCurrentWeather());
		
		//maybe make a vbox that contains both the weatherBox and the forecastBox, thenn add them to the wholeBox
		if (weatherBox.getChildren().size() == 1) {
			weatherBox.getChildren().clear();
		}
		
		weatherBox.getChildren().addAll(weather);
		
		if (!this.wholeBox.getChildren().contains(weatherBox)) {
			this.wholeBox.getChildren().add(weatherBox);
		}
		
		//handles forecast
		VBox forecast = new VBox(getForecastDay(this.dfs.getCurrentDay()));
		
		if (forecastBox.getChildren().size() == 5) {
			forecastBox.getChildren().clear();
		}

		forecastBox.getChildren().addAll(forecast);
		
		if (!this.wholeBox.getChildren().contains(forecastBox)) {
			this.wholeBox.getChildren().addAll(forecastBox);
		}
	}
}
