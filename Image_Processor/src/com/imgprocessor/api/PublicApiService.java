package com.imgprocessor.api;

import com.imgprocessor.model.Representation;
import com.imgprocessor.view.Application;

/**
 * 
 */
public class PublicApiService {

    /**
     * Default constructor
     */
    public PublicApiService(Application appRefference) {
    	
    }

    
    /**
     * 
     */
    protected RequestValidator validator;

    /**
     * 
     */
    protected ImageAPI api;




    /**
     * @param filePath
     */
    public void processRequest(String filePath) {
        // TODO implement here
    }

    /**
     * 
     */
    protected void chooseRightImageApi() {
        // TODO implement here
    }

    /**
     * 
     */
    protected void validateRequest() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Representation getResult() {
        // TODO implement here
        return null;
    }


}