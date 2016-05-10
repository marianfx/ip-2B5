/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;

import com.imgprocessor.controller.DetailsApprendListener;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.ImageProcessedRepresentation;
import com.imgprocessor.processor.TruncatingException;
import com.imgprocessor.processor.ValidatingException;
import com.imgprocessor.processor.ProcessingException;



/**
 *
 * @author tifuivali
 */
public interface Api {
    

    ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException,
                                                               TruncatingException,ProcessingException;
    void addProgressChangedListener(ProgressChangedListener listener);
    void removeProgressChangedListener(ProgressChangedListener listener);
    void addDetailsApprendListener(DetailsApprendListener listener);
    void removeDetailsApprendListener(DetailsApprendListener listener);
}
