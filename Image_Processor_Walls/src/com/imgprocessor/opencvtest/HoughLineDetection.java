package com.imgprocessor.opencvtest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

import com.imgprocessor.model.Line;
import com.imgprocessor.processor.ImageProcessorImpl;

public class HoughLineDetection {
	
	private String picPath;
	Mat channeledImage;
	Mat imageMatrix;
	public Mat unmodifiedImage;
	public Mat finalImage;
	public Mat preprocessed;
	
	Scalar RED 			= new Scalar(0, 0, 255);
	Scalar WHITE 		= new Scalar(255, 255, 255);
	Scalar GREEN		= new Scalar(0, 255, 0);
	
	ImageProcessorImpl processor;
	
	
	public Integer maxLineGap 		= 5;
	
	public HoughLineDetection(String picPath, ImageProcessorImpl processor) {
		
		this.picPath = picPath;
		this.processor = processor;
		
		//open the file
		imageMatrix = readImage();
//		System.out.println("Image read from " + picPath);
	}
	
	
	public Mat readImage(){
		
		channeledImage 			= Imgcodecs.imread(this.picPath, 0);
		unmodifiedImage 	= Imgcodecs.imread(this.picPath);
		
		return unmodifiedImage;
	}


	private List<Line> transformIntoMatrixOfLines(Mat lines){
		
		List<Line> theLines = new ArrayList<>();
		
		for(int i = 0; i < lines.rows(); i++){
					
			double[] vec = lines.get(i, 0); //each cell of the matrix is a result, a pair of two points' coordinates
						
			double  x1 = vec[0],
					y1 = vec[1],
					x2 = vec[2],
					y2 = vec[3];
			
			theLines.add(new Line(x1, x2, y1, y2));
		}
		
		return theLines;
	}
	
	
	
	public List<Line> detectLines(){
		
		processor.appendDetail("\n\nStarted detecting lines (for walls).");
		processor.setProgress(0);
		
		Filters.clearPictures();
		
		finalImage = new Mat();
		unmodifiedImage.copyTo(finalImage);



		processor.appendDetail("Converting image to black.");
		preprocessed = Filters.convertColorToBlack(finalImage, true, processor);
		processor.setProgress(5);
		
		// + 10
		
//		preprocessed = Filters.denoiseImage(preprocessed, true);


		processor.appendDetail("Running billateral filter on the image (for blurring).");
		preprocessed = Filters.billateralFilterImage(preprocessed, 9, 150, true, processor);
		processor.setProgress(10);
		// + 10
		
		
//		preprocessed = Filters.downsampleImage(preprocessed, true);
		

		
//		preprocessed = Filters.gaussianBlurImage(preprocessed, 5, true);
		

		processor.appendDetail("Denoising image.");
		preprocessed = Filters.denoiseImage(preprocessed, true, processor);
		processor.setProgress(30);
		// + 10
		
//		preprocessed = Filters.runMorphology(preprocessed, 1, Imgproc.MORPH_CROSS, Imgproc.MORPH_GRADIENT, 2, true);

		
		Mat blackMatrix = new Mat(preprocessed.size(), CvType.CV_8UC1, new Scalar(0));
//		
//	
		blackMatrix = new Mat(preprocessed.size(), CvType.CV_8UC3, new Scalar(0));		


		processor.appendDetail("Detecting RAW lines.");
		Mat lines = new Mat();
		LineSegmentDetector lineSegmentDetector = Imgproc.createLineSegmentDetector();
		lineSegmentDetector.detect(preprocessed, lines);
		
		List<Line> lines2 = transformIntoMatrixOfLines(lines);
		

		
		// POST PROCESS LINES
		
		LineProcessor lp = new LineProcessor(lines2);
////
		finalImage = blackMatrix.clone();
		lp.drawLines(lines2, finalImage, WHITE, 1, false, true, processor);
		// lines detected here
		processor.setProgress(70);
		

		// removing same lines
//		processor.appendDetail("Removing some lines that may represent the same line.");
//		finalImage = blackMatrix.clone();
//		lines2 = lp.removeSameLines(lines2, 6);
//		lp.drawLines(lines2, finalImage, WHITE, 1, false, true, processor);

		processor.appendDetail("Fixing some lines (making them right) and removing small lines.");
		finalImage = blackMatrix.clone();
		lines2 = lp.makeLinesRight(lines2, 10);
		lp.drawLines(lines2, finalImage, WHITE, 1, false, true, processor);
		
		finalImage = blackMatrix.clone();
		lines2 = lp.removeSmallLines(lines2, 35);
		lp.drawLines(lines2, finalImage, WHITE, 1, false, true, processor);

		// + 10
		processor.setProgress(80);
		

		processor.appendDetail("Post-processing lines (fixing some gaps, merging same-like lines).");
		finalImage = blackMatrix.clone();
		lines2 = lp.filterLiter(lines2, maxLineGap);
		lp.drawLines(lines2, finalImage, WHITE, 1, false, true, processor);
		
		finalImage = blackMatrix.clone();
		lines2 = lp.makeLinesRight(lines2, 10);
		lp.drawLines(lines2, finalImage, WHITE, 1, false, true, processor);
////		
		// + 10
		processor.setProgress(90);

		
//
		processor.appendDetail("Fixing again the lines (making them right) and re-running the post-processing.");
		finalImage = blackMatrix.clone();
		lines2 = lp.filterLiter(lines2, maxLineGap);
		lp.drawLines(lines2, finalImage, WHITE, 1, false, true, processor);	
//		
		finalImage = blackMatrix.clone();
		
		
		//obtain single lines from multiple 
		lines2 = lp.getLinesWithThisThickness(lines2);
		lp.drawLines(lines2, finalImage, GREEN, 2, false, true, processor);
		
		 // + 10
		processor.setProgress(100);

		

//		finalImage = blackMatrix.clone();
//		lines2 = lp.makeLinesRight(lines2, 15);
//		lp.drawLines(lines2, finalImage, WHITE, 1, false, true);
//		
		processor.appendDetail("Finally. Finished.");
		
		//set image
		try {
			processor.updateImage(ImageIO.read(new File(LineProcessor.drawID + ". lineDetection.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines2;
		
	}

}
