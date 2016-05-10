/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExternalApiPackage;

import EventsObjectPackages.DetailsApprendListener;
import EventsObjectPackages.ProgressChangedListener;
import ExtendedImagePackage.ExtendedImage;
import ImageModelPackage.ImageProcessedRepresentation;
import ImagePreProcesingPackage.ImagePreProcesorImpl;
import ImagePreProcesingPackage.TruncatingException;
import ImagePreProcesingPackage.ValidatingException;
import ImageProcesingPackage.ImageProcesor;
import ImageProcesingPackage.ImageProcesorImpl;
import ImageProcesingPackage.ProcesingException;
import ImagePreProcesingPackage.ImagePreprocesor;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author tifuivali
 */
public class ImageProcesorApiImpl implements ImageProcesorApi{

    private ExtendedImage extendedImage=null;
    private ImagePreprocesor imagePreprocesor=null;
    private ImageProcesor imageProcesor=null;
    
    public ImageProcesorApiImpl(File imageFile) throws FileNotFoundException
    {
        extendedImage=new ExtendedImage(imageFile);
        imagePreprocesor=new ImagePreProcesorImpl(extendedImage);
        imageProcesor=new ImageProcesorImpl(imagePreprocesor);
    }

    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation(File imageFile) throws ValidatingException, TruncatingException, ProcesingException, FileNotFoundException {
         extendedImage=new ExtendedImage(imageFile);
        imagePreprocesor=new ImagePreProcesorImpl(extendedImage);
        imageProcesor=new ImageProcesorImpl(imagePreprocesor);
        return  imageProcesor.getImageProcessedRepresentation();
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
