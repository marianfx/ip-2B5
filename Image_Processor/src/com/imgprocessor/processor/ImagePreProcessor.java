package com.imgprocessor.processor;

/**
 * 
 */
public class ImagePreProcessor {

    /**
     * Default constructor
     */
    public ImagePreProcessor() {
    }

    /**
     * 
     */
    protected ExtendedImage internalImage;

    /**
     * 
     */
    protected ImageValidator validator;

    /**
     * 
     */
    protected FormatConverter converter;

    /**
     * 
     */
    protected ImageTruncator truncator;






    /**
     * @param img
     */
    public void loadImage() {
        // TODO implement here
    }

    /**
     * 
     */
    public void preprocess() {
        // TODO implement here
    }

    /**
     * 
     */
    protected void validate() {
        // TODO implement here
    }

    /**
     * 
     */
    protected void convertToInternalFormat() {
        // TODO implement here
    }

    /**
     * 
     */
    protected void truncate() {
        // TODO implement here
    }

    /**
     * @return
     */
    public ExtendedImage getFinalImage() {
        // TODO implement here
        return null;
    }

}