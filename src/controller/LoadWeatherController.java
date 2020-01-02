package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import model.WeatherSystem;

public class LoadWeatherController implements EventHandler<ActionEvent> {
	
	private TextField question;
	
	public LoadWeatherController(TextField question) {
		this.question = question;
	}
	
	@Override
	public void handle(ActionEvent event) {
		System.out.println(this.question.getText());
		
		String location = question.getText();
		WeatherSystem ws = new WeatherSystem(location);
		
		//ws.printWeatherData();
		System.out.println(ws.getCurrentTemp(ws.getTempMap()));
		ws.get5DayForecast();
	}
}
