/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imgprocessor.controller.ImageUpdateAction;
import com.imgprocessor.controller.ImageUpdateListener;
import com.imgprocessor.processor.ImageState;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import org.opencv.core.Mat;


/**
 *
 * @author tifuivali
 * Obeictul imagine extins ce va fi preprocesat si apoi procesat.
 * contine imagine si matricea imagine
 */
public class ExtendedImage {
    
    private Image image = null;
    private Mat matRepresentation = null;
    private final int width;
    private final int height;
    private ImageState imageState = null;
    List<ImageUpdateListener> imageUpdateListeners=null;
    
    /**
     * Create a new Extended Image using a File Image.
     * @param fileImage 
     * @throws java.io.FileNotFoundException 
     */
    public ExtendedImage(File fileImage) throws FileNotFoundException {
    	
      // this.matRepresentation=Highgui.imread(fileImage.getAbsolutePath());
       this.image = new Image(new FileInputStream(fileImage));
       this.width = (int)image.getWidth();
       this.height = (int)image.getHeight();
       this.imageState=ImageState.Loaded;
       this.imageUpdateListeners = new ArrayList<>();
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return the matRepresentation
     */
    public Mat getMatRepresentation() {
        return matRepresentation;
    }

    /**
     * @param matRepresentation the matRepresentation to set
     */
    public void setMatRepresentation(Mat matRepresentation) {
        this.matRepresentation = matRepresentation;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the imageState
     */
    public ImageState getImageState() {
        return imageState;
    }

    /**
     * @param imageState the imageState to set
     */
    public void setImageState(ImageState imageState) {
        this.imageState = imageState;
    }
    
    public void addImageUpdateListener(ImageUpdateListener listener)
    {
        this.imageUpdateListeners.add(listener);
    }
    
    public void removeImageUpdateListener(ImageUpdateListener listener)
    {
        this.imageUpdateListeners.remove(listener);
    }
    
    public void updateImage(BufferedImage bi)
    {
        imageUpdateListeners.stream().forEach((listener) -> {
            listener.onUpdatePerformed(new ImageUpdateAction(bi));
        });
    }
    
 
    
    
}
