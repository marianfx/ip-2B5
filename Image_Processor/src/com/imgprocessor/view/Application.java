package com.imgprocessor.view;

import org.opencv.core.Core;

import com.imgprocessor.opencvtest.FaceDetection;
import com.imgprocessor.opencvtest.HoughLineDetection;
import com.imgprocessor.processor.ImageProcessor;

public class Application {
	
	public Application() {
	    	
	    }

    
    /**
     * 
     */
    @SuppressWarnings("unused")
	private ImageProcessor internalProcessor;



    /**
     * @param args
     */
    public void main(String args) {
        // TODO implement here
    }

    /**
     * @return
     */
    public ImageProcessor getImgProcessor() {
        // TODO implement here
        return null;
    }

    // test
	public static void main(String[] args) {
		
		//#################################################
		//########## IMPORTANT ############################
		//## This one thing must be executed once and	###
		//## only once per process, because it loads	###
		//## the native OpenCV library					###
		//## Amen.                                      ###
		//#################################################
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
//		FaceDetection faceDetection = new FaceDetection("test\\andreea.jpg");
//		faceDetection.detectFace();
		
		HoughLineDetection lineDetection = new HoughLineDetection("test\\wallstest.png");
		lineDetection.detectLines();
	}

}
