/**
 * 
 */
package com.imgprocessor.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author
 *
 */
public class ControlsArea extends VBox {
	
	private Button loadButton;
	private Button processButton;
	
	public ControlsArea() {
	
		this.loadButton = new Button("Load Image");
		this.processButton = new Button("Process Image");
		
		this.setAlignment(Pos.CENTER);
		this.setPrefWidth(140);
		this.setMinWidth(140);
		this.setMaxWidth(200);
		this.setSpacing(20);
		this.setPadding(new Insets(10,10,0,10));
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
		
		this.getChildren().addAll(loadButton, processButton);	
		
	}
	
	/**
	 * @return the loadButton
	 */
	public Button getLoadButton() {
		return loadButton;
	}
	/**
	 * @return the processButton
	 */
	public Button getProcessButton() {
		return processButton;
	}

}
