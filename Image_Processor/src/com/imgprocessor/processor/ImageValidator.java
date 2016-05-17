package com.imgprocessor.processor;


/**
 * 
 */
public class ImageValidator {
	
	private final int MIN_WIDTH = 500;
	private final int MIN_HEIGHT = 500;

    protected ExtendedImage internalImage;

    /**
     * Default constructor
     */
    public ImageValidator() {
        // TODO implement here
    }
    

    /**
     * @param image
     */
    public ImageValidator(ExtendedImage internalImage) {
        
    	this.internalImage = internalImage;
    }

    /**
     * 
     */
    public String allowedExtensions;


    /**
     * @return
     */
    public boolean validate() {
        
    	return internalImage.getWidth() >= this.MIN_WIDTH &&
    		   internalImage.getHeight() >= this.MIN_HEIGHT;
    }

}