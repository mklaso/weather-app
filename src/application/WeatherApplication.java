package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;
import view.*;
import controller.*;
import javafx.scene.Scene;

public class WeatherApplication extends Application {
	
	Stage window;
	private AddressObtainer aoObtainer = new AddressObtainer();
	
	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		
		// MODEL
		WeatherSystem weatherModel = new WeatherSystem("");
		DayForecastSystem forecastModel = new DayForecastSystem(weatherModel);
		
		// VIEW setup
		WeatherSystemView weatherView = new WeatherSystemView(window, forecastModel);
		
		// MODEL -> VIEW hookup
		weatherModel.attach(weatherView);
		forecastModel.attach(weatherView);
		
		LoadWeatherController weatherController = new LoadWeatherController
				(weatherView.locationField, forecastModel);
		
		weatherView.searchButton.setOnAction(weatherController);
		
		weatherView.tb.setOnAction(new ToggleButtonController(weatherView.tb, weatherView.tb2, weatherController));
		weatherView.tb2.setOnAction(new ToggleButtonController(weatherView.tb, weatherView.tb2, weatherController));
		
		// SCENES SETUP
		Scene weatherScene = new Scene(weatherView);
		
		// STAGE
		window.setScene(weatherScene);
		window.setTitle("Weather Application");
		window.setResizable(false); 

		String macAddress = aoObtainer.getMACAddress();

		// LAUNCH THE GUI
		window.show();
		
		//database stuff
		weatherView.database.connect();
		weatherView.database.connectionStatus();
		weatherView.database.login();
		
		if (!weatherView.database.isRegistered(macAddress)) {
			weatherView.database.registerUser(macAddress);
			System.out.println("User registered!");
		}
		
		//load database locations for user
		weatherView.database.setDbLocationsData(weatherView.locationsList);
		
		for (int i = 0; i < weatherView.locationsList.size(); i++) {
			System.out.println(i+1 + ": " + weatherView.locationsList.get(i) + "\n");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
