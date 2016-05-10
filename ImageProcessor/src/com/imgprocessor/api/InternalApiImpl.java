/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;

import com.imgprocessor.controller.DetailsApprendListener;
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
public class InternalApiImpl implements Api{

    //static deoarece va fi setatat din main controler
    private static ImageProcessor internalImageProcesor; 
    public InternalApiImpl()
    {
    }
    
    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcessingException {
        if(!existsInternalImageProcessorInitialized())
            return null;
        if(!isProcessed())
          internalImageProcesor.processing();
        return internalImageProcesor.getImageProcessedRepresentation();
        
    }
    
    private boolean existsInternalImageProcessorInitialized()
    {
       return internalImageProcesor==null;
    }
    
    private boolean isProcessed()
    {
        return internalImageProcesor.getExtendedImage().getImageState()==ImageState.Processed;
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        internalImageProcesor.addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
       internalImageProcesor.removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsApprendListener(DetailsApprendListener listener) {
        internalImageProcesor.addDetailsApprendListener(listener);
    }

    @Override
    public void removeDetailsApprendListener(DetailsApprendListener listener) {
        internalImageProcesor.removeDetailsApprendListener(listener);
    }
    
    
}
