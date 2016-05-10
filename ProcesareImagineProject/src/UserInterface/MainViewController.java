/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import EventsObject.DetailsApprendAction;
import EventsObject.DetailsApprendListener;
import EventsObject.ProgressChangedAction;
import EventsObject.ProgressChangedListener;
import ExtendedImage.ExtendedImage;
import ImagePreProcesing.ImagePreProcesorImpl;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import ImagePreProcesing.ImagePreprocesor;
import ImagePreProcesing.TruncatingException;
import ImagePreProcesing.ValidatingException;
import ImageProcesing.ImageProcesor;
import ImageProcesing.ImageProcesorImpl;
import ImageProcesing.ProcesingException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    
   
    
    ImagePreprocesor imagePreprocesor=null;
    ImageProcesor imageProcesor=null;
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
       imagePreprocesor=new ImagePreProcesorImpl(extendedImage);
       imageProcesor=new ImageProcesorImpl(imagePreprocesor);
       imageProcesor.addDetailsApprendListener((DetailsApprendAction e) -> {
           textDetails.appendText(e.getDetailsApprended());
        });
       imageProcesor.addProgressChangedListener((ProgressChangedAction a) -> {
          progressBar.setProgress(a.getProgress());
        });
        try {
            imageProcesor.processing();
        } catch (ValidatingException | TruncatingException | ProcesingException ex) {
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
