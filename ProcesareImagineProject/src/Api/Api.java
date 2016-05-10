/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Api;

import EventsObject.DetailsApprendListener;
import EventsObject.ProgressChangedListener;
import ImageModel.ImageProcessedRepresentation;
import ImagePreProcesing.TruncatingException;
import ImagePreProcesing.ValidatingException;
import ImageProcesing.ProcesingException;

/**
 *
 * @author tifuivali
 */
public interface Api {
    

    ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException,
                                                               TruncatingException,ProcesingException;
    void addProgressChangedListener(ProgressChangedListener listener);
    void removeProgressChangedListener(ProgressChangedListener listener);
    void addDetailsApprendListener(DetailsApprendListener listener);
    void removeDetailsApprendListener(DetailsApprendListener listener);
}
