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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 *
 * @author tifuivali
 */
public class ImagePreProcessorImpl implements ImagePreProcessor {

    private ExtendedImage extendedImage;
    
    Vector<DetailsAppendListener> detailsAppendListeners;
    Vector<ProgressChangedListener> progressChangedListeners;
    
    public ImagePreProcessorImpl(File imageFile) throws FileNotFoundException
    {
        this.extendedImage = new ExtendedImage(imageFile);
        
        this.detailsAppendListeners=new Vector<>();
        this.progressChangedListeners=new Vector<>();
    }
    
    @Override
    public void preProcess() throws ValidatingException, TruncatingException {
    	
        if(!validate())
            throw new ValidatingException();
        
        truncate();
    }
    
    /**
     * Validates image.
     * @return 
     */
    private boolean validate() {
    	
        appendDetail("Validating image...");
        boolean validated= new ImageValidator(getExtendedImage()).validate();
        appendDetail("Image validated!");
        
        setProgress(3);
        return validated;
    }

    /**
     * Truncates image.
     */
   
    private void truncate() throws TruncatingException {
    	
        appendDetail("Truncating image...");
        ImageTruncator truncator=new ImageTruncator(getExtendedImage());
        appendDetail("Image Truncated!");
        
        setProgress(15);
        this.extendedImage = truncator.getTrucatedExtendedImage();
    }
    /**
     * Get the preprocessed image.
     * @return 
     */
    @Override
    public ExtendedImage getPreProcessedExtendedImage(){
   
        return this.getExtendedImage();
        
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
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.remove(listener);
    }

    @Override
    public void addDetailsAppendListener(DetailsAppendListener listener) {
       this.detailsAppendListeners.add(listener);
    }

    @Override
    public void removeDetailsAppendListener(DetailsAppendListener listener) {
       this.detailsAppendListeners.remove(listener);
    }

    /**
     * @return the extendedImage
     */
    public ExtendedImage getExtendedImage() {
        return extendedImage;
    }
    
}
