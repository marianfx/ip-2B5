/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;

import com.imgprocessor.controller.DetailsApprendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.processor.ExtendedImage;
import com.imgprocessor.model.ImageProcessedRepresentation;
import com.imgprocessor.processor.ImagePreProcessorImpl;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;
import com.imgprocessor.processor.ImageProcessor;
import com.imgprocessor.processor.ImageProcessorImpl;
import com.imgprocessor.processor.ProcessingException;
import com.imgprocessor.processor.ImagePreprocessor;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author tifuivali
 */
public class ImageProcessorApiImpl implements ImageProcessorApi{

    private ExtendedImage extendedImage=null;
    private ImagePreprocessor imagePreprocesor=null;
    private ImageProcessor imageProcesor=null;
    
    public ImageProcessorApiImpl(File imageFile) throws FileNotFoundException
    {
        extendedImage=new ExtendedImage(imageFile);
        imagePreprocesor=new ImagePreProcessorImpl(extendedImage);
        imageProcesor=new ImageProcessorImpl(imagePreprocesor);
    }

    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation(File imageFile) throws ValidatingException, TruncatingException, ProcessingException, FileNotFoundException {
         extendedImage=new ExtendedImage(imageFile);
        imagePreprocesor=new ImagePreProcessorImpl(extendedImage);
        imageProcesor=new ImageProcessorImpl(imagePreprocesor);
        return  imageProcesor.getImageProcessedRepresentation();
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
