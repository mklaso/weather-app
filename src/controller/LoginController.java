package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements EventHandler<ActionEvent> {

	private TextField username;
	private TextField password;
	private SceneChangeController sceneChanger;
	
	//signup handler will be very similar to this, except when you sign up, it'll check
	//if the username already exists within the database, if it doesn't, it'll create a new
	//user/pass entry in the database and will register the user -> this leads to a scene change
	//from the register page to the login page
	//if the username already exists, a popout/alert message will come up saying the user already exists.

	//when the database is setup w/ SqLite, this part should check
	//if the username/password exists within the database,
	//then if it does, make a scene change (successful login) that
	//directs the user to the weather app scene
	//if the username/password doesn't exist/is wrong, have an alert/popout
	//message that says the parameters entered are wrong/do not exist and
	//to try again
	
	public LoginController(TextField username, TextField password, 
			SceneChangeController sceneChanger) {
		this.username = username;
		this.password = password;
		this.sceneChanger = sceneChanger;
	}
	
	@Override
	public void handle(ActionEvent event) {
		String whichButton = ((Button)event.getSource()).getText();
		
			//routes to weather forecast page
		if (whichButton.equals("Login")) {
			sceneChanger.setWindowAttributes(sceneChanger.loginView, 600, 600, 2, "Weather Forecast");
			//routes to login page
		} else if (whichButton.equals("Signup")) {
			sceneChanger.setWindowAttributes(sceneChanger.signupView, 266.3999938964844, 436.79998779296875, 0, "Login");
		}
	}
}

