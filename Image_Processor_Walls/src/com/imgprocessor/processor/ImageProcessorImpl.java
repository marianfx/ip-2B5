/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imgprocessor.controller.DetailsAppendAction;
import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ImageUpdateAction;
import com.imgprocessor.controller.ImageUpdateListener;
import com.imgprocessor.controller.ProgressChangedAction;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.ImageProcessedRepresentation;
import com.imgprocessor.model.Line;
import com.imgprocessor.model.Representation;
import com.imgprocessor.opencvtest.HoughLineDetection;

import java.awt.image.BufferedImage;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Vector;
/**
 *
 * @author tifuivali
 */
public class ImageProcessorImpl implements ImageProcessor {
    
    private ImagePreProcessor imagePreProcessor;
    private ExtendedImage extendedImage;
    
    private ImageProcessedRepresentation imageProcessedRepresentation;
    
    private Vector<DetailsAppendListener> detailsAppendListeners;
    private Vector<ProgressChangedListener> progressChangedListeners;
    private Vector<ImageUpdateListener> imageChangedListeners;
    
    private File ImageFile;
    private ImageProcessorImpl thisReff = this;
    public static boolean DETECT_ONLY_WALLS = false;
    
    public Representation imageRepresentation;
    
    
    
    public ImageProcessorImpl(File imageFile) throws FileNotFoundException {
    	
        this.imagePreProcessor = new ImagePreProcessorImpl(imageFile);
        this.extendedImage=this.imagePreProcessor.getPreProcessedExtendedImage();
        this.detailsAppendListeners = new Vector<>();
        this.progressChangedListeners = new Vector<>();
        this.imageChangedListeners = new Vector<>();
    	this.ImageFile = imageFile;
    	
    }
    
    /**
     * @return the imagePreprocesor
     */
    public ImagePreProcessor getImagePreprocessor() {
        return imagePreProcessor;
    }

    /**
     * @param imagePreprocesor the imagePreprocesor to set
     */
    public void setImagePreprocessor(ImagePreProcessor imagePreprocessor) {
        this.imagePreProcessor = imagePreprocessor;
    }

    @Override
    public void process() 
    		throws 	ValidatingException, TruncatingException, ProcessingException {
    	
    	// here, order kindof matters
    	//// loads the opencv 249 library for features2d
		System.loadLibrary("opencv_java249");
		// loads the opencv 310 library for fillConvexPoly and all the others
		System.loadLibrary("opencv_java310");
		
		imageRepresentation = new Representation();
		
		
		// run template detection && line detection in another thread
		new Thread(){
			
			public void run() {
				
				// object detection
				if(!DETECT_ONLY_WALLS){
					DetectObject objectDetector = new DetectObject(ImageFile.getAbsolutePath(), thisReff);
			        objectDetector.detectAllObject();
		        }
		        
				// line detection
		        HoughLineDetection houghLineDetection = new HoughLineDetection(DetectObject.TEMPLATE_OUTPUT_PATH, thisReff);
		        List<Line> detectedWalls = houghLineDetection.detectLines();
		        imageRepresentation.populateWalls(detectedWalls);
		        
		        //xml encode
		        thisReff.setProgress(0);
		        thisReff.appendDetail("Serializing the representation into 'Representation.xml'...");
				try {
					   
					XMLEncoder  myEncoder = new XMLEncoder(new FileOutputStream ("Representation.xml"));
					myEncoder.writeObject(imageRepresentation);
					myEncoder.flush();
					myEncoder.close();

			        thisReff.setProgress(100);
			        thisReff.appendDetail("Finished serialization.");
					
				} catch (FileNotFoundException e) {
					thisReff.appendDetail("FAILED!");
					e.printStackTrace();
				}
			};
			
		}.start();
		
    }

    /**
     * Procesing image and get it's representation.
     * @return the imageProcessedRepresentation
     * @throws com.imgproctempl.processor.ValidatingException
     * @throws com.imgproctempl.processor.TruncatingException
     * @throws com.imgproctempl.processor.ProcessingException
     */
    @Override
    public ImageProcessedRepresentation getImageProcessedRepresentation() throws ValidatingException, TruncatingException, ProcessingException {
        process();
        return imageProcessedRepresentation;
    }
    
    public void updateImage(BufferedImage img)
    {
    	for(ImageUpdateListener listener: imageChangedListeners)
        {
            listener.onUpdatePerformed(new ImageUpdateAction(img));
        }
    }
    
    
    public void appendDetail(String detail)
    {
       for(DetailsAppendListener listener:detailsAppendListeners)
       {
           listener.onAppendPerformed(new DetailsAppendAction(detail + "\r\n"));
       }
    }
    
    public void setProgress(double value)
    {
       for(ProgressChangedListener listener:progressChangedListeners)
       {
           listener.onValueChanged(new ProgressChangedAction(value));
       }
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.add(listener);
        this.getImagePreprocessor().addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.remove(listener);
        this.getImagePreprocessor().removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsAppendListener(DetailsAppendListener listener) {
    	
       this.detailsAppendListeners.add(listener);
       this.getImagePreprocessor().addDetailsAppendListener(listener);
    }
    
    public void addImageChangeListers(ImageUpdateListener listener) {
    	
        this.imageChangedListeners.add(listener);
     }

    @Override
    public void removeDetailsAppendListener(DetailsAppendListener listener) {
       this.detailsAppendListeners.remove(listener);
       this.getImagePreprocessor().removeDetailsAppendListener(listener);
    }

    @Override
    public ExtendedImage getExtendedImage() {
    	
        return this.extendedImage;
    }
    
}
