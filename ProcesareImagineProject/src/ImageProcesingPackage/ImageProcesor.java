/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcesingPackage;

import EventsObjectPackages.DetailsApprendListener;
import EventsObjectPackages.ProgressChangedListener;
import ImageModelPackage.ImageProcessedRepresentation;
import ImagePreProcesingPackage.TruncatingException;
import ImagePreProcesingPackage.ValidatingException;

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
   ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcesingException;
}
