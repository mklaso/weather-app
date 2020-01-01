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

	@Override
	public void handle(ActionEvent event) {
		String whichButton = ((Button)event.getSource()).getText();
			//routes to signup page
		if (whichButton.equals("Create an account")) {
			this.window.setScene(getSceneReference(1));
			loginView.getUsername().clear();
			loginView.getPassword().clear();
			this.window.setTitle("Signup");
			//routes to login page
		} else if (whichButton.equals("Already have an account?")) {
			this.window.setScene(getSceneReference(0));
			signupView.getUsername().clear();
			signupView.getPassword().clear();
			this.window.setTitle("Login");
			
			//routes to login page (presumably from a logout button on weather page)
		} else {
			this.window.setScene(getSceneReference(0));
			weatherView.locationField.clear();
			this.window.setTitle("Login");
		}
		
	}
}
