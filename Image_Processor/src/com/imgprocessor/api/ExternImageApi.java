package com.imgprocessor.api;

import com.imgprocessor.model.Representation;
import com.imgprocessor.processor.ImageProcessor;

/**
 * 
 */
public class ExternImageApi implements ImageAPI {

    /**
     * Default constructor
     */
    public ExternImageApi() {
    	
    }

    /**
     * 
     */
    public ImageProcessor processor;



    /**
     * 
     */
    public void processImage() {
        // TODO implement here
    }
    /**
     * @return
     */
    public Representation getResult() {
        // TODO implement here
        return null;
    }

	@Override
	public void processImage(String filePath) {
		// TODO Auto-generated method stub
		
	}

}