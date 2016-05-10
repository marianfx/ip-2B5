/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcesing;

import EventsObject.DetailsApprendListener;
import EventsObject.ProgressChangedListener;
import ExtendedImage.ExtendedImage;
import ImageModel.ImageProcessedRepresentation;
import ImagePreProcesing.TruncatingException;
import ImagePreProcesing.ValidatingException;

/**
 *
 * @author tifuivali
 */
public interface ImageProcesor {
    
   void processing() throws ValidatingException,TruncatingException,ProcesingException;
   void addProgressChangedListener(ProgressChangedListener listener);
   void removeProgressChangedListener(ProgressChangedListener listener);
   void addDetailsApprendListener(DetailsApprendListener listener);
   void removeDetailsApprendListener(DetailsApprendListener listener);
   ExtendedImage getExtendedImage();
   ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcesingException;
}
