/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;


import com.imgprocessor.controller.DetailsApprendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.ImageProcessedRepresentation;
import com.imgprocessor.processor.ExtendedImage;
import java.io.File;
import java.io.FileNotFoundException;
import com.imgprocessor.processor.*;

/**
 *
 * @author tifuivali
 */
public class ExternalApiImpl implements Api{

private ExtendedImage extendedImage=null;
    private ImagePreprocessor imagePreprocesor=null;
    private ImageProcessor imageProcesor=null;
    
    public ExternalApiImpl(File imageFile) throws FileNotFoundException
    {
        extendedImage=new ExtendedImage(imageFile);
        imagePreprocesor=new ImagePreProcessorImpl(extendedImage);
        imageProcesor=new ImageProcessorImpl(imagePreprocesor);
    }

    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcessingException{
        imageProcesor.processing();
        return imageProcesor.getImageProcessedRepresentation();
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        imageProcesor.addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        imagePreprocesor.removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsApprendListener(DetailsApprendListener listener) {
        imageProcesor.addDetailsApprendListener(listener);
    }

    @Override
    public void removeDetailsApprendListener(DetailsApprendListener listener) {
       imageProcesor.removeDetailsApprendListener(listener);
    }
}
