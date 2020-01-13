package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.DayForecastSystem;
import model.WeatherSystem;
import view.*;
import controller.*;
import javafx.scene.Scene;

public class WeatherApplication extends Application {
	
	Stage window;
	
	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		
		// MODEL
		WeatherSystem weatherModel = new WeatherSystem("Unknown");
		DayForecastSystem forecastModel = new DayForecastSystem(weatherModel);
		
		// VIEW setup
		WeatherSystemView weatherView = new WeatherSystemView(window, forecastModel);
		
		// MODEL -> VIEW hookup
		weatherModel.attach(weatherView);
		forecastModel.attach(weatherView);
	
		weatherView.searchButton.setOnAction(new LoadWeatherController
				(weatherView.locationField, forecastModel));
		
		
		// SCENES SETUP
		Scene weatherScene = new Scene(weatherView);
		
		// STAGE
		window.setScene(weatherScene);
		window.setTitle("Weather Application");
	    
		// LAUNCH THE GUI
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
