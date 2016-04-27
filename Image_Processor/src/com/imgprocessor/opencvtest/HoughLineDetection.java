package com.imgprocessor.opencvtest;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HoughLineDetection {
	
	private String picPath;
	
	private Integer threshold			= 390; 	// Higher the threshold => fewer lines detected (thickness involved => thicker lines).
												// Means minimun number of intersections to detect a line
	private Integer minLineLength		= 10;	// The minimum length of a line to be detected (pixels?)
	private Integer maxLineGap			= 20;	// Not necessary always. Maximum gap between two points so they're still considered part of the same line.
	
	Mat theImage;

	int thickness 		= 10;
	Scalar color 		= new Scalar(0, 0, 255);
	Double minThickness = 0.0;
	
	
	public HoughLineDetection(String picPath) {
		
		this.picPath = picPath;
		
	}
	
	
	public Mat readImage(){
		
		theImage = Imgcodecs.imread(this.picPath, 0);
		
		return theImage;
	}
	
	
	/**
	 * Preprocess the input image by getting and returning it's skeleton (using the Skeletonizer class)
	 * @param inputImage is the matrix for the unchanged input image (usually opened from a file)
	 * @return another image matrix, black and white, binary, which contains only the skeleton of the original image (small lines for each object), being draw white on black
	 */
	private Mat preprocessImageWithSkeletonization(Mat inputImage){

		System.out.println("Preprocessing - running the skeletonizer algorithm.");
		
		Skeletonizer skeletonizer 		= new Skeletonizer(inputImage);
		skeletonizer.THICKNESS_LIMIT	= minThickness;
		skeletonizer.skeletonize();
		
		Mat skeleton = skeletonizer.getSkeleton();
		String fileName = "preprocessed.png";
		Imgcodecs.imwrite(fileName, skeleton);
		System.out.println("Preprocessing - saved preprocessed image into file " + fileName);
		
		return skeleton;
	}
	
	
	/**
	 * Tries running the Hough Lines (the probabilistic variant, which detects lines with two-points coordinates images).
	 * The input image should not be the raw image, but an preprocessed one.
	 * Preprocessing would mean - cropping the image and standardizing colors and pixels on the image by running an edge detection algorithm or an skeletonization algorithm.
	 * @param inputImage is the input image, preferrably pre-processed.
	 * @return the matrix containint the lines from the image. It's a matrix - each cell consists of 4 integers - x1, y1, x2, y2, all aligned in a single column, multiple rows.
	 */
	public Mat getLinesFromImage(Mat inputImage){

		System.out.println("Processing - running Hough Lines.");
		
		Mat lines = new Mat();
		//rho 			= 1 pixel
		//theta 		= 1 degree in radians (pi/180)
		
		// 2. Find it with the skeleton
		Imgproc.HoughLinesP(inputImage, lines, 1, Math.PI/180, threshold, minLineLength, maxLineGap);
		
		return lines;
	}
	
	
	public void processLines(Mat lines){
		
		//go through the matrix columns
		System.out.println("Number of lines detected: " + lines.rows());
		
		for(int x = 0; x < lines.rows(); x++){
			
			double[] vec = lines.get(x, 0); //each cell of the matrix is a result, a pair of two points' coordinates
			
			double  x1 = vec[0],
					y1 = vec[1],
					x2 = vec[2],
					y2 = vec[3];
			
			//compute min and max so we can crop
			
			System.out.println(String.format("Line [%d] detected: (%f, %f) -> (%f, %f).", x + 1, x1, y1, x2, y2));
			
			Point start = new Point(x1, y1);
			Point end   = new Point(x2, y2);
			
			//Write the data to the edge-detected-gray-3channel-image
			Imgproc.line(theImage, start, end, color, thickness);
			
		}
		
		
		String saveFileName = "lineDetection.png";
		System.out.println(String.format("Writing file containing the lines detected as %s.", saveFileName));
		Imgcodecs.imwrite(saveFileName, theImage);
		System.out.println("Finished detecting lines.");
	}
	
	
	public void detectLines(){
		
		System.out.println("Started detecting lines.");
		
		//open the file
		Mat imageMatrix = readImage();
		System.out.println("Image read from " + picPath);
		
		// preprocess the file
		Mat preprocessed = preprocessImageWithSkeletonization(imageMatrix);
		System.out.println("Image was preprocessed.");
		
		// run hough line detection and detect lines
		Mat lines = getLinesFromImage(preprocessed);
		
		// process the lines
		processLines(lines);
		System.out.println("Finished.");
		
		
		
//		Mat binaryMatrix = new Mat();
//		
//		Mat edgesMatrix 					= new Mat();
//		Mat grayThreeChannelsEdgesMatrix 	= new Mat();
		
		//should do this preprocessing of the image to get only grey colors (bw basically)
		//requires converting to rgb, than back to grey (reverse of the next)
//		   Mat mYuv = new Mat();
//		    Mat mRgba = new Mat();
//		    Mat thresholdImage = new Mat(getFrameHeight() + getFrameHeight() / 2, getFrameWidth(), CvType.CV_8UC1);
//		    mYuv.put(0, 0, data);
//		    Imgproc.cvtColor(mYuv, mRgba, Imgproc.COLOR_YUV420sp2RGB, 4);
//		    Imgproc.cvtColor(mRgba, thresholdImage, Imgproc.COLOR_RGB2GRAY, 4);
		

		// 1/ find it with edge detection
//		Imgproc.HoughLinesP(edgesMatrix, lines, 1, Math.PI/180, threshold, minLineLength, maxLineGap);
		

		// Execute edge detection Canny Algorythm
		//first arg = the initial image matrix
		// scnd arg = the image matrix resulted after edge detection
		// 3rd arg  = the first threshold (sort of precision)
		// 4th arg  = the second threshold (sort of precision)
		// 5th arg  = aperture size
		// 6th arg  = is L2Gradient
		// RETURN: The resulted image is single channeled, 'grey' colored
//		Imgproc.Canny(imageMatrix, edgesMatrix, 50.0, 200.0, 3, false);
		
		
		//first and first - use findContours to crop the image
		
//		Imgproc.findContours(image, contours, hierarchy, mode, method);
		
		
		//
		//############ pre-computation #########
		//compute the image skeleton, by erosion
		//
		/*
		 * A skeleton must preserve the structure of the shape but all redundant pixels should be removed.
		 * */
		
//		System.out.println("Started detecting edges in the image.");
//		

//		Imgproc.cvtColor(imageMatrix, edgesMatrix, Imgproc.COLOR_RGB2GRAY, 1);
		
		//Use cvtColor to convert the edge matrix (consisting of grey color) from single channel image to three channel image
		//Meaning: gray image has un pixel consisting of only one 'color' - 154, transforming it to 3 channel it would mean it has has RGB(154, 154, 154) - 3 channels
//		Imgproc.cvtColor(edgesMatrix, grayThreeChannelsEdgesMatrix, Imgproc.COLOR_GRAY2BGR);
		
		
		
	}

}
