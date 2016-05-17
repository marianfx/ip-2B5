/**
 * 
 */
package com.imgprocessor.controller;

import com.imgprocessor.view.MainWindow;

import javafx.stage.Stage;

/**
 * @author 
 *
 */
public class MainController {

	private MainWindow mainWindow;
	
	public MainController() {}

	public void initMainWindow(Stage stage) {
		
		mainWindow = new MainWindow(stage);
		mainWindow.setMainController(this);
	}
	
}
