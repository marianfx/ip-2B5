/**
 * 
 */
package com.imgprocessor.controller;

import java.io.File;
import java.nio.file.Paths;

import com.imgprocessor.processor.ImageValidator;
import com.imgprocessor.view.MainWindow;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
/**
 * @author 
 *
 */
public class LoadButtonController implements EventHandler<MouseEvent> {
	
	private MainWindow mainWindow;
	
	public LoadButtonController(MainWindow mainWindow) {
		
		this.mainWindow = mainWindow;
	}

	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		
		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		File file = fileChooser.showOpenDialog(mainWindow.getStage());
		

		ImageValidator imageValidator = new ImageValidator();
	}

	private void configureFileChooser(FileChooser fileChooser) {
		
		fileChooser.setTitle("Load Image");
		fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
												 new FileChooser.ExtensionFilter("PNG", "*.png"));
	}
}
