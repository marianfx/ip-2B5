/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.processor.ExtendedImage;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;

/**
 *
 * @author tifuivali
 * Am zis sa renuntam la convertor ..cum credeti si voi..
 */
public interface ImagePreProcessor {
    
   public void preProcess() throws ValidatingException,TruncatingException;
   public ExtendedImage getPreProcessedExtendedImage();
   
   void addProgressChangedListener(ProgressChangedListener listener);
   void removeProgressChangedListener(ProgressChangedListener listener);
   void addDetailsAppendListener(DetailsAppendListener listener);
   void removeDetailsAppendListener(DetailsAppendListener listener);
}
