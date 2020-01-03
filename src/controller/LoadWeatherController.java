package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import model.DayForecastSystem;
import model.WeatherSystem;

public class LoadWeatherController implements EventHandler<ActionEvent> {
	
	private TextField searchResult;
	private DayForecastSystem dfs;
	
	public LoadWeatherController(TextField searchResult, DayForecastSystem dfs) {
		this.searchResult = searchResult;
		this.dfs = dfs;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		String location = searchResult.getText();

		WeatherSystem ws = this.dfs.getWeatherSystem();
		ws.setLocation(location);
		ws.get5DayForecast();
		
	    this.dfs.updateWeatherSystem(ws);
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
