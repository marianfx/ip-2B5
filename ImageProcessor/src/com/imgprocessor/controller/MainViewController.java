/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.controller;

import com.imgprocessor.processor.ExtendedImage;
import com.imgprocessor.processor.ImagePreProcessorImpl;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import com.imgprocessor.processor.ImagePreprocessor;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;
import com.imgprocessor.processor.ImageProcessor;
import com.imgprocessor.processor.ImageProcessorImpl;
import com.imgprocessor.processor.ProcessingException;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author tifuivali
 */
public class MainViewController implements Initializable {

    /**
     * @param aMainScene the mainScene to set
     */
    public static void setMainScene(Stage aMainScene) {
        mainStage = aMainScene;
    }
    
    /**
     * Controller button Browse.
     * @param evt 
     */
    
   
    
    ImagePreprocessor imagePreprocesor=null;
    ImageProcessor imageProcesor=null;
    ExtendedImage extendedImage=null;
    
    private static Stage mainStage=null;
    @FXML
    ImageView imageView;
    @FXML
    TextArea textDetails;
    @FXML
    TextField textImage;
    @FXML
    ProgressBar progressBar;
    
    @FXML
    private void buttonLoadOnClick(ActionEvent evt) 
    {
         FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        try {
            File fileImg=fileChooser.showOpenDialog(mainStage);
            extendedImage=new ExtendedImage(fileImg);
            textImage.setText(fileImg.getName());
            textDetails.appendText("Image loaded!\r\n");
            imageView.setImage(extendedImage.getImage());
            
        } catch (FileNotFoundException ex) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erorr");
            alert.setContentText("Erorr loading image: "+ex.getMessage());
            alert.show();
        }
    }
    /**
     * Controller button Load.
     * @param evt 
     */
    @FXML
    private void buttonProcessOnClick(ActionEvent evt)
    {
        if(extendedImage==null)
            return;
       imagePreprocesor=new ImagePreProcessorImpl(extendedImage);
       imageProcesor=new ImageProcessorImpl(imagePreprocesor);
       imageProcesor.addDetailsApprendListener((DetailsApprendAction e) -> {
           textDetails.appendText(e.getDetailsApprended());
        });
       imageProcesor.addProgressChangedListener((ProgressChangedAction a) -> {
          progressBar.setProgress(a.getProgress());
        });
        try {
            imageProcesor.processing();
        } catch (ValidatingException | TruncatingException | ProcessingException ex) {
             Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erorr");
            alert.setContentText("Erorr process image: "+ex.getMessage());
            alert.show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    
    
}
