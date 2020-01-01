package view;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ButtonHighlighter {

	Stage stage;
	
	public static final String HIGHLIGHT = "-fx-background-color: rgb(145, 255, 255);"
			+ " -fx-font-size:16;";
			
	public static final String REGULAR = "-fx-background-color: rgb(193, 255, 255);"
			+ " -fx-font-size:16;";
	
	public static final String TEXT = "-fx-font-size:16;"
			+ " -fx-prompt-text-fill: derive(-fx-control-inner-background,-30%); }";
	
	public static void modifyColour(Button button, String oldStyle, String newStyle) {
		button.setOnMouseEntered(
			event -> {
				button.setStyle(newStyle);
			}
		);
		
		button.setOnMouseExited(
			event -> {
				button.setStyle(oldStyle);

			}
		);
	}
}

