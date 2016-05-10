/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imgprocessor.controller.DetailsApprendAction;
import com.imgprocessor.controller.DetailsApprendListener;
import com.imgprocessor.controller.ProgressChangedAction;
import com.imgprocessor.controller.ProgressChangedListener;
import java.util.Vector;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author tifuivali
 */
public class ImagePreProcessorImpl implements ImagePreprocessor{

    private ExtendedImage extendedImage=null;
    
    //utilizat pentru scrierea de detalii ...
    //scrierile de detalii se vor face prin procedura apprendDetail();
    
    
     //listeneri aprend details
    Vector<DetailsApprendListener> detailsAprendListeners;
    //listeneri progress changed
    Vector<ProgressChangedListener> progressChangedListeners;
    private ProgressBar progressBar=null;
    
    public ImagePreProcessorImpl(ExtendedImage extendedImage)
    {
        this.extendedImage=extendedImage;
        this.detailsAprendListeners=new Vector<>();
        this.progressChangedListeners=new Vector<>();
    }
    
    /**
     * Validating image.
     * @return 
     */
    private boolean validate() {
        apprendDetail("Validating image...");
        boolean validated= new ImageValidator(extendedImage).validate();
        apprendDetail("Image validated!");
        setProgress(3);
        return validated;
    }

    /**
     * Truncate image.
     */
   
    private void truncate() throws TruncatingException {
        apprendDetail("Truncating image...");
        ImageTruncator truncator=new ImageTruncator(extendedImage);
        apprendDetail("Image Truncated");
        setProgress(15);
        this.extendedImage=truncator.getTrucatedExtendedImage();
    }
    /**
     * Get the image prerocesed.
     * @return 
     */
    @Override
    public ExtendedImage getPreProcesedExtendedImage(){
   
        return this.extendedImage;
        
    }

    
    private void apprendDetail(String detail)
    {
       for(DetailsApprendListener listener:detailsAprendListeners)
       {
           listener.onApprendPerformed(new DetailsApprendAction(detail+"\r\n"));
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
    public void preProcessing() throws ValidatingException, TruncatingException {
        if(!validate())
            throw new ValidatingException();
        truncate();
    }

    /**
     * @return the progressBar
     */
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * @param progressBar the progressBar to set
     */
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
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
    public void addDetailsApprendListener(DetailsApprendListener listener) {
       this.detailsAprendListeners.add(listener);
    }

    @Override
    public void removeDetailsApprendListener(DetailsApprendListener listener) {
       this.detailsAprendListeners.remove(listener);
    }
    
}
