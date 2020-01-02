package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import model.DayForecastSystem;
import model.WeatherSystem;

public class LoadWeatherController implements EventHandler<ActionEvent> {
	
	private TextField question;
	
	public LoadWeatherController(TextField question) {
		this.question = question;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		String location = question.getText();
		WeatherSystem ws = new WeatherSystem(location);
		
		ws.printWeatherData();
		
		DayForecastSystem dfs = new DayForecastSystem(ws);
		
		dfs.setForecastDay(1);
		dfs.printForecastDay();
		
		dfs.setForecastDay(2);
		dfs.printForecastDay();
		
		dfs.setForecastDay(3);
		dfs.printForecastDay();
		
		dfs.setForecastDay(4);
		dfs.printForecastDay();
		
		dfs.setForecastDay(5);
		dfs.printForecastDay();
	}
}
