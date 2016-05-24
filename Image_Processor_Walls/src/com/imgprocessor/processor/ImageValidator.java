/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;


/**
 *
 * @author tifuivali
 */
public class ImageValidator {
    
    private ExtendedImage extendedImage=null;
    
    public ImageValidator(ExtendedImage extendedImage)
    {
        this.extendedImage=extendedImage;
    }
    
    /**
     * Validating image.
     * @return 
     */
    
    //ca validare m-am gandit ca nu am putea procesa imagini care au 
    //o latime respectiv lungime mai mica de 200 px
    public boolean validate()
    {
        if(extendedImage.getWidth()>200&&extendedImage.getHeight()>200)
        {
            extendedImage.setImageState(ImageState.Validated);
            return true;
        }
        return false;
    }
}
