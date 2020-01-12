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
		// TODO Auto-generated method stub
		if (b1.isSelected()) {
			b2.setSelected(false);
		}
		
		if (b2.isSelected()) {
			b1.setSelected(false);
		}
		
	}

}
