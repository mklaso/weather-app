package controller;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.*;

public class SceneChangeController implements EventHandler<ActionEvent> {
	
	Stage window;
	public ArrayList<Scene> sceneReferences = new ArrayList<Scene>();
	WeatherSystemView weatherView;
	LoginPageView loginView;
	SignupPageView signupView;
	
	public SceneChangeController(WeatherSystemView weatherView,
			LoginPageView loginView, SignupPageView signupView) {
		this.weatherView = weatherView;
		this.loginView = loginView;
		this.signupView = signupView;
	}
	
	/**
	 * Set reference to the specific stage
	 * 
	 * @param stage
	 */
	public void setStageReference(Stage stage) {
		this.window = stage;
	}
	
	/**
	 * Add scene to the arraylist
	 * 
	 * @param scene
	 */
	public void setSceneReference(Scene scene) {
		this.sceneReferences.add(scene);
	}
	
	/**
	 * 
	 * @param n, the index
	 * @return scene at the given index
	 */
	public Scene getSceneReference(int n) {
		return this.sceneReferences.get(n);
	}
	
	public void setWindowDimensions(double height, double width) {
		this.window.setMinWidth(width);
		this.window.setMaxWidth(width);
		this.window.setMinHeight(height);
		this.window.setMaxHeight(height);
	}
	
	public void setWindowAttributes(ViewType viewType, double height, double width, int sceneNumber, String titleName) {
		this.window.setScene(getSceneReference(sceneNumber));
		viewType.getUsername().clear();
		viewType.getPassword().clear();
		this.window.setTitle(titleName);
		this.setWindowDimensions(height, width);
	}

	@Override
	public void handle(ActionEvent event) {
		String whichButton = ((Button)event.getSource()).getText();
			//routes to signup page
		if (whichButton.equals("Create an account")) {
			this.setWindowAttributes(loginView, 266.3999938964844, 438.79998779296875, 1, "Signup");

			//routes to login page
		} else if (whichButton.equals("Already have an account?")) {
			this.setWindowAttributes(signupView, 266.3999938964844, 436.79998779296875, 0, "Login");
			
			//routes to login page (presumably from a logout button on weather page)
		} else {
			this.setWindowAttributes(signupView, 266.3999938964844, 436.79998779296875, 0, "Login");
			weatherView.locationField.clear();
		}
	}
}
