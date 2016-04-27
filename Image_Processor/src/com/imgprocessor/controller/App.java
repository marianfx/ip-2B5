package com.imgprocessor.controller;

import com.imgprocessor.processor.ImageProcessor;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 */
public class App extends Application {
	
	 /**
     * 
     */
    private ImageProcessor internalProcessor;

    /**
     * Default constructor
     */
    public App() {
    	
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        
    	Application.launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		MainController mainController = new MainController();
		
		mainController.initMainWindow(primaryStage);
		
		primaryStage.show();
	}

	 /**
     * @return
     */
    public ImageProcessor getImgProcessor() {
        // TODO implement here
        return internalProcessor;
    }
}