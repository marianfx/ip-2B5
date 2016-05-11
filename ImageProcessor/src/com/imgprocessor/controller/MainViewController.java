/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.controller;

import com.imgprocessor.api.InternalProcessorNotFound;
import com.imgprocessor.api.NotSupportedFileFormatException;
import com.imgprocessor.api.PublicApiService;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;
import com.imgprocessor.processor.ProcessingException;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author tifuivali
 */
public class MainViewController implements Initializable {
	
	private String imageFilepath;
	private PublicApiService publicApiService = new PublicApiService();
    
    private static Stage mainStage;
    @FXML
    ImageView imageView;
    @FXML
    TextArea textDetails;
    @FXML
    TextField textImage;
    @FXML
    ProgressBar progressBar;

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
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
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
            textImage.setText(fileImg.getName());
            textDetails.appendText("Image loaded!\r\n");
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
     */
    @FXML
    private void buttonProcessOnClick(ActionEvent evt) {
        
       try {
    	   publicApiService.processRequest(imageFilepath);
    	   
    	   if(imageFilepath != null) {
    		   
    		   publicApiService.getImageApi().addDetailsAppendListener((DetailsAppendAction e) -> {
    			   textDetails.appendText(e.getDetailsAppended());
    		   });

    		   publicApiService.getImageApi().addProgressChangedListener((ProgressChangedAction a) -> {
    			   progressBar.setProgress(a.getProgress());
    		   });
    	   }
           
           publicApiService.getResult();
           
           /*so until a new image is loaded the old representation will be returned*/
           imageFilepath = null;
           
    	  
       } catch (FileNotFoundException | NotSupportedFileFormatException | InternalProcessorNotFound |
    		   ValidatingException | TruncatingException | ProcessingException e) {
    	   
    	   Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setHeaderText("Error");
           alert.setContentText("Image processing error: " + e.getMessage());
           alert.show();
       }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    public String getImageFilepath() {
    	
    	return imageFilepath;
    }
}
