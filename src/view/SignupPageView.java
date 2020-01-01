package view;

import controller.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupPageView extends FlowPane {
	
	private Stage stage;
	public Button signupButton = new Button("Signup");
	public Button accountButton = new Button("Already have an account?");
	TextField username = new TextField();
	PasswordField password = new PasswordField();
	
	public SignupPageView(Stage stage) {
		
		this.stage = stage;
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(40, 10, 40, 10));
		this.setStyle("-fx-background-color:"
				+ "linear-gradient(from 25% 25% to 100% 100%, rgb(254, 152, 154), rgb(248, 225, 137));"
				+ " -fx-border-color:black;");
		
		VBox loginBox = new VBox(15);
		
		username.setPrefColumnCount(17);
		username.setMinSize(30,  30);
		username.setPromptText("enter your desired username");
		username.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }");
		
		password.setPrefColumnCount(17);
		password.setMinSize(30,  30);
		password.setPromptText("enter your desired password");
		password.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }");
		
		HBox buttonsBox = new HBox(10);
		signupButton.setMinSize(60, 30);
		
		accountButton.setMinHeight(30);
		
		buttonsBox.getChildren().addAll(signupButton, accountButton);
		buttonsBox.setAlignment(Pos.CENTER);
		
		//setting up enter key to work for logging in
		username.setOnKeyPressed(new EnterKeypressHandler(signupButton));
		password.setOnKeyPressed(new EnterKeypressHandler(signupButton));
		
		loginBox.getChildren().addAll(username, password, buttonsBox);
		
		this.getChildren().addAll(loginBox);
	}
	
	public TextField getUsername() {
		return this.username;
	}
	
	public TextField getPassword() {
		return this.password;
	}
}
