/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExternalApiPackage;

import EventsObjectPackages.DetailsApprendListener;
import EventsObjectPackages.ProgressChangedListener;
import ImageModelPackage.ImageProcessedRepresentation;
import ImagePreProcesingPackage.TruncatingException;
import ImagePreProcesingPackage.ValidatingException;
import ImageProcesingPackage.ProcesingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author tifuivali
 */
public interface ImageProcesorApi {
    

    ImageProcessedRepresentation getImageProcessedRepresentation(File imageFile) throws ValidatingException,
                                                               TruncatingException,ProcesingException,FileNotFoundException;
    void addProgressChangedListener(ProgressChangedListener listener);
    void removeProgressChangedListener(ProgressChangedListener listener);
    void addDetailsApprendListener(DetailsApprendListener listener);
    void removeDetailsApprendListener(DetailsApprendListener listener);
}
