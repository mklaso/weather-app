package view;

import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import controller.EnterKeypressHandler;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.*;
import resources.ImageResources;

public class WeatherSystemView extends AnchorPane implements Observer {
	
	private Stage stage;
	private DayForecastSystem dfs;
	public TextField locationField = new TextField();
	public Button searchButton = new Button();
	public String averageFontSize = "-fx-font-size: 16;";
	
	//path relative to user's machine
	private String path = new ImageResources().getImagePath();
	private Label location = new Label("Unknown");
	private Label day = new Label("Unknown");
	private Label date = new Label("--/--/----");
	private Label time = new Label("--:--");
	private Label currentTemp = new Label("N/A");
	private Label weather = new Label("Unknown");
	private Label minMaxFeelsTemp = new Label("N/A");
	private Label humidity = new Label("N/A% Humidity");
	private Label wind = new Label("Wind speeds of N/A");
	private Label sunrise = new Label("Sunrise at N/A ");
	private Label sunset = new Label("Sunset at N/A");
	private ImageView currentWeatherImage = new ImageView();
	public Button tb = new Button("°C");
	public Button tb2 = new Button("°F");

	private VBox day1 = new VBox();
	private VBox day2 = new VBox();
	private VBox day3 = new VBox();
	private VBox day4 = new VBox();
	private VBox day5 = new VBox();
	
	public WeatherSystemView(Stage stage, DayForecastSystem dfs) {
		this.stage = stage;
		this.dfs = dfs;
		
		this.stage.setMinWidth(825);
		this.stage.setMaxWidth(870);
		this.stage.setMinHeight(725);
		this.stage.setMaxHeight(770);
		
		this.setStyle("-fx-background-color: transparent;");
		setSize(this, 825, 725);
		
		//searchImage setup
		ImageView searchImage = new ImageView();
		this.setImage(searchImage, "search.png", 35, 35);
		AnchorPane.setTopAnchor(searchImage, 34.5);
		AnchorPane.setLeftAnchor(searchImage, 475.0);
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
		searchBox.setTranslateX(35);
		
		//menu image setup
		ImageView menuImage = new ImageView();
		this.setImage(menuImage, "menu.png", 30, 30);
		HBox.setMargin(menuImage, new Insets(0, 10, 0, 0));
		menuImage.setCursor(Cursor.HAND);
		
		//search field inside searchBox
		locationField.setStyle("-fx-font-size: 20; -fx-background-radius: 30;");
		locationField.setPromptText("enter a location");
		setSize(locationField, 252, 47);
		locationField.setOnKeyPressed(new EnterKeypressHandler(searchButton));
		
		//image to add locations
		ImageView plusImage = new ImageView();
		this.setImage(plusImage, "add.png", 40, 40);
		HBox.setMargin(plusImage, new Insets(0, 10, 0, 0));
		plusImage.setCursor(Cursor.HAND);
		
		//toggleHbox holds the two toggle buttons for C/F
		HBox toggleHbox = new HBox();
		setSize(toggleHbox, 85, 47);
		toggleHbox.setAlignment(Pos.CENTER);
		
		//toggle setup
		tb.setStyle(StyleSetter.toggleStyle);
		tb.setCursor(Cursor.HAND);
		tb2.setStyle(StyleSetter.toggleStyle2);
		tb2.setCursor(Cursor.HAND);
		
		toggleHbox.getChildren().addAll(tb, tb2);
		searchBox.getChildren().addAll(menuImage, locationField, plusImage, toggleHbox);
		
		//current weather vbox; underneath searchbox
		VBox weatherBox = new VBox();
		VBox.setMargin(weatherBox, new Insets(0, 0, 10, 0));
		weatherBox.setPadding(new Insets(0, 0, 20, 0));
		setSize(weatherBox, 637, 391);
		weatherBox.setAlignment(Pos.CENTER);
		weatherBox.setStyle("-fx-background-color: transparent;");
		weatherBox.setTranslateX(-20);
		
		topVbox.getChildren().addAll(searchBox, weatherBox);
		
		//inner hbox of weatherBox; holds location/image
		HBox locationBox = new HBox(15);
		setSize(locationBox, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		locationBox.setAlignment(Pos.CENTER);
		locationBox.setTranslateX(-10);
		locationBox.setTranslateY(-10);
		
		//pinpoint image
		ImageView pinpointImage = new ImageView();
		this.setImage(pinpointImage, "marker.png", 30, 30);
		
		//location label
		setSize(location, USE_COMPUTED_SIZE, 40);
		location.setStyle("-fx-font-size: 28; -fx-font-weight: BOLD;");
		location.setAlignment(Pos.CENTER_LEFT);
		
		locationBox.getChildren().addAll(pinpointImage, location);
		
		currentTemp.setStyle("-fx-font-size: 60;");
		weather.setStyle("-fx-font-size: 18; -fx-font-weight: 500;");
		weather.setTranslateY(-12.5);
		
		day.setStyle(averageFontSize);
		date.setStyle(averageFontSize);
		time.setStyle(averageFontSize);
		minMaxFeelsTemp.setStyle("-fx-font-size: 15; -fx-font-style: italic; -fx-font-weight: 500;");
		humidity.setStyle(averageFontSize);
		wind.setStyle(averageFontSize);
		sunrise.setStyle(averageFontSize);
		sunset.setStyle(averageFontSize);
		
		//vbox that holds the 3 day/date/time
		VBox container = new VBox();
		container.getChildren().addAll(day, date, time);
		container.setAlignment(Pos.CENTER);
		
		//another container
		HBox dayAndIcon = new HBox(10);
		setForecastImage(currentWeatherImage, "sky");
		dayAndIcon.getChildren().addAll(container, currentWeatherImage);
		dayAndIcon.setAlignment(Pos.CENTER);
		
		//holds all temps
		VBox tempBox = new VBox();
		tempBox.setAlignment(Pos.CENTER);
		tempBox.getChildren().addAll(currentTemp, minMaxFeelsTemp);
		minMaxFeelsTemp.setTranslateY(-10.0);

		//bottom hbox (5day forecast)
		HBox bottomHbox = new HBox();
		setSize(bottomHbox, 815, 220);
		bottomHbox.setAlignment(Pos.CENTER);
		bottomHbox.setStyle("-fx-background-color: "
				+ " linear-gradient(to left, rgb(226, 166, 255), rgb(252, 222, 124));");
		bottomHbox.setEffect(new DropShadow());
		
		//5 vbox setup to put inside forecast box
		setForecastVBox(day1, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day2, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day3, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day4, "N/A", "N/A", "N/A", "N/A", "N/A");
		setForecastVBox(day5, "N/A", "N/A", "N/A", "N/A", "N/A");
		
		weatherBox.getChildren().addAll(locationBox, dayAndIcon, tempBox, weather,
				humidity, wind, sunrise, sunset);
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
			e.printStackTrace();
		}
	}
	
	public void setForecastVBox(VBox vbox, String d, String dt, String t, String w, String i) {
		HBox.setMargin(vbox, new Insets(10, 10, 10, 10));
		setSize(vbox, 143, 200);
		Label day = new Label(d);
		Label date = new Label(dt);
		Label temp = new Label(t); 
		Label weather = new Label(w);
		day.setStyle("-fx-font-size: 18; -fx-font-weight: 500;");
		temp.setStyle("-fx-font-size: 18; -fx-font-weight: 700;");
		weather.setStyle("-fx-font-size: 16; -fx-font-weight: 600;");
		ImageView icon = new ImageView();
		this.setForecastImage(icon, i);
		
		vbox.setSpacing(5);
		vbox.setOpacity(0.95);
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
		date.setText(ws.getLocalDate());
		time.setText(ws.getLocalTime());		
		currentTemp.setText(ws.getCurrentTemp(ws.getTempMap()));
		weather.setText(ws.getWeatherStatus(ws.getAllDataMap()));
		humidity.setText(ws.getHumidity() + " Humidity");
		wind.setText("Wind speeds of " + ws.getWindConditions());
		setForecastImage(currentWeatherImage, ws.getWeatherStatus(ws.getAllDataMap()));
		minMaxFeelsTemp.setText(ws.getMaxTemp(ws.getTempMap()) + " / " + ws.getMinTemp(ws.getTempMap()) 
		+ " Feels like " + ws.getFeelsLikeTemp(ws.getTempMap()) );
		sunrise.setText("Sunrise at " + ws.getSunriseTime());
		sunset.setText("Sunset at " + ws.getSunsetTime());
	}
	
	public void setForecastImage(ImageView forecastImage, String weatherCondition) {
		//regex patterns to determine weather icons to be displayed
		Pattern snow = Pattern.compile("^.*(snow).*$");
		Pattern rain = Pattern.compile("^.*(rain).*$");
		Pattern clearsky = Pattern.compile("^.*(sky).*$");
		Pattern thunder = Pattern.compile("^.*(thunder).*$");
		Pattern clouds = Pattern.compile("^.*(overcast clouds|broken clouds).*$");
		Pattern cloudsDayNight = Pattern.compile("^.*(scattered clouds|few clouds).*$");
		Pattern mist = Pattern.compile("^.*(mist).*$");

		double height = 75.0;
		double width = 75.0;
		
		WeatherSystem ws = this.dfs.getWeatherSystem();
		
		int localTime = WeatherSystem.getTimeIn24Hr(ws.getLocalTime());
		int sunriseTime = WeatherSystem.getTimeIn24Hr(ws.getSunriseTime());
		int sunsetTime = WeatherSystem.getTimeIn24Hr(ws.getSunsetTime());

		if (localTime >= sunriseTime && localTime < sunsetTime) {
			if (cloudsDayNight.matcher(weatherCondition).matches()) {
				this.setImage(forecastImage, "sunclouds.png", width, height);
			} else if (clearsky.matcher(weatherCondition).matches()) {
				this.setImage(forecastImage, "sun.png", width, height);
			}
		} else if (localTime >= sunsetTime || localTime <= sunriseTime) {
			if (cloudsDayNight.matcher(weatherCondition).matches()) {
				this.setImage(forecastImage, "nightclouds.png", width, height);
			} else if (clearsky.matcher(weatherCondition).matches()) {
				this.setImage(forecastImage, "moon.png", width, height);
			}
		}
		
		if (clouds.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "clouds2.png", width, height);
		} else if (mist.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "mist.png", width, height);
		} else if (snow.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "snow.png", width, height);
		} else if (rain.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "rain.png", width, height);
		} else if (thunder.matcher(weatherCondition).matches()) {
			this.setImage(forecastImage, "storm.png", width, height);
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
		//for daily weather
		this.setCurrentWeather();
		
		//for 5 day forecast
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
