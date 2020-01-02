package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import model.DayForecastSystem;
import model.WeatherSystem;

public class LoadWeatherController implements EventHandler<ActionEvent> {
	
	private TextField question;
	private DayForecastSystem dfs;
	
	public LoadWeatherController(TextField question, DayForecastSystem dfs) {
		this.question = question;
		this.dfs = dfs;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		String location = question.getText();

		WeatherSystem ws = this.dfs.getWeatherSystem();
		ws.setLocation(location);
		ws.get5DayForecast();
		
	    this.dfs.updateWeatherSystem(ws);
		//ws.printWeatherData();
	    this.dfs.print5DayForecast();

		if (this.dfs.forecastDataExists()) {
			this.dfs.setForecastDay(1);
			this.dfs.setForecastDay(2);	
			this.dfs.setForecastDay(3);
			this.dfs.setForecastDay(4);
			this.dfs.setForecastDay(5);
		} else  {
			this.dfs.setInvalid5DayForecast();
		}
	}
}
