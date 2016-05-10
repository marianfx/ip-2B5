/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Api;

import EventsObject.DetailsApprendListener;
import EventsObject.ProgressChangedListener;
import ExtendedImage.ImageState;
import ImageModel.ImageProcessedRepresentation;
import ImagePreProcesing.TruncatingException;
import ImagePreProcesing.ValidatingException;
import ImageProcesing.ImageProcesor;
import ImageProcesing.ProcesingException;

/**
 *
 * @author tifuivali
 */
public class InternalApiImpl implements Api{

    //static deoarece va fi setatat din main controler
    private static ImageProcesor internalImageProcesor; 
    public InternalApiImpl()
    {
    }
    
    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcesingException {
        if(!existsInternalImageProcessorInitialized())
            return null;
        if(!isProcessed())
          internalImageProcesor.processing();
        return internalImageProcesor.getImageProcessedRepresentation();
        
    }
    
    private boolean existsInternalImageProcessorInitialized()
    {
       return internalImageProcesor==null;
    }
    
    private boolean isProcessed()
    {
        return internalImageProcesor.getExtendedImage().getImageState()==ImageState.Processed;
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        internalImageProcesor.addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
       internalImageProcesor.removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsApprendListener(DetailsApprendListener listener) {
        internalImageProcesor.addDetailsApprendListener(listener);
    }

    @Override
    public void removeDetailsApprendListener(DetailsApprendListener listener) {
        internalImageProcesor.removeDetailsApprendListener(listener);
    }
    
    
}
