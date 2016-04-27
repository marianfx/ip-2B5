package com.imgprocessor.opencvtest;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {
	
	private String picPath;
	
	public FaceDetection(String picPath){
		
		this.picPath = picPath;
	}
	
	
	public void detectFace(){
		
		System.out.println("Started detection on file " + picPath);
		
		//create a face detector using configuration from the cascade frontal face config
		CascadeClassifier faceDetector = new CascadeClassifier("test\\lbpcascade_frontalface.xml");
		
		//load the matrix with the image
		Mat imageMatrix = Imgcodecs.imread(picPath);
		
		//Create a matrix of rectangles
		MatOfRect faceDetections = new MatOfRect();
		
		//Detect the faces in the imageMatrix and save them in the faceDetections matrix
		faceDetector.detectMultiScale(imageMatrix, faceDetections);
		
		System.out.println(String.format("Detected %s faces.", faceDetections.toArray().length));
		
		//Draw a box around each face
		for (Rect rectangle : faceDetections.toArray()) {
			
			Point boxStart 	= new Point(rectangle.x, rectangle.y);										//start = face detection (x,y) coords
			Point boxEnd 	= new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height);	//end 	= (x,y) + detection (width, height)
			Scalar color	= new Scalar(0, 255, 0);													//draw with green
			
			Imgproc.rectangle(imageMatrix, boxStart, boxEnd, color);
		}
		
		String saveFileName = "faceDetection.png";
		System.out.println(String.format("Writing %s with faces detected.", saveFileName));
		Imgcodecs.imwrite(saveFileName, imageMatrix);
		System.out.println("Finished detecting.");
		
	}
	

}
