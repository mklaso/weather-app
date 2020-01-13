package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class ToggleButtonController implements EventHandler<ActionEvent> {
	
	private ToggleButton b1;
	private ToggleButton b2;
	
	public ToggleButtonController(ToggleButton b1, ToggleButton b2) {
		this.b1 = b1;
		this.b2 = b2;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		String toggleStyle = "-fx-font-size: 18; -fx-background-radius: 0; "
				+ "-fx-background-color: grey; -fx-border-color: white;";
		String toggleStyle2 = "-fx-font-size: 18; -fx-background-radius: 0; "
				+ "-fx-background-color: white; -fx-border-color: grey;";
		
		
		if (b1.isSelected()) {
			b1.setStyle(toggleStyle);
			b2.setSelected(false);
			b2.setStyle(toggleStyle2);
		}
		
		if (b2.isSelected()) {
			b2.setStyle(toggleStyle);
			b1.setSelected(false);
			b1.setStyle(toggleStyle2);
		}	
	}
}
