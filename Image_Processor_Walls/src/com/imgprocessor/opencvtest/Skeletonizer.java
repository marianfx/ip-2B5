package com.imgprocessor.opencvtest;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgproc.Imgproc;

/**
 * A Skeleton preserves the main structure of the object, but removes the redundant pixels (only small shaped lines of the object structure remain)
 * @author F. Marian
 *
 */
public class Skeletonizer {
	
	private Mat imageMatrix;									// the initial image
	private Mat internalMatrix	= new Mat();					// the image, thresholded to binary matrix, grey colored
	private Mat skeleton;										// the resulted Skeleton
	
	private Size imageSize;										// the size of the binary image
	private int skeletonType	= CvType.CV_8UC1;				// OpenCV representation type of the image object which will represent the skeleton. Usually 8 bit image.
	private Scalar color		= new Scalar(0);				// the color of the skeleton (background). Usually black
	
	private Mat element;										// this is the element which will detect dots in the binary image, usually a cross-shaped
	private int elemType		= Imgproc.MORPH_CROSS;			// specifies the type of the element. Usually a cross
	private double erosionSize	= 1;							// the size of the object. Usually = 3
	
	private Double threshold		= 200.0;					// indicates the threshold used for pixel-detection (number of things to intersect so they can form a valid thing - eg dots for line)
	private Double maxValue 		= 255.0;					// the maximum value a detected pixel has (at detection, not after)
	
	public Double THICKNESS_LIMIT	= 0.0;						// indicates the thickness of lines at which the algorithm will stop eroding the image.
																// shall be less than maxVALUE (at >= will identify nothing)
	
	
	
	public Skeletonizer(Mat imageMatrix) {
		
		this.imageMatrix = imageMatrix;
		
	}

	
	/**
	 * Transforms the input image into an bw binary one, and initializes the necessary data for processing.
	 */
	private void configureImage(){
		
		internalMatrix = imageMatrix;
		
		//configure parameters
		imageSize 		= internalMatrix.size();
		skeleton 		= new Mat(imageSize, skeletonType, color);						// init the skeleton
		
		// construct the element
		element		 	= Imgproc.getStructuringElement(elemType, new Size(2*erosionSize + 1, 2*erosionSize + 1), new Point(-1, -1));
	}
	
	
	/**
	 * Runs the algorithm of getting the skeleton of the current object (only the important 'lines') (it goes through the repeated process of cross dilate - erode)
	 */
	public void skeletonize(){
		
		configureImage();
		
		// init a temp matrix, which will be used at the dilate - erode process
		Mat temp = new Mat(imageSize, skeletonType);
		
		boolean done 		= false;
		int type 			= Imgproc.MORPH_OPEN;
		
		do{
			
			Imgproc.morphologyEx(internalMatrix, temp, type, element);      // execute a morphology (transform) on the image using the cross (open = dilate + erode)
			Core.bitwise_not(temp, temp);									// invert te bits of the temp matrix
			Core.bitwise_and(internalMatrix, temp, temp);  					// store only the pixels found in the initial image and the temp image too (makes sense, right - erode a little beet, keep only what's correct)
			Core.bitwise_or(skeleton, temp, skeleton); 						// store the results in the skeleton with an bitwise or
			Imgproc.erode(internalMatrix, internalMatrix, element);         // remove from the init matrix the used kernel
			
			// Get max thickness and check if need to stop
			MinMaxLocResult minMaxLocResult =  Core.minMaxLoc(internalMatrix);
			
			done = (minMaxLocResult.maxVal <= THICKNESS_LIMIT);//stop at the given thickness
			
		} while (!done);
	}
	
	
	
	
	
	
	/**
	 * @return the threshold
	 */
	public Double getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}



	/**
	 * @return the skeleton
	 */
	public Mat getSkeleton() {
		return skeleton;
	}



	/**
	 * @return the maxValue
	 */
	public Double getMaxValue() {
		return maxValue;
	}



	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}


}
