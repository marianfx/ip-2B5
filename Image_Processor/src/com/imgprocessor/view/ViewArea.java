/**
 * 
 */
package com.imgprocessor.view;

import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author 
 *
 */
public class ViewArea extends VBox {
	
	private ImageView imageView;
	private ProgressBar processProgressBar;
	private TextArea processDetails;
	
	public ViewArea() {
		
		imageView = new ImageView();
		
		processDetails = new TextArea();
		processDetails.setEditable(false);
		
		processProgressBar = new ProgressBar(0.0);
		
		
		this.setPadding(new Insets(10,10,10,10));
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
		
		this.getChildren().addAll(imageView, processProgressBar, processDetails);
		
	}
	
	/**
	 * @return the imageView
	 */
	public ImageView getImageView() {
		return imageView;
	}

	/**
	 * @return the processProgressBar
	 */
	public ProgressBar getProcessProgressBar() {
		return processProgressBar;
	}

	/**
	 * @return the processDetails
	 */
	public TextArea getProcessDetails() {
		return processDetails;
	}

}
