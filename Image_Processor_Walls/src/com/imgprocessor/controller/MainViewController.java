/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import com.imgprocessor.api.InternalProcessorNotFound;
import com.imgprocessor.api.NotSupportedFileFormatException;
import com.imgprocessor.api.PublicApiService;
import com.imgprocessor.controller.DetailsAppendAction;
import com.imgprocessor.controller.ImageUpdateAction;
import com.imgprocessor.controller.ProgressChangedAction;
import com.imgprocessor.opencvtest.LineProcessor;
import com.imgprocessor.processor.DetectObject;
import com.imgprocessor.processor.ImageProcessorImpl;
import com.imgprocessor.processor.ProcessingException;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;

import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author tifuivali
 */
public class MainViewController implements Initializable {
	
	private String imageFilepath;
	private final PublicApiService publicApiService = new PublicApiService();
    
    private static Stage mainStage;
    @FXML
    ImageView imageView;
    @FXML
    AnchorPane imagePanel;
    @FXML
    SplitPane splitPane;
    @FXML
    TextField textTemplate;
    @FXML
    TextField textMinWidth;
    @FXML
    TextField textMaxWidth;
    @FXML
    TextArea textDetails;
    @FXML
    TextField textImage;
    @FXML
    CheckBox onlyLinesCK;
    @FXML
    ProgressBar progressBar;
    
    ImageProcessorImpl currentProcessor;

    /**
     * @param aMainScene the scene to set
     */
    public static void setMainScene(Stage aMainScene) {
    	
        mainStage = aMainScene;
        
    }
    
    
    /**
     * Load button controller.
     * @param evt
     */
    @FXML
    private void buttonLoadOnClick(ActionEvent evt) {
    	
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().toString()));                 
        fileChooser.getExtensionFilters().addAll(
        		new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        		new FileChooser.ExtensionFilter("PNG", "*.png"));
        
        try {
        	
            File fileImg = fileChooser.showOpenDialog(mainStage);
            
            /*if user chooses cancel*/
            if(fileImg == null)
            	return;
            
            imageFilepath = fileImg.getAbsolutePath();
            
            /*update the view: text field, details text area, image view*/
            textImage.setText(imageFilepath);
            textDetails.appendText("Image loaded!\n");
            imageView.setImage(new Image(new FileInputStream(fileImg)));
            
            
        } catch (FileNotFoundException ex) {
        	
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erorr");
            alert.setContentText("Erorr loading image: " + ex.getMessage());
            alert.show();
        }
    }
    
    /**
     * Process button controller.
     * @param evt
     * @throws ProcessingException 
     * @throws TruncatingException 
     * @throws ValidatingException 
     */
    @FXML
    private void buttonProcessOnClick(ActionEvent evt) throws ValidatingException, TruncatingException, ProcessingException {
        
       try {
    	   publicApiService.processRequest(imageFilepath);
    	   
    	   if(imageFilepath != null) {
    		   
    		   
    		   ImageProcessorImpl imageProcessorImpl = new ImageProcessorImpl(new File(imageFilepath));
    		   // update info
    		   DetectObject.TEMPLATE_INPUT_PATH = "_input//" + textTemplate.getText().trim();
    		   LineProcessor.WALL_MIN_WIDTH = Integer.parseInt(textMinWidth.getText());
    		   LineProcessor.WALL_MAX_WIDTH = Integer.parseInt(textMaxWidth.getText());
    		   ImageProcessorImpl.DETECT_ONLY_WALLS = onlyLinesCK.isSelected();
    		   
    		   imageProcessorImpl.addDetailsAppendListener( (DetailsAppendAction e) -> {
    			   
                   Platform.runLater(new Runnable() {
                	   
                	@Override
   					public void run() {
   						
   					 textDetails.appendText(e.getDetailsAppended());
//                     System.out.println("Updated text ");
   					}
   				});
    			   
    			   
    		   });

    		   imageProcessorImpl.addProgressChangedListener((ProgressChangedAction a) -> {
    			   
    			   Platform.runLater(new Runnable() {
					public void run() {
						
						progressBar.setProgress(a.getProgress() / 100.0);
//                        System.out.println("pogress "+a.getProgress());
					}
				});
    			   
    		   });
                   
              imageProcessorImpl.addImageChangeListers((ImageUpdateAction action) -> {
                    	   
                    	   Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								
								imageView.setImage(action.getFXImage());
//	                            System.out.println("image updated");
								
							}
						});
                          
                   });
    		   
               
//    		   this.currentProcessor = new ImageProcessorImpl();
               imageProcessorImpl.process();
    	   }
           
          // publicApiService.getResult();
           
           /*so until a new image is loaded the old representation will be returned*/
           imageFilepath = null;
           textDetails.appendText("Image unloaded.\n");
           
    	  
       } catch (FileNotFoundException | NotSupportedFileFormatException | InternalProcessorNotFound e) {
    	   
    	   Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Error");
           alert.setContentText("Image processing error: " + e.getMessage());
           alert.show();
       }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        textDetails.setEditable(false);
        textTemplate.setText("templates2");
        textMinWidth.setText("15");
        textMaxWidth.setText("25");
        onlyLinesCK.setSelected(false);
        //fit image
        imageView.fitWidthProperty().bind(imagePanel.widthProperty());
    }
    
    public String getImageFilepath() {
    	
    	return imageFilepath;
    }
}
