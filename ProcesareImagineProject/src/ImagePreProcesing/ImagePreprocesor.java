/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImagePreProcesing;

import EventsObject.DetailsApprendListener;
import EventsObject.ProgressChangedListener;
import ExtendedImage.ExtendedImage;

/**
 *
 * @author tifuivali
 * Am zis sa renuntam la convertor ..cum credeti si voi..
 */
public interface ImagePreprocesor {
    
   void preProcessing() throws ValidatingException,TruncatingException;
   void addProgressChangedListener(ProgressChangedListener listener);
   void removeProgressChangedListener(ProgressChangedListener listener);
   void addDetailsApprendListener(DetailsApprendListener listener);
   void removeDetailsApprendListener(DetailsApprendListener listener);
   ExtendedImage getPreProcesedExtendedImage();
    
}
