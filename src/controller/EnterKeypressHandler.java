package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EnterKeypressHandler implements EventHandler<KeyEvent>{
	
	private Button button;
	
	public EnterKeypressHandler(Button button) {
		this.button = button;
	}
	
	@Override
	public void handle(KeyEvent event) {
		
		//if enter key is pressed, it triggers the
		//button's ActionEvent handler (LoginController or LoadWeatherController)
		
		if (event.getCode().equals(KeyCode.ENTER)) {
			this.button.fire();
		}
		
	}

}
