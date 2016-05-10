/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imgprocessor.controller.DetailsApprendAction;
import com.imgprocessor.controller.DetailsApprendListener;
import com.imgprocessor.controller.ProgressChangedAction;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.Coordinates;
import com.imgprocessor.model.ImageProcessedRepresentation;
import com.imgprocessor.model.Wall;
import java.util.Vector;
import javafx.scene.image.Image;
import org.opencv.core.Mat;

/**
 *
 * @author tifuivali
 */
public class ImageProcessorImpl implements ImageProcessor{
    
    // proprocesorul de imagine , contine si imaginea 
    private ImagePreprocessor imagePreprocesor=null;
    
    //aici se vor pune walls windows dors
    private ImageProcessedRepresentation imageProcessedRepresentation=null;
    
    //listeneri aprend details
    Vector<DetailsApprendListener> detailsAprendListeners;
    //listeneri progress changed
    Vector<ProgressChangedListener> progressChangedListeners;
    
    
    public ImageProcessorImpl(ImagePreprocessor imagePreprocesor)
    {
        this.imagePreprocesor=imagePreprocesor;
        imageProcessedRepresentation=new ImageProcessedRepresentation();
        this.detailsAprendListeners=new Vector<>();
        this.progressChangedListeners=new Vector<>();
    }
    
    /**
     * @return the imagePreprocesor
     */
    public ImagePreprocessor getImagePreprocesor() {
        return imagePreprocesor;
    }

    /**
     * @param imagePreprocesor the imagePreprocesor to set
     */
    public void setImagePreprocesor(ImagePreprocessor imagePreprocesor) {
        this.imagePreprocesor = imagePreprocesor;
    }
    


    @Override
    public void processing() throws ValidatingException, TruncatingException, ProcessingException {
       
        //verificam daca am primit un ImageProcesor cu imagine deja preprocesata
        if(imagePreprocesor.getPreProcesedExtendedImage().getImageState()!=ImageState.Preprocesed)
              imagePreprocesor.preProcessing();
        
        
        imagePreprocesor.getPreProcesedExtendedImage().setImageState(ImageState.Processing);
        
        //procesare si umplere Reprezentare
       
       //daca avem nevoie de matricea imaginii:
       Mat matImg=imagePreprocesor.getPreProcesedExtendedImage().getMatRepresentation();
       
       //daca avem nevoie de imagine..
       Image img=imagePreprocesor.getPreProcesedExtendedImage().getImage();
       
       
       //cand vrem sa adaugam o line , la fel pt usi...
        Coordinates start=new Coordinates(0, 0);
        Coordinates end=new Coordinates(5, 5);
       Wall wall=new Wall(start, end);
       this.imageProcessedRepresentation.addWall(wall);
        
        
       imagePreprocesor.getPreProcesedExtendedImage().setImageState(ImageState.Processed);
       //petru setarea procesului 
        setProgress(100);
       
        //pentru aprend detail
        apprendDetail("procesare completa");
        
    }

    /**
     * Procesing image and get it's representation.
     * @return the imageProcessedRepresentation
     * @throws ImagePreProcesingPackage.ValidatingException
     * @throws ImagePreProcesingPackage.TruncatingException
     * @throws ImageProcesingPackage.ProcesingException
     */
    public ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcessingException {
        processing();
        return imageProcessedRepresentation;
    }


    
     private void apprendDetail(String detail)
    {
       for(DetailsApprendListener listener:detailsAprendListeners)
       {
           listener.onApprendPerformed(new DetailsApprendAction(detail+"\r\n"));
       }
    }
    
    private void setProgress(double value)
    {
       for(ProgressChangedListener listener:progressChangedListeners)
       {
           listener.onValueChanged(new ProgressChangedAction(value));
       }
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.add(listener);
        this.getImagePreprocesor().addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.remove(listener);
        this.getImagePreprocesor().removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsApprendListener(DetailsApprendListener listener) {
       this.detailsAprendListeners.add(listener);
       this.getImagePreprocesor().addDetailsApprendListener(listener);
    }

    @Override
    public void removeDetailsApprendListener(DetailsApprendListener listener) {
       this.detailsAprendListeners.remove(listener);
       this.getImagePreprocesor().removeDetailsApprendListener(listener);
    }
    
}
