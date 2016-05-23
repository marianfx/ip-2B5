package com.imgprocessor.opencvtest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import com.imgprocessor.processor.ImageProcessorImpl;

public class Filters {
	
	public static Integer filterID = 1;
	/**
	 * Converts a colored inage into a grey bw image
	 */
	public static Mat convertColorToBlack(Mat input, boolean writeToFile, ImageProcessorImpl processor){
		
		Mat output = new Mat();
		Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2GRAY);
		
		if(writeToFile){
			Imgcodecs.imwrite(filterID + ". convertedtoblack.png", output);
			try {
				processor.updateImage(ImageIO.read(new File(filterID + ". convertedtoblack.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		filterID++;
		return output;
	}
	
	public static void clearPictures() {
		
		File cFile = new File(Paths.get(".").toAbsolutePath().normalize().toString());
		
		String files[] = cFile.list();
		
		for (String file : files) {
			
			if(file.endsWith(".png")){
				
				File file2 = new File(file);
				file2.delete();
			}
		}
	}
	
	/**
	 * Smooth (median blur) the image.
	 * @param blurSize - must be ODD.
	 * @return
	 */
	public static Mat medianBlur(Mat input, Integer blurSize, boolean writeToFile){
		
		Mat output = new Mat();
		
		Imgproc.medianBlur(input, output, blurSize);
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". smoothed.png", output);
		
		filterID++;
		return output;
	}
	
	
	/**
	 * Gaussian Blurs the image with the sigmaX and sigmaY set to blurSize
	 * @param blurSize - must be ODD
	 * @return
	 */
	public static Mat gaussianBlurImage(Mat input, Integer blurSize, boolean writeToFile){
		
		Mat output = new Mat();
		
		Imgproc.GaussianBlur(input, output, new Size(blurSize, blurSize), 2, 2);
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". smoothed - gaussian blur.png", output);
		
		filterID++;
		return output;
	}
	
	
	/**
	 * Thresholding - transforms every pixel with a value below minValue into black(0) and every value above to white (255). It also inverts colors then.
	 * @param minValue - the minimum value which is considered white. If set to 0 => auto detected.
	 * @return
	 */
	public static Mat thresholdImage(Mat input, Integer minValue, boolean writeToFile){
		
		Mat output = new Mat();
		
		Imgproc.threshold(input, output, minValue, 255, Imgproc.THRESH_BINARY);
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". thresholded.png", output);
		
		filterID++;
		return output;
	}
	
	
	
	/**
	 * Adaptive Thresholding - sort of thresholding, but adapts to the pixels around, each pixels becomes the mean of the all-rounders
	 * @param blockSize - the size of the window to make the mean
	 * @param size - the size of the obtain pixels
	 * @return
	 */
	public static Mat adaptiveThresholdImage(Mat input, Integer blockSize, Integer size, boolean writeToFile){
		
		Mat output = new Mat();
		
		Imgproc.adaptiveThreshold(input, output, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, blockSize, size);
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". adaptive-thresholded.png", output);
		
		filterID++;
		return output;
	}
	
	
	public static Mat denoiseImage(Mat input, boolean writeToFile, ImageProcessorImpl processor){
		
		Mat output = new Mat();
		

		Photo.fastNlMeansDenoising(input, output);
		
		if(writeToFile){
			Imgcodecs.imwrite(filterID + ". denoised.png", output);try {
				processor.updateImage(ImageIO.read(new File(filterID + ". denoised.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		filterID++;
		return output;
	}
	
	
	public static Mat downsampleImage(Mat input, boolean writeToFile){
		
		Mat output = new Mat();
		

		Imgproc.pyrDown(input, output);
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". downsampled and blurred.png", output);
		
		filterID++;
		return output;
	}
	
	
	public static Mat skeletonizeImage(Mat input, boolean writeToFile){
		
		Mat output = new Mat();
		

		Skeletonizer skeletonizer = new Skeletonizer(input);
		skeletonizer.skeletonize();
		output = skeletonizer.getSkeleton();
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". skeletonized.png", output);
		
		filterID++;
		return output;
	}
	
	public static Mat runMorphology(Mat input, Integer size, int elemType, int type, int iterations, boolean writeToFile){
		
		Mat output = new Mat();
		

		Mat kernel	= Imgproc.getStructuringElement(elemType, new Size(2*size + 1, 2*size + 1), new Point(size, size));
		Imgproc.morphologyEx(input, output, type, kernel, new Point(size, size), iterations);
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". morphologyEx.png", output);
		
		filterID++;
		return output;
	}
	
	
	
	
	public static Mat billateralFilterImage(Mat input, Integer iterations, Integer Strength, boolean writeToFile, ImageProcessorImpl processor){
		
		Mat output = new Mat();
		

		Imgproc.bilateralFilter(input, output, iterations, Strength, Strength);
		
		if(writeToFile){
			Imgcodecs.imwrite(filterID + ". billateral filtered.png", output);
			try {
				processor.updateImage(ImageIO.read(new File(filterID + ". billateral filtered.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		filterID++;
		return output;
	}
	
	
	public static Mat negateImage(Mat input, boolean writeToFile){
		
		Mat output = new Mat();
		

		Core.bitwise_not(input, output);
		
		if(writeToFile)
			Imgcodecs.imwrite(filterID + ". bitwise not.png", output);
		
		filterID++;
		return output;
	}
	
	
	
	public static void writeImageBasic(Mat image){
		
		Imgcodecs.imwrite(filterID + ". basic.png", image);
		filterID++;
	}
	
	
	

}
