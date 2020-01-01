package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class LoadWeatherController implements EventHandler<ActionEvent> {
	
	private TextField question;
	
	public LoadWeatherController(TextField question) {
		this.question = question;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		System.out.println(this.question.getText());
		
	}
}
