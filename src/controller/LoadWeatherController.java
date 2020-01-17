package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import model.DayForecastSystem;
import model.WeatherSystem;

public class LoadWeatherController implements EventHandler<ActionEvent> {
	
	private TextField searchResult;
	private DayForecastSystem dfs;
	private String lastValidLocation = "";
	
	public LoadWeatherController(TextField searchResult, DayForecastSystem dfs) {
		this.searchResult = searchResult;
		this.setDfs(dfs);
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		
		String location = searchResult.getText();
		
		if (!location.equals("")) {
			WeatherSystem ws = this.dfs.getWeatherSystem();
			
			ws.setLocation(location);
			this.dfs.updateWeatherSystem(ws);
			
			boolean valid = ws.getValidity();
		    
		    if (valid) {
		    	lastValidLocation = location;
		    	this.dfs.isValidSearch = true;
		    	ws.get5DayForecast();
		    	this.dfs.hasSearchOccurred = true;
		    	
				if (this.dfs.forecastDataExists()) {
					this.dfs.notifyObservers();
				}
				
		    } else {
		    	ws.setLocation(lastValidLocation);
		    	this.dfs.isValidSearch = false;
		    	ws.get5DayForecast();
		    	this.dfs.hasSearchOccurred = false;
		    	
		    	if (this.dfs.forecastDataExists()) {
					this.dfs.notifyObservers();
				}
		    }
		    
		}
	}

	public DayForecastSystem getDfs() {
		return dfs;
	}

	public void setDfs(DayForecastSystem dfs) {
		this.dfs = dfs;
	}
}
