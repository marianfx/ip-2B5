package com.imgprocessor.view;
import com.imgprocessor.controller.LoadButtonController;
import com.imgprocessor.controller.MainController;
import com.imgprocessor.processor.ImageProcessor;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 * 
 */
public class MainWindow {
	
	private final int MIN_WIDTH  = 800; 
	private final int MIN_HEIGHT = 600;
	
	private final String TITLE = "ImageProcessor";
	private final String MAIN_ICON = "";
	
	private MainController mainController;
	
	private Stage mainWindow;
		private SplitPane mainSplitPane = new SplitPane();
		private ControlsArea controlsArea = new ControlsArea();
		private ViewArea viewArea = new ViewArea();

    public ImageProcessor imgProcessor;

   /* public JSplitPane mainSplitPane;
 
    public JFileChooser openFileDialog;
    public JProgressBar progressBar;
    public JLabel imageDisplay;
*/
	/**
	 * 
	 * @param stage
	 */
    public MainWindow(Stage stage) {
    	
    	this.mainWindow = stage;
    	
    	this.decorateTitleBar();
    	this.setMinSize();
    	
    	mainSplitPane.getItems().addAll(controlsArea, viewArea);
    	
    	Scene mainScene = new Scene(mainSplitPane);
    	
    	this.addEvents();
		
		mainWindow.setScene(mainScene);
    	
	}
    
    private void decorateTitleBar() {
		
		mainWindow.setTitle(TITLE);
		/*mainWindow.getIcons().add(new Image(getClass().
				getResource(MAIN_ICON).toExternalForm()));*/
	}
	
	private void setMinSize() {

		mainWindow.setMinWidth(MIN_WIDTH);
		mainWindow.setMinHeight(MIN_HEIGHT);
	}

    
	private void addEvents() {
		
		controlsArea.getLoadButton().setOnMouseClicked(new LoadButtonController(this));
	}
	
    /**
     * @return
     */
    public ImageProcessor getImgProcessor() {
        // TODO implement here
        return null;
    }

    
    /**
     * @param proc
     */
    public void setImgProcessor(ImageProcessor proc) {
        // TODO implement here
    }

    /**
     * 
     * @param mainController
     */
    public void setMainController(MainController mainController) {
    	
    	this.mainController = mainController;
    }
    
    public MainController getMainController() {
    	
    	return this.mainController;
    }
    
    public Stage getStage() {
    	
    	return this.mainWindow;
    }
}