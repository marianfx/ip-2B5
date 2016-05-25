/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.controller;

import com.imgprocessor.controller.MainViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author tifuivali
 */
public class ImageProcessorApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/com/imgprocessor/view/MainView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Image processor");
        stage.show();
        MainViewController.setMainScene(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
