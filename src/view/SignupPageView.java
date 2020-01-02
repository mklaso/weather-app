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
	private TextField username = new TextField();
	private PasswordField password = new PasswordField();
	
	public SignupPageView(Stage stage) {
		
		this.stage = stage;
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(40, 10, 40, 10));
		this.setStyle("-fx-background-color:"
				+ "linear-gradient(from 25% 25% to 100% 100%, rgb(169, 169, 252), rgb(177, 249, 254));"
				+ " -fx-border-color:black;");
		
		VBox loginBox = new VBox(15);
		
		username.setPrefColumnCount(17);
		username.setMinSize(30,  39);
		username.setPromptText("enter your desired username");
		username.setStyle(ButtonHighlighter.TEXT);
		
		password.setPrefColumnCount(17);
		password.setMinSize(30,  39);
		password.setPromptText("enter your desired password");
		password.setStyle(ButtonHighlighter.TEXT);
		
		HBox buttonsBox = new HBox(10);
		signupButton.setMinSize(60, 30);
		signupButton.setStyle(ButtonHighlighter.REGULAR);
		
		accountButton.setMinHeight(30);
		accountButton.setStyle(ButtonHighlighter.REGULAR);
		
		buttonsBox.getChildren().addAll(signupButton, accountButton);
		buttonsBox.setAlignment(Pos.CENTER);
		
		//setting up enter key to work for logging in
		username.setOnKeyPressed(new EnterKeypressHandler(signupButton));
		password.setOnKeyPressed(new EnterKeypressHandler(signupButton));
		
		loginBox.getChildren().addAll(username, password, buttonsBox);
		
		this.getChildren().addAll(loginBox);
		
		ButtonHighlighter.modifyColour(signupButton, ButtonHighlighter.REGULAR, ButtonHighlighter.HIGHLIGHT);
		ButtonHighlighter.modifyColour(accountButton, ButtonHighlighter.REGULAR, ButtonHighlighter.HIGHLIGHT);
	}
	
	public TextField getUsername() {
		return this.username;
	}
	
	public TextField getPassword() {
		return this.password;
	}
}
