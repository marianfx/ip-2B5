/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Api;

import EventsObject.DetailsApprendListener;
import EventsObject.ProgressChangedListener;
import ExtendedImage.ExtendedImage;
import ImageModel.ImageProcessedRepresentation;
import ImagePreProcesing.ImagePreProcesorImpl;
import ImagePreProcesing.ImagePreprocesor;
import ImagePreProcesing.TruncatingException;
import ImagePreProcesing.ValidatingException;
import ImageProcesing.ImageProcesor;
import ImageProcesing.ImageProcesorImpl;
import ImageProcesing.ProcesingException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author tifuivali
 */
public class ExternalApiImpl implements Api{

private ExtendedImage extendedImage=null;
    private ImagePreprocesor imagePreprocesor=null;
    private ImageProcesor imageProcesor=null;
    
    public ExternalApiImpl(File imageFile) throws FileNotFoundException
    {
        extendedImage=new ExtendedImage(imageFile);
        imagePreprocesor=new ImagePreProcesorImpl(extendedImage);
        imageProcesor=new ImageProcesorImpl(imagePreprocesor);
    }

    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcesingException{
        imageProcesor.processing();
        return imageProcesor.getImageProcessedRepresentation();
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        imageProcesor.addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        imagePreprocesor.removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsApprendListener(DetailsApprendListener listener) {
        imageProcesor.addDetailsApprendListener(listener);
    }

    @Override
    public void removeDetailsApprendListener(DetailsApprendListener listener) {
       imageProcesor.removeDetailsApprendListener(listener);
    }
}
