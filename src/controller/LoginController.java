package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoginController implements EventHandler<ActionEvent> {

	private TextField username;
	private TextField password;
	private SceneChangeController sceneChanger;
	
	//signup handler will be very similar to this, except when you sign up, it'll check
	//if the username already exists within the database, if it doesn't, it'll create a new
	//user/pass entry in the database and will register the user -> this leads to a scene change
	//from the register page to the login page
	//if the username already exists, a popout/alert message will come up saying the user already exists.
	
	public LoginController(TextField username, TextField password, 
			SceneChangeController sceneChanger) {
		this.username = username;
		this.password = password;
		this.sceneChanger = sceneChanger;
	}
	
	@Override
	public void handle(ActionEvent event) {
		String whichButton = ((Button)event.getSource()).getText();
		
		if (whichButton.equals("Login")) {
			sceneChanger.window.setScene(sceneChanger.getSceneReference(2));
			sceneChanger.loginView.getUsername().clear();
			sceneChanger.loginView.getPassword().clear();
		} else if (whichButton.equals("Signup")) {
			sceneChanger.window.setScene(sceneChanger.getSceneReference(0));
			sceneChanger.signupView.getUsername().clear();
			sceneChanger.signupView.getPassword().clear();
		}
		//when the database is setup w/ SqLite, this part should check
		//if the username/password exists within the database,
		//then if it does, make a scene change (successful login) that
		//directs the user to the weather app scene
		//if the username/password doesn't exist/is wrong, have an alert/popout
		//message that says the parameters entered are wrong/do not exist and
		//to try again
		
		System.out.println(this.username.getText());
		System.out.println(this.password.getText());
		
		
		
	}
}

