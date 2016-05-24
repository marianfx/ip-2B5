/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

/**
 *
 * @author tifuivali
 * Clasa utilizata pentru trinchierea imaginii , va fi trunchiata imaginea extinsa ce contine matricea 
 * si imaginea prpriu-zisa.
 */
public class ImageTruncator {
    
    private ExtendedImage extendedImage=null;
    
    /**
     * Create an ImageTruncator.
     * @param extendedImage 
     */
    public ImageTruncator(ExtendedImage extendedImage)
    {
        this.extendedImage=extendedImage;
    }
    
    
    public ExtendedImage getTrucatedExtendedImage() throws TruncatingException
    {
        truncate();
        return this.extendedImage;
    }
    /**
     * Aici se va realiza trunchierea , se pot crea si alte metode private care sa vina in ajutor.
     * trunchierea se realizeaza pe obiectul extendedImage din aceasta clasa.
     * 
     * daca apare o problema se arunca exceptie 
     */
    private void truncate() throws TruncatingException
    {
        
        //after truncate
        //trebuie setata matricea imaginii si imaginea propriu zisa la dimensiunile corecte
        //after
        extendedImage.setImageState(ImageState.Truncated);
    }
}
