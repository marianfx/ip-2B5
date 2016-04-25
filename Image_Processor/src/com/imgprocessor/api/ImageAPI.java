package com.imgprocessor.api;

import com.imgprocessor.model.Representation;

/**
 * 
 */
public interface ImageAPI {


    /**
     * 
     */
    public void processImage();

    /**
     * @param filePath
     */
    public void processImage(String filePath);

    /**
     * @return
     */
    public Representation getResult();

}