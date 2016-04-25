package com.imgprocessor.processor;

import javax.swing.ImageIcon;

import com.imgprocessor.model.Representation;

/**
 * 
 */
public class ImageProcessor {

    /**
     * Default constructor
     */
    public ImageProcessor() {
    }

    /**
     * 
     */
    protected ExtendedImage currentImage;

    /**
     * 
     */
    public ImagePreProcessor preprocessor;

    /**
     * 
     */
    protected Representation internalRepresentation;



    /**
     * @param img
     */
    public void loadImage(ImageIcon img) {
        // TODO implement here
    }

    /**
     * 
     */
    public void process() {
        // TODO implement here
    }

    
    /**
     * @return
     */
    public ExtendedImage getFinalImage() {
        // TODO implement here
        return null;
    }

    
    /**
     * @return
     */
    public Representation getProcessedRepresentation() {
        // TODO implement here
        return null;
    }

}