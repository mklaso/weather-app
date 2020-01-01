package controller;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneChangeController implements EventHandler<ActionEvent> {
	
	Stage window;
	public ArrayList<Scene> sceneReferences = new ArrayList<Scene>();
	
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
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
