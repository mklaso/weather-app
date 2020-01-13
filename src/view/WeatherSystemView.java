package view;

import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.EnterKeypressHandler;
import controller.LoadWeatherController;
import controller.ToggleButtonController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.*;
import resources.ImageResources;

public class WeatherSystemView extends AnchorPane implements Observer {
	
	private Stage stage;
	public Button goBackButton = new Button("Go Back");
	public TextField locationField = new TextField();
	private DayForecastSystem dfs;
	public Button searchButton = new Button("Get Weather");
	public String averageFontSize = "-fx-font-size: 18;";
	
	//no longer hardcoded, relative to user's location
	private String path = new ImageResources().getImagePath();
	
	Label location = new Label("Unknown");
	Label day = new Label("Unknown");
	Label date = new Label("--/--/----");
	Label time = new Label("--:--");
	Label currentTemp = new Label("N/A"); //make this big af
	Label weather = new Label("Unknown");
	Label feelsTemp = new Label("Feels like N/A");
	Label humidity = new Label("N/A% Humidity");
	Label wind = new Label("Wind speeds of N/A");
	ImageView currentWeatherImage = new ImageView();
	
	//add these 4 somewhere in after the rest above is setup properly
	Label minTemp = new Label("Min temperature: ");
	Label maxTemp = new Label("Max temperature: ");
	Label sunrise = new Label("Sunrise at: ");
	Label sunset = new Label("Sunset at: ");
	
	VBox day1 = new VBox();
	VBox day2 = new VBox();
	VBox day3 = new VBox();
	VBox day4 = new VBox();
	VBox day5 = new VBox();

	
	public WeatherSystemView(Stage stage, DayForecastSystem dfs) {
		this.stage = stage;
		this.dfs = dfs;
		
		this.setStyle("-fx-background-color: transparent;");
		setSize(this, 825, 725);
		
		//searchImage setup
		ImageView searchImage = new ImageView();
		this.setImage(searchImage, "icons8_search_100px.png", 35, 35);
		AnchorPane.setTopAnchor(searchImage, 34.5);
		AnchorPane.setLeftAnchor(searchImage, 440.0);
		searchImage.setCursor(Cursor.HAND);
		searchImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		     @Override
		     public void handle(MouseEvent event) {
		    	 searchButton.fire();
		     }
		});
		
		//stackpane main layout background
		StackPane stackPane = new StackPane();
		AnchorPane.setBottomAnchor(stackPane, 5.0);
		AnchorPane.setTopAnchor(stackPane, 5.0);
		AnchorPane.setLeftAnchor(stackPane, 5.0);
		AnchorPane.setRightAnchor(stackPane, 5.0);
		setSize(stackPane, 815, 715);
		stackPane.setAlignment(Pos.CENTER);
		stackPane.setStyle("-fx-background-color: orange;");
		stackPane.setEffect(new DropShadow());
		
		//main vbox that holds the other stuff
		VBox mainVbox = new VBox();
		setSize(mainVbox, 815, 715);
		
		//top vbox (current weather/search)
		VBox topVbox = new VBox();
		setSize(topVbox, 815, 495);
		topVbox.setAlignment(Pos.CENTER);
		topVbox.setStyle("-fx-background-color: "
				+ "linear-gradient(from 25% 25% to 100% 100%, rgb(177, 249, 254), rgb(226, 166, 255));");
		
		//inner hbox (inside topVbox - searching hbox)
		HBox searchBox = new HBox(10);
		setSize(searchBox, 527, 96);
		searchBox.setAlignment(Pos.CENTER);
		searchBox.setStyle("-fx-background-color: transparent;");
		
		//menu image setup
		ImageView menuImage = new ImageView();
		this.setImage(menuImage, "icons8_menu_30px_1.png", 30, 30);
		HBox.setMargin(menuImage, new Insets(0, 10, 0, 0));
		menuImage.setCursor(Cursor.HAND);
		
		//search field inside searchBox
		locationField.setStyle("-fx-font-size: 20; -fx-background-radius: 30;");
		locationField.setPromptText("enter a location");
		setSize(locationField, 252, 47);
		locationField.setOnKeyPressed(new EnterKeypressHandler(searchButton));
		
		//image to add locations
		ImageView plusImage = new ImageView();
		this.setImage(plusImage, "icons8_plus_math_40px.png", 40, 40);
		HBox.setMargin(plusImage, new Insets(0, 10, 0, 0));
		plusImage.setCursor(Cursor.HAND);
		
		//toggleHbox holds the two toggle buttons for C/F
		HBox toggleHbox = new HBox();
		setSize(toggleHbox, 85, 47);
		toggleHbox.setAlignment(Pos.CENTER);
		
		//toggle setup
		String toggleStyle = "-fx-font-size: 18; -fx-background-radius: 0; "
				+ "-fx-background-color: white; -fx-border-color: grey;";
		ToggleButton tb = new ToggleButton("°C");
		ToggleButton tb2 = new ToggleButton("°F");
		tb.setStyle(toggleStyle);
		tb.setCursor(Cursor.HAND);
		tb2.setStyle(toggleStyle);
		tb2.setCursor(Cursor.HAND);
		
		toggleHbox.getChildren().addAll(tb, tb2);
		tb.setOnAction(new ToggleButtonController(tb, tb2));
		tb2.setOnAction(new ToggleButtonController(tb2, tb));
		
		searchBox.getChildren().addAll(menuImage, locationField, plusImage, toggleHbox);
		
		//current weather vbox; underneath searchbox
		VBox weatherBox = new VBox(5);
		VBox.setMargin(weatherBox, new Insets(0, 0, 10, 0));
		weatherBox.setPadding(new Insets(0, 0, 20, 0));
		setSize(weatherBox, 637, 391);
		weatherBox.setAlignment(Pos.CENTER);
		weatherBox.setStyle("-fx-background-color: transparent;");
		
		topVbox.getChildren().addAll(searchBox, weatherBox);
		
		//inner hbox of weatherBox; holds location/image
		HBox locationBox = new HBox(15);
		setSize(locationBox, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		locationBox.setAlignment(Pos.CENTER);
		
		//pinpoint image
		ImageView pinpointImage = new ImageView();
		this.setImage(pinpointImage, "icons8_marker_50px.png", 30, 30);
		
		//location label
		setSize(location, USE_COMPUTED_SIZE, 40);
		location.setStyle("-fx-font-size: 28; -fx-font-weight: BOLD;");
		location.setAlignment(Pos.CENTER_LEFT);
		
		locationBox.getChildren().addAll(pinpointImage, location);
		
		currentTemp.setStyle("-fx-font-size: 60;");
		weather.setStyle("-fx-font-size: 22;");
		
		day.setStyle(averageFontSize);
		date.setStyle(averageFontSize);
		time.setStyle(averageFontSize);
		feelsTemp.setStyle(averageFontSize);
		humidity.setStyle(averageFontSize);
		wind.setStyle(averageFontSize);
		
		//vbox that holds the 3 day/date/time
		VBox container = new VBox(5);
		container.getChildren().addAll(day, date, time);
		container.setAlignment(Pos.CENTER);
		
		//another container
		HBox dayAndIcon = new HBox(10);
		setForecastImage(currentWeatherImage, "sky");
		dayAndIcon.getChildren().addAll(container, currentWeatherImage);
		dayAndIcon.setAlignment(Pos.CENTER);
		
		//add to this
		weatherBox.getChildren().addAll(locationBox, dayAndIcon, currentTemp, weather,
				feelsTemp, humidity, wind);

		//bottom hbox (5day forecast)
		HBox bottomHbox = new HBox();
		setSize(bottomHbox, 815, 220);
		bottomHbox.setAlignment(Pos.CENTER);
		bottomHbox.setStyle("-fx-background-color: "
				+ " linear-gradient(to left, rgb(212, 122, 230), rgb(252, 222, 124));");
		bottomHbox.setEffect(new DropShadow());
		
		//5 vbox setup to put inside forecast box
		setForecastVBox(day1, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day2, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day3, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day4, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day5, "N/A", "N/A", "N/A", "N/A", "N/A");
		
		bottomHbox.getChildren().addAll(day1, day2, day3, day4, day5);
		mainVbox.getChildren().addAll(topVbox, bottomHbox);
		stackPane.getChildren().addAll(mainVbox);
		
		
		this.getChildren().addAll(stackPane, searchImage);
	}
	
	public void setImage(ImageView view, String endOfPath, double width, double height) {
		FileInputStream inputStream;
		
		try {
			inputStream = new FileInputStream(path + endOfPath);
			Image image = new Image(inputStream);
			view.setImage(image);
			view.setFitHeight(height);
			view.setFitWidth(width);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setForecastVBox(VBox vbox, String d, String dt, String t, String w, String i) {
		HBox.setMargin(vbox, new Insets(10, 10, 10, 10));
		setSize(vbox, 143, 200);
		vbox.setSpacing(5);
		Label day = new Label(d);
		Label date = new Label(dt);
		Label temp = new Label(t); 
		Label weather = new Label(w);
		day.setStyle(averageFontSize);
		temp.setStyle(averageFontSize);
		weather.setStyle(averageFontSize);
		ImageView icon = new ImageView();
		this.setForecastImage(icon, i);

		vbox.setAlignment(Pos.CENTER);
		vbox.setEffect(new DropShadow());
		vbox.setStyle("-fx-background-color: beige;");
		vbox.getChildren().addAll(day, date, icon, temp, weather);
	}
	
	public static void setSize(Region r, double width, double height) {
		r.setMinWidth(width);
		r.setMaxWidth(width);
		r.setMinHeight(height);
		r.setMaxHeight(height);
	}
	
	public void setCurrentWeather() {
		WeatherSystem ws = this.dfs.getWeatherSystem();
	
		location.setText(ws.getLocation());
		day.setText(ws.getCurrentDay());
		//add a date one as well
		date.setText(ws.getLocalDate());
		time.setText(ws.getLocalTime());
		currentTemp.setText(ws.getCurrentTemp(ws.getTempMap()));
		weather.setText(ws.getWeatherStatus(ws.getAllDataMap()));
		feelsTemp.setText("Feels like " + ws.getFeelsLikeTemp(ws.getTempMap()));
		humidity.setText(ws.getHumidity() + " Humidity");
		wind.setText("Wind speeds of " + ws.getWindConditions());
		setForecastImage(currentWeatherImage, ws.getWeatherStatus(ws.getAllDataMap()));
		
		//add these somewhere to the view later
		maxTemp.setText("Daily high of " + ws.getMaxTemp(ws.getTempMap()));
		minTemp.setText("Daily low of " + ws.getMinTemp(ws.getTempMap()));
		sunrise.setText("Sunrise at " + ws.getSunriseTime());
		sunset.setText("Sunset at " + ws.getSunsetTime());
	}
	
	public void setForecastImage(ImageView forecastImage, String weatherCondition) {
		//regex patterns to determine weather icons to be displayed
		Pattern snow = Pattern.compile("^.*(snow).*$");
		Pattern rain = Pattern.compile("^.*(rain).*$");
		Pattern clearsky = Pattern.compile("^.*(sky).*$");
		Pattern thunder = Pattern.compile("^.*(thunder).*$");
		Pattern clouds = Pattern.compile("^.*(cloud).*$");

		double height = 75.0;
		double width = 75.0;
		
		if (snow.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "icons8_snowflake_125px.png", width, height);
		} else if (rain.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "icons8_rainfall_125px.png", width, height);
		} else if (thunder.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "icons8_storm_125px.png", width, height);
		} else if (clouds.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "icons8_clouds_125px.png", width, height);
		} else if (clearsky.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "icons8_sun_125px.png", width, height);
		} else {
			this.setImage(forecastImage, "icons8_sun_125px.png", width, height);
		}
	}
	
	public void updateForecastVBox(int number) {
		
		//updates forecast corresponding forecast box with relevant location data
		if (number == 0) {
			day1.getChildren().clear();
			this.setForecastVBox(day1, this.dfs.getDay(), this.dfs.getDate(), this.dfs.getTemp(),
					this.dfs.getWeather(), this.dfs.getWeather());
		} else if (number == 1) {
			day2.getChildren().clear();
			this.setForecastVBox(day2, this.dfs.getDay(), this.dfs.getDate(), this.dfs.getTemp(),
					this.dfs.getWeather(), this.dfs.getWeather());
		} else if (number == 2) {
			day3.getChildren().clear();
			this.setForecastVBox(day3, this.dfs.getDay(), this.dfs.getDate(), this.dfs.getTemp(),
					this.dfs.getWeather(), this.dfs.getWeather());
		} else if (number == 3) {
			day4.getChildren().clear();
			this.setForecastVBox(day4, this.dfs.getDay(), this.dfs.getDate(), this.dfs.getTemp(),
					this.dfs.getWeather(), this.dfs.getWeather());
		} else if (number == 4) {
			day5.getChildren().clear();
			this.setForecastVBox(day5, this.dfs.getDay(), this.dfs.getDate(), this.dfs.getTemp(),
					this.dfs.getWeather(), this.dfs.getWeather());
		}
	}

	@Override
	public void update(Observable o) {
		//updates the daily weather
		this.setCurrentWeather();
		
		//updates all forecast data
		this.dfs.setForecastDay(1);
		this.updateForecastVBox(this.dfs.getCurrentDay());
		this.dfs.setForecastDay(2);
		this.updateForecastVBox(this.dfs.getCurrentDay());
		this.dfs.setForecastDay(3);
		this.updateForecastVBox(this.dfs.getCurrentDay());
		this.dfs.setForecastDay(4);
		this.updateForecastVBox(this.dfs.getCurrentDay());
		this.dfs.setForecastDay(5);
		this.updateForecastVBox(this.dfs.getCurrentDay());
	}
}
