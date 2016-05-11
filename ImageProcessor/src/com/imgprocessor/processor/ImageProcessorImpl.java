/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imgprocessor.controller.DetailsAppendAction;
import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ProgressChangedAction;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.Coordinates;
import com.imgprocessor.model.ImageProcessedRepresentation;
import com.imgprocessor.model.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;
import javafx.scene.image.Image;
import org.opencv.core.Mat;

/**
 *
 * @author tifuivali
 */
public class ImageProcessorImpl implements ImageProcessor {
    
    private ImagePreProcessor imagePreProcessor;
    private ExtendedImage extendedImage;
    
    private ImageProcessedRepresentation imageProcessedRepresentation;
    
    private Vector<DetailsAppendListener> detailsAppendListeners;
    private Vector<ProgressChangedListener> progressChangedListeners;
    
    
    public ImageProcessorImpl(File imageFile) throws FileNotFoundException {
    	
        this.imagePreProcessor = new ImagePreProcessorImpl(imageFile);
        
        this.detailsAppendListeners = new Vector<>();
        this.progressChangedListeners = new Vector<>();
    }
    
    /**
     * @return the imagePreprocesor
     */
    public ImagePreProcessor getImagePreprocessor() {
        return imagePreProcessor;
    }

    /**
     * @param imagePreprocesor the imagePreprocesor to set
     */
    public void setImagePreprocessor(ImagePreProcessor imagePreprocessor) {
        this.imagePreProcessor = imagePreprocessor;
    }

    @Override
    public void process() 
    		throws 	ValidatingException, TruncatingException, ProcessingException {
    	
    	if(this.imageProcessedRepresentation != null)
    	{
    		appendDetail("Processing complete!");
    		return;
    	}

        this.imageProcessedRepresentation = new ImageProcessedRepresentation();

    	//verificam daca am primit un ImageProcesor cu imagine deja preprocesata
    	if(imagePreProcessor.getPreProcessedExtendedImage().getImageState()!=ImageState.Preprocessed)
    		imagePreProcessor.preProcess();


    	imagePreProcessor.getPreProcessedExtendedImage().setImageState(ImageState.Processing);

    	//procesare si umplere Reprezentare

    	//daca avem nevoie de matricea imaginii:
    	Mat matImg=imagePreProcessor.getPreProcessedExtendedImage().getMatRepresentation();

    	//daca avem nevoie de imagine..
    	Image img=imagePreProcessor.getPreProcessedExtendedImage().getImage();


    	//cand vrem sa adaugam o line , la fel pt usi...
    	Coordinates start=new Coordinates(0, 0);
    	Coordinates end=new Coordinates(5, 5);
    	Wall wall=new Wall(start, end);
    	this.imageProcessedRepresentation.addWall(wall);


    	imagePreProcessor.getPreProcessedExtendedImage().setImageState(ImageState.Processed);
    	//petru setarea procesului 
    	setProgress(100);

    	//pentru aprend detail
    	appendDetail("Processing complete!");     
    }

    /**
     * Procesing image and get it's representation.
     * @return the imageProcessedRepresentation
     * @throws ImagePreProcesingPackage.ValidatingException
     * @throws ImagePreProcesingPackage.TruncatingException
     * @throws ImageProcesingPackage.ProcesingException
     */
    public ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcessingException {
        process();
        return imageProcessedRepresentation;
    }


    
     private void appendDetail(String detail)
    {
       for(DetailsAppendListener listener:detailsAppendListeners)
       {
           listener.onAppendPerformed(new DetailsAppendAction(detail+"\r\n"));
       }
    }
    
    private void setProgress(double value)
    {
       for(ProgressChangedListener listener:progressChangedListeners)
       {
           listener.onValueChanged(new ProgressChangedAction(value));
       }
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.add(listener);
        this.getImagePreprocessor().addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.remove(listener);
        this.getImagePreprocessor().removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsAppendListener(DetailsAppendListener listener) {
       this.detailsAppendListeners.add(listener);
       this.getImagePreprocessor().addDetailsAppendListener(listener);
    }

    @Override
    public void removeDetailsAppendListener(DetailsAppendListener listener) {
       this.detailsAppendListeners.remove(listener);
       this.getImagePreprocessor().removeDetailsAppendListener(listener);
    }

    @Override
    public ExtendedImage getExtendedImage() {
    	
        return this.extendedImage;
    }
    
}
