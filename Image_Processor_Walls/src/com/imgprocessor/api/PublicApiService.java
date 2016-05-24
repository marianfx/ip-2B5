/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;

import com.imgprocessor.api.ExternalImageApiImpl;
import com.imgprocessor.api.ImageApi;
import com.imgprocessor.api.InternalProcessorNotFound;
import com.imgprocessor.api.NotSupportedFileFormatException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author tifuivali
 */
public class PublicApiService {
	
    private ImageApi imageApi;
    
    /**
     * Process the request.
     * Instantiates a <code>RequestValidator</code> which validates the request.
     * If the request is valid 'choose' 
     * @param imageFilepath
     * @throws com.imgproctempl.api.NotSupportedFileFormatException
     * @throws java.io.FileNotFoundException
     * @throws InternalProcessorNotFound 
     */
    public void processRequest(String imageFilepath) 
    		throws NotSupportedFileFormatException, FileNotFoundException, InternalProcessorNotFound {
    	
    	if(imageFilepath != null) {
    		
//    		RequestValidator requestValidator = new RequestValidator(imageFilepath);
//    		requestValidator.validate();
    	}
        
        chooseImageApi(imageFilepath);     
    }
    
    
    /**
     * Chooses right API based on the image file path.
     * Instantiates the image API reference. 
     * @throws FileNotFoundException 
     * @throws InternalProcessorNotFound 
     */
    private void chooseImageApi(String imageFilepath) throws FileNotFoundException, InternalProcessorNotFound {
        
    	if(imageFilepath != null)
    		imageApi = new ExternalImageApiImpl(new File(imageFilepath));
    	
    	else if(imageApi == null)
    		throw new InternalProcessorNotFound();
    }
    
    public ImageApi getImageApi() {
    	
    	return imageApi;
    }
}
