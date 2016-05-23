/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.ImageProcessedRepresentation;

/**
 *
 * @author tifuivali
 */
public interface ImageProcessor {
    
   void process() throws ValidatingException, TruncatingException, ProcessingException;
   
   ImageProcessedRepresentation getImageProcessedRepresentation() 
		   throws ValidatingException, TruncatingException, ProcessingException;
   
   public ExtendedImage getExtendedImage();
   
   /*progress bar and details text area listeners*/
   void addProgressChangedListener(ProgressChangedListener listener);
   void removeProgressChangedListener(ProgressChangedListener listener);
   void addDetailsAppendListener(DetailsAppendListener listener);
   void removeDetailsAppendListener(DetailsAppendListener listener);
   
}
