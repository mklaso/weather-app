package view;

import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.EnterKeypressHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.*;
import resources.ImageResources;

public class WeatherSystemView extends FlowPane implements Observer {
	
	private Stage stage;
	public Button goBackButton = new Button("Go Back");
	public TextField locationField = new TextField();
	private DayForecastSystem dfs;
	private VBox wholeBox = new VBox(10);
	public Button searchButton = new Button("Get Weather");
	private HBox forecastBox = new HBox(5);
	//private VBox weatherBox = new VBox(10);
	private ImageView imageView = new ImageView();
	
	//for current weather
	private ImageView currentWeatherImage = new ImageView();
	
	//hbox for current weather stats + currentWeatherImage
	private HBox weatherHBOX = new HBox(5);
	
	//no longer hardcoded, relative to user's location
	private String path = new ImageResources().getImagePath();
	
	
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
		locationField.setStyle(StyleSetter.TEXT);
		
		searchButton.setMinSize(30, 30);
		searchButton.setStyle(StyleSetter.REGULAR);
		goBackButton.setMinSize(30, 30);
		goBackButton.setStyle(StyleSetter.REGULAR);
		
		//allows enter button to be used for making a search
		locationField.setOnKeyPressed(new EnterKeypressHandler(searchButton));
		
		searchBox.getChildren().addAll(locationField, searchButton, goBackButton);
		wholeBox.getChildren().add(searchBox);
		
		this.getChildren().addAll(wholeBox);
		
		StyleSetter.modifyColour(searchButton, StyleSetter.REGULAR, StyleSetter.HIGHLIGHT);
		StyleSetter.modifyColour(goBackButton, StyleSetter.REGULAR, StyleSetter.HIGHLIGHT);
		
		FileInputStream inputstream;


		try {
			inputstream = new FileInputStream(path + "giphy.gif");
			Image image = new Image(inputstream); 
			ImageView currentView = new ImageView(image);
			currentView.setFitHeight(200.0);
			currentView.setFitWidth(200.0);
			this.getChildren().add(currentView);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public VBox getCurrentWeather() {
		WeatherSystem ws = this.dfs.getWeatherSystem();
		VBox currentWeather = new VBox(3);
		
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

		//sets currentWeather image to match weather condition at current time
		this.currentWeatherImage = getWeatherImage(ws.getWeatherStatus(ws.getAllDataMap()));
		
		currentWeather.getChildren().addAll(city, location, currentTemp, feelsTemp,
				minTemp, maxTemp, humidity, wind, weather, sunrise, sunset);
		
		return currentWeather;
	}
	
	public VBox getForecastDay(int n) {
		
		
		VBox dayForecast = new VBox();
		Label day = new Label(this.dfs.getDay());
		Label temp = new Label(this.dfs.getTemp());
		Label weather = new Label(this.dfs.getWeather());
		imageView = this.getWeatherImage(this.dfs.getWeather());
		dayForecast.getChildren().addAll(day, imageView, temp, weather);
		dayForecast.setPadding(new Insets(40, 10, 10, 10));
		
		return dayForecast;
	}
	
	public ImageView getWeatherImage(String weatherCondition) {
		//regex patterns to determine weather icons to be displayed
		Pattern snow = Pattern.compile("^.*(snow).*$");
		Pattern rain = Pattern.compile("^.*(rain).*$");
		Pattern clearsky = Pattern.compile("^.*(sky).*$");
		Pattern thunder = Pattern.compile("^.*(thunder).*$");
		Pattern clouds = Pattern.compile("^.*(cloud).*$");
		
		FileInputStream inputstream;

		try {
			if (snow.matcher(weatherCondition).matches()) {
				inputstream = new FileInputStream(path + "icons8_snowflake_125px.png");
			} else if (rain.matcher(weatherCondition).matches()) {
				inputstream = new FileInputStream(path + "icons8_rainfall_125px.png");
			} else if (thunder.matcher(weatherCondition).matches()) {
				inputstream = new FileInputStream(path + "icons8_storm_125px.png");
			} else if (clouds.matcher(weatherCondition).matches()) {
				inputstream = new FileInputStream(path + "icons8_clouds_125px.png");
			} else if (clearsky.matcher(weatherCondition).matches()) {
				inputstream = new FileInputStream(path + "icons8_sun_125px.png");
			} else {
				inputstream = new FileInputStream(path + "icons8_sun_125px.png");
			}
			
			Image image = new Image(inputstream); 
			ImageView currentView = new ImageView(image);
			currentView.setFitHeight(50.0);
			currentView.setFitWidth(50.0);
			return currentView;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageView;
	}

	@Override
	public void update(Observable o) {
		
		//handles weather
		VBox weather = new VBox(getCurrentWeather());
		currentWeatherImage.setFitHeight(100.0);
		currentWeatherImage.setFitWidth(100.0);
		
		//maybe make a vbox that contains both the weatherBox and the forecastBox, thenn add them to the wholeBox
		if (weatherHBOX.getChildren().size() == 2) {
			weatherHBOX.getChildren().clear();
		}
		
		weatherHBOX.getChildren().addAll(weather, currentWeatherImage);
		HBox.setMargin(currentWeatherImage, new Insets(45, 0, 0, 75));
		weatherHBOX.setPadding(new Insets(30, 0, 0, 0));
		
		if (!this.wholeBox.getChildren().contains(weatherHBOX)) {
			this.wholeBox.getChildren().add(weatherHBOX);
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
