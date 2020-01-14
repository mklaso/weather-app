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
		this.setDfs(dfs);
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		String location = searchResult.getText();
		
		WeatherSystem ws = this.dfs.getWeatherSystem();
		ws.setLocation(location);
		ws.get5DayForecast();
		
	    this.dfs.updateWeatherSystem(ws);

		if (this.dfs.forecastDataExists()) {
			this.dfs.notifyObservers();
		} else  {
			this.dfs.setInvalid5DayForecast();
		}
	}

	public DayForecastSystem getDfs() {
		return dfs;
	}

	public void setDfs(DayForecastSystem dfs) {
		this.dfs = dfs;
	}
}
