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
		
//		System.out.println(aoObtainer.getMACAddress());
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
