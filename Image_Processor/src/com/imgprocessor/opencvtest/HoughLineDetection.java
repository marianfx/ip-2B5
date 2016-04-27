package com.imgprocessor.opencvtest;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HoughLineDetection {
	
	private String picPath;
	
	public HoughLineDetection(String picPath) {
		
		this.picPath = picPath;
		
	}
	
	public void detectLines(){
		
		System.out.println("Started detecting lines.");
		
		//open the file
		Mat imageMatrix = Imgcodecs.imread(picPath, 0);
//		Mat binaryMatrix = new Mat();
		
		Mat edgesMatrix 					= new Mat();
		Mat grayThreeChannelsEdgesMatrix 	= new Mat();
		
		//should do this preprocessing of the image to get only grey colors (bw basically)
		//requires converting to rgb, than back to grey (reverse of the next)
//		   Mat mYuv = new Mat();
//		    Mat mRgba = new Mat();
//		    Mat thresholdImage = new Mat(getFrameHeight() + getFrameHeight() / 2, getFrameWidth(), CvType.CV_8UC1);
//		    mYuv.put(0, 0, data);
//		    Imgproc.cvtColor(mYuv, mRgba, Imgproc.COLOR_YUV420sp2RGB, 4);
//		    Imgproc.cvtColor(mRgba, thresholdImage, Imgproc.COLOR_RGB2GRAY, 4);
		

		

		// Execute edge detection Canny Algorythm
		//first arg = the initial image matrix
		// scnd arg = the image matrix resulted after edge detection
		// 3rd arg  = the first threshold (sort of precision)
		// 4th arg  = the second threshold (sort of precision)
		// 5th arg  = aperture size
		// 6th arg  = is L2Gradient
		// RETURN: The resulted image is single channeled, 'grey' colored
		Imgproc.Canny(imageMatrix, edgesMatrix, 50.0, 200.0, 3, false);
		
		
		//first and first - use findContours to crop the image
		
//		Imgproc.findContours(image, contours, hierarchy, mode, method);
		
		//
		//############ pre-computation #########
		//compute the image skeleton, by erosion
		//
		/*
		 * A skeleton must preserve the structure of the shape but all redundant pixels should be removed.
		 * */
		
		// 1. Transform to gray, binary image
//		int threshold = 20;
//		int maxval    = 255;
//		Imgproc.threshold(imageMatrix, imageMatrix, threshold, maxval, Imgproc.THRESH_BINARY);
		

//		Imgcodecs.imwrite("grayscaled.png", binaryMatrix);
		
		// 2. Build the skeleton matrix, with the same size as the initial one. The Skeleton is filled with black at the beginning.
//		Size size = imageMatrix.size();
//		int type  = CvType.CV_8UC1;
//		Scalar color  = new Scalar(0);
//		
//		Mat skeleton = new Mat(size, type, color);
		
		// 3. Also build a temp matrix to store the intermediate computation
//		Mat temp = new Mat(size, type);
		
		// 4. Declare the element which will be used on the transforms ,here a 3x3 matrix, cross-shaped
//		type = Imgproc.MORPH_CROSS;
//		size = new Size(3, 3);
//		Mat element = Imgproc.getStructuringElement(type, size);
//		
//		// the algorithm, which loops untill there is at least one pixel remaining
//		boolean done 	= false;
//		type 			= Imgproc.MORPH_OPEN;
//		
//		do{
//			
//			Imgproc.morphologyEx(imageMatrix, temp, type, element);     // get the cross from the image
//			Core.bitwise_not(temp, temp);								// invert te bits
//			Core.bitwise_and(imageMatrix, temp, temp);  				// image AND temp, store in temp
//			Core.bitwise_or(skeleton, temp, skeleton); 					// store the results in the skeleton with an bitwise or
//			Imgproc.erode(imageMatrix, imageMatrix, element);           // remove from the init matrix the used kernel
//			
//			MinMaxLocResult minMaxLocResult =  Core.minMaxLoc(imageMatrix);
//			
//			done = (minMaxLocResult.maxVal == 0);//stop when threshold = 0
//			
//		} while (!done);
//		
//
//		Imgcodecs.imwrite("skeleton.png", skeleton);
//		
//
//		System.out.println("Started detecting edges in the image.");
//		

//		Imgproc.cvtColor(imageMatrix, edgesMatrix, Imgproc.COLOR_RGB2GRAY, 1);
		
		//Use cvtColor to convert the edge matrix (consisting of grey color) from single channel image to three channel image
		//Meaning: gray image has un pixel consisting of only one 'color' - 154, transforming it to 3 channel it would mean it has has RGB(154, 154, 154) - 3 channels
		Imgproc.cvtColor(edgesMatrix, grayThreeChannelsEdgesMatrix, Imgproc.COLOR_GRAY2BGR);
		

		System.out.println("Started Hough Line Detection.");
		
		Mat lines = new Mat();
		int threshold 	= 50; //Higher the threshold => fewer lines detected (thickness involved => thicker lines). Means minimun number of intersections to detect a line
		int minLineLength = 50; //not necessary always. Minimum number of points to form a line.
		int maxLineGap  = 10; //not necessary always. Maximum gap between two points so it's still considered a line
		//rho = 1 means 1 pixel
		//theta in radians, 1 degree (pi/180)
		
		// 1/ find it with edge detection
//		Imgproc.HoughLinesP(edgesMatrix, lines, 1, Math.PI/180, threshold, minLineLength, maxLineGap);
		
		// 2. Find it with the skeleton
		Imgproc.HoughLinesP(edgesMatrix, lines, 1, Math.PI/180, threshold, minLineLength, maxLineGap);
		
		
		System.out.println(lines.rows());
		System.out.println(lines.cols());
		
		
		//go through the matrix columns
		for(int x = 0; x < lines.rows(); x++){
			
			double[] vec = lines.get(x, 0); //each cell of the matrix is a result, a pair of two points' coordinates
			
			double  x1 = vec[0],
					y1 = vec[1],
					x2 = vec[2],
					y2 = vec[3];
			
			//compute min and max so we can crop
			
			System.out.println(String.format("Line detected: (%f, %f) -> (%f, %f).", x1, y1, x2, y2));
			
			Point start = new Point(x1, y1);
			Point end   = new Point(x2, y2);
			
			//Write the data to the edge-detected-gray-3channel-image
			int thickness = 5;
			Scalar color = new Scalar(255, 0, 0);//red
			Imgproc.line(grayThreeChannelsEdgesMatrix, start, end, color, thickness);
			
		}
		
		
		String saveFileName = "lineDetection.png";
		System.out.println(String.format("Writing %s with lines detected.", saveFileName));
		Imgcodecs.imwrite(saveFileName, grayThreeChannelsEdgesMatrix);
		System.out.println("Finished detecting lines.");
		
	}

}
