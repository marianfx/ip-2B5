package com.imgprocessor.opencvtest;

import java.util.ArrayList;
import java.util.List;

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
	
	
	public Integer maxLineGap1 		= 5;//for very closed-by lines
	public Integer maxLineGap2		= 50;//for final merging of lines
	public Integer maxLineGap3		= 50;//for perpendicularity
	
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
	
	
	/**
	 * Detects the lines from the image and then post-processes them to obtain the walls.
	 * @return
	 */
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

		processor.appendDetail("Running billateral filter on the image (for blurring).");
		preprocessed = Filters.billateralFilterImage(preprocessed, 9, 150, true, processor);
		processor.setProgress(10);
		// + 10
		
		

		processor.appendDetail("Denoising image.");
		preprocessed = Filters.denoiseImage(preprocessed, true, processor);
		processor.setProgress(30);
		// + 10


		processor.appendDetail("Detecting RAW lines.");
		Mat lines = new Mat();
		LineSegmentDetector lineSegmentDetector = Imgproc.createLineSegmentDetector();
		lineSegmentDetector.detect(preprocessed, lines);
		
		// get the list of lines and post-process them
		List<Line> lines2 = transformIntoMatrixOfLines(lines);
		return postProcessLines(lines2);
		
	}
	
	
	private List<Line> postProcessLines(List<Line> input){
		
		// POST PROCESS LINES
		
		LineProcessor lp = new LineProcessor(input);
		List<Line> output = new ArrayList<>();
		Mat blackMatrix = new Mat(preprocessed.size(), CvType.CV_8UC3, new Scalar(0));

		// the initial detected lines
		finalImage = blackMatrix.clone();
		LineProcessor.drawLines(input, finalImage, WHITE, 1, false, true, processor);
		processor.setProgress(70);
		

		processor.appendDetail("Fixing some lines (making them right) and removing small lines.");
		finalImage = blackMatrix.clone();
		output = lp.makeLinesRight(input, 10);
		LineProcessor.drawLines(output, finalImage, WHITE, 1, false, true, processor);
		
		finalImage = blackMatrix.clone();
		output = lp.removeSmallLines(output, 35);
		LineProcessor.drawLines(output, finalImage, WHITE, 1, false, true, processor);

		// + 10
		processor.setProgress(80);
		

		processor.appendDetail("Post-processing lines (fixing some gaps, merging same-like lines).");
		finalImage = blackMatrix.clone();
		output = lp.filterLiter(output, maxLineGap1);
		LineProcessor.drawLines(output, finalImage, WHITE, 1, false, true, processor);
		
		
		finalImage = blackMatrix.clone();
		output = lp.makeLinesRight(output, 10);
		LineProcessor.drawLines(output, finalImage, WHITE, 1, false, true, processor);		
		// + 10
		processor.setProgress(90);

		
		processor.appendDetail("Fixing again the lines (making them right) and re-running the post-processing.");
		finalImage = blackMatrix.clone();
		output = lp.filterLiter(output, maxLineGap1);
		LineProcessor.drawLines(output, finalImage, WHITE, 1, false, true, processor);	
		
		
		
		//obtain single lines from multiple 
		finalImage = blackMatrix.clone();
		output = lp.getLinesWithThisThickness(finalImage, output);
		LineProcessor.drawLines(output, finalImage, GREEN, 2, false, true, processor);
		
		

		// then unite the perpendicular ones
		
		processor.appendDetail("Final merging of lines");
		output = LineProcessor.uniteLinesFinal(finalImage, output, maxLineGap3, false);
		finalImage = blackMatrix.clone();
		LineProcessor.drawLines(output, finalImage, GREEN, 1, true, true, processor);	
		
//		output = lp.removeSameLines(output, 50);
//		finalImage = blackMatrix.clone();
//		lp.drawLines(output, finalImage, GREEN, 1, true, true, processor);
		
		 // + 10
		processor.setProgress(100);
		processor.appendDetail("Finally. Finished.");
		
		
		return output;
	}

}
