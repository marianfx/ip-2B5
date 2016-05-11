/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;

import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.ImageProcessedRepresentation;
import com.imgprocessor.processor.ImageProcessor;
import com.imgprocessor.processor.ImageState;
import com.imgprocessor.processor.ProcessingException;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;




/**
 *
 * @author tifuivali
 */
public class InternalImageApiImpl implements ImageApi{

    //static deoarece va fi setatat din main controler
    private static ImageProcessor internalImageProcessor; 
    
    public InternalImageApiImpl() {}
    
    @Override
    public void processImage(String imageFilePath) 
    		throws ValidatingException, TruncatingException, ProcessingException {};
    
    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation() 
    		throws ValidatingException, TruncatingException, ProcessingException {
    	
        if(!existsInternalImageProcessorInitialized())
            return null;
        
        if(!isProcessed())
          internalImageProcessor.process();
        
        return internalImageProcessor.getImageProcessedRepresentation();
    }
    
    private boolean existsInternalImageProcessorInitialized()
    {
       return internalImageProcessor==null;
    }
    
    private boolean isProcessed()
    {
        return internalImageProcessor.getExtendedImage().getImageState()==ImageState.Processed;
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        internalImageProcessor.addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
       internalImageProcessor.removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsAppendListener(DetailsAppendListener listener) {
        internalImageProcessor.addDetailsAppendListener(listener);
    }

    @Override
    public void removeDetailsAppendListener(DetailsAppendListener listener) {
        internalImageProcessor.removeDetailsAppendListener(listener);
    }   
    
    @Override
    public ImageProcessor getImageProcessor() {
    	// TODO Auto-generated method stub
    	return null;
    }
}
