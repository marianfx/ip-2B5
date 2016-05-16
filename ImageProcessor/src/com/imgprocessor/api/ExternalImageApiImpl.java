/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;


import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.ImageProcessedRepresentation;

import java.io.File;
import java.io.FileNotFoundException;
import com.imgprocessor.processor.*;


/**
 *
 * @author tifuivali
 */
public class ExternalImageApiImpl implements ImageApi {

    private final ImageProcessor imageProcessor;
    
    public ExternalImageApiImpl(File imageFile) throws FileNotFoundException {
    	
        imageProcessor = new ImageProcessorImpl(imageFile);
    }
    
    
    /*never used?*/
    @Override
    public void processImage(String imageFilePath)
    		throws ValidatingException, TruncatingException, ProcessingException {
    	
    	imageProcessor.process();
    }
    
    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation() 
    		throws ValidatingException, TruncatingException, ProcessingException {
    	
        return imageProcessor.getImageProcessedRepresentation();
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        imageProcessor.addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        imageProcessor.removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsAppendListener(DetailsAppendListener listener) {
        imageProcessor.addDetailsAppendListener(listener);
    }

    @Override
    public void removeDetailsAppendListener(DetailsAppendListener listener) {
       imageProcessor.removeDetailsAppendListener(listener);
    }
    
    @Override
    public ImageProcessor getImageProcessor() {
    	
    	return imageProcessor;
    }
}
