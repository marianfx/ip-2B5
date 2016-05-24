/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;

import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.processor.ImageProcessor;
import com.imgprocessor.processor.ProcessingException;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;

/**
 *
 * @author tifuivali
 */
public interface ImageApi {
    
	public void processImage(String imageFilePath)
			throws ValidatingException, TruncatingException, ProcessingException;
    
    public ImageProcessor getImageProcessor(); 
    
    void addProgressChangedListener(ProgressChangedListener listener);
    void removeProgressChangedListener(ProgressChangedListener listener);
    void addDetailsAppendListener(DetailsAppendListener listener);
    void removeDetailsAppendListener(DetailsAppendListener listener);
}
