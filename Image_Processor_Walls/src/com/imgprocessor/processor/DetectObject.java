package com.imgprocessor.processor;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.imgmodel.buildingParts.Coordinates;
import com.imgmodel.buildingParts.Door;
import com.imgmodel.buildingParts.Stairs;
import com.imgmodel.buildingParts.Window;
import com.imgprocessor.model.Line;
import com.imgprocessor.opencvtest.LineProcessor;


public class DetectObject
{
	public class PointsWrapper{

		public Point point1, point2, point3, point4;

		public PointsWrapper(Point p1, Point p2, Point p3, Point p4) {

			this.point1 = p1;
			this.point2 = p2;
			this.point3 = p3;
			this.point4 = p4;
		}
	}

	private String filePath;
	private ImageProcessorImpl processor;
	public static String TEMPLATE_OUTPUT_PATH = "_output//template_output.jpg";
	public static String TEMPLATE_INPUT_PATH  = "_input//templates2";

	public static String[] templates = new String[]{"door", "stair", "window", "elevator", "hydrant", "electricpanel"};

	Point point1, point2, point3, point4;


	public DetectObject(String filePath, ImageProcessorImpl processor){

		this.filePath = filePath;
		this.processor = processor;
	}

	/**
	 * Finds an object into the specified image
	 */
	public Mat find_object(Mat objectImage, Mat sceneImage)
	{
		Mat img = sceneImage;
		MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
		FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SURF);
		//System.out.println("Detecting key points...");
		featureDetector.detect(objectImage, objectKeyPoints);

		MatOfKeyPoint objectDescriptors = new MatOfKeyPoint();
		DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SURF);
		//System.out.println("Computing descriptors...");
		descriptorExtractor.compute(objectImage, objectKeyPoints, objectDescriptors);

		// Create the matrix for output image. 
		Mat outputImage = new Mat(objectImage.rows(), objectImage.cols(), Highgui.CV_LOAD_IMAGE_COLOR);
		Scalar newKeypointColor = new Scalar(255, 0, 0);

		//System.out.println("Drawing key points on object image...");
		Features2d.drawKeypoints(objectImage, objectKeyPoints, outputImage, newKeypointColor, 0);

		// Match object image with the scene image
		MatOfKeyPoint sceneKeyPoints = new MatOfKeyPoint();
		MatOfKeyPoint sceneDescriptors = new MatOfKeyPoint();
		//System.out.println("Detecting key points in background image...");
		featureDetector.detect(sceneImage, sceneKeyPoints);
		//System.out.println("Computing descriptors in background image...");
		descriptorExtractor.compute(sceneImage, sceneKeyPoints, sceneDescriptors);

		Mat matchoutput = new Mat(sceneImage.rows() * 2, sceneImage.cols() * 2, Highgui.CV_LOAD_IMAGE_COLOR);
		Scalar matchestColor = new Scalar(0, 255, 0);

		List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
		DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
		//System.out.println("Matching object and scene images...");
		descriptorMatcher.knnMatch(objectDescriptors, sceneDescriptors, matches, 2);


		//System.out.println("Calculating good match list...");
		LinkedList<DMatch> goodMatchesList = new LinkedList <DMatch>();


		float nndrRatio = 0.7f;

		for (int i = 0; i < matches.size(); i++)
		{
			MatOfDMatch matofDMatch =  (MatOfDMatch) matches.get(i);
			DMatch[] dmatcharray = matofDMatch.toArray();
			DMatch m1 = dmatcharray[0];
			DMatch m2 = dmatcharray[1];


			if (m1.distance <= m2.distance * nndrRatio)
			{
				goodMatchesList.addLast(m1);

			}
		}

		Features2d.drawMatches(objectImage, objectKeyPoints, sceneImage, sceneKeyPoints, matches.get(0) , matchoutput, matchestColor, newKeypointColor, new MatOfByte(), 2);

		// write out the good matches
		Highgui.imwrite("_output//outputImage.jpg", matchoutput);

		if (goodMatchesList.size() >= 6)
		{
			List <KeyPoint> objKeypointlist = objectKeyPoints.toList();
			List <KeyPoint> scnKeypointlist = sceneKeyPoints.toList();

			LinkedList <Point> objectPoints = new LinkedList<Point>();
			LinkedList <Point> scenePoints = new LinkedList<Point>();

			for (int i = 0; i < goodMatchesList.size(); i++)
			{
				objectPoints.addLast(objKeypointlist.get(goodMatchesList.get(i).queryIdx).pt);
				scenePoints.addLast(scnKeypointlist.get(goodMatchesList.get(i).trainIdx).pt);
			}

			MatOfPoint2f objMatOfPoint2f = new MatOfPoint2f();
			objMatOfPoint2f.fromList(objectPoints);
			MatOfPoint2f scnMatOfPoint2f = new MatOfPoint2f();
			scnMatOfPoint2f.fromList(scenePoints);

			Mat homography = Calib3d.findHomography(objMatOfPoint2f, scnMatOfPoint2f, Calib3d.RANSAC, 3);

			Mat obj_corners = new Mat(4, 1, CvType.CV_32FC2);
			Mat scene_corners = new Mat(4, 1, CvType.CV_32FC2);

			obj_corners.put(0, 0, new double[] { 0, 0 });
			obj_corners.put(1, 0, new double[] { objectImage.cols(), 0 });
			obj_corners.put(2, 0, new double[] { objectImage.cols(), objectImage.rows() });
			obj_corners.put(3, 0, new double[] { 0, objectImage.rows() });

			//System.out.println("Transforming object corners to scene corners...");
			Core.perspectiveTransform(obj_corners, scene_corners, homography);

			List<Line> lines=new  ArrayList<Line>();

			Line l1 = new Line(new Point(scene_corners.get(0, 0)), new Point(scene_corners.get(1, 0)));
			Line l2 = new Line(new Point(scene_corners.get(1, 0)), new Point(scene_corners.get(2, 0)));
			Line l3 = new Line(new Point(scene_corners.get(2, 0)), new Point(scene_corners.get(3, 0)));
			Line l4 = new Line(new Point(scene_corners.get(3, 0)), new Point(scene_corners.get(0, 0)));
			lines.add(l1);
			lines.add(l2);
			lines.add(l3);
			lines.add(l4);

			int error = 20;
			LineProcessor lineProc = new LineProcessor(lines); 
			List <Line> rightLines = lineProc.makeLinesRight(lines, error);

//			System.out.println(rightLines.get(0).toString());
//				Imgproc.line(img, rightLines.get(0).getStartingPoint(),rightLines.get(0).getEndingPoint(), new Scalar(255, 0, 0 ), 4);
//				Imgproc.line(img, rightLines.get(1).getStartingPoint(), rightLines.get(1).getEndingPoint(), new Scalar(0, 255, 0), 4);
//				Imgproc.line(img, rightLines.get(2).getStartingPoint(), rightLines.get(2).getEndingPoint(), new Scalar(0, 0, 255), 4);
//				Imgproc.line(img, rightLines.get(3).getStartingPoint(), rightLines.get(3).getEndingPoint(), new Scalar(0, 255, 255), 4);

			point1 = rightLines.get(0).getStartingPoint();
			point2 = rightLines.get(0).getEndingPoint();
			point3 = rightLines.get(2).getStartingPoint(); 
			point4 = rightLines.get(2).getEndingPoint();
			
			


//			point1 = new Point(scene_corners.get(0, 0));
//			point2 = new Point(scene_corners.get(1, 0));
//			point3 = new Point(scene_corners.get(2, 0));
//			point4 = new Point(scene_corners.get(3, 0));

			MatOfPoint matPoints = new MatOfPoint(point1,point2,point3,point4);


			Imgproc.fillConvexPoly(img, matPoints, new Scalar(255,255,255));
			//Core.rectangle(img, matchLoc , new Point(scene_corners.get(2, 0)), new Scalar(255,255,255),-1);
			//System.out.println("Drawing matches image...");
			MatOfDMatch goodMatches = new MatOfDMatch();
			goodMatches.fromList(goodMatchesList);

			Features2d.drawMatches(objectImage, objectKeyPoints, sceneImage, sceneKeyPoints, goodMatches, matchoutput, matchestColor, newKeypointColor, new MatOfByte(), 2);

			Highgui.imwrite("_output//outputImage.jpg", outputImage);
			Highgui.imwrite("_output//matchoutput.jpg", matchoutput);
			
			return img;
		}
		else
		{
			return null;
		}
	}

	public List<PointsWrapper> removeObject(String objName, String object, String scene, String outputImage){

		List<PointsWrapper> output = new ArrayList<>();
		int nrObjects = 0;

		processor.appendDetail("Searching " + objName + " in " + scene);

		Mat objectImage = Highgui.imread(object, Highgui.CV_LOAD_IMAGE_COLOR);
		Mat sceneImage = Highgui.imread(scene, Highgui.CV_LOAD_IMAGE_COLOR);

		Mat aux = sceneImage, aux2;
		aux2 = find_object(objectImage, aux);

		while(aux2 != null)
		{	
			nrObjects++;
			processor.appendDetail("Found one " + objName + ": (" + (int)point1.x + ", " + (int)point1.y + ") -> (" + (int)point2.x + ", " + (int)point2.y + ")");
			output.add(new PointsWrapper(point1, point2, point3, point4));
			
			if(point1.y==point2.y){
				if(point1.x<point2.x){
					point1.y += 8;
					point2.y += 8;	
				}else{
					point1.y -= 8;
					point2.y -= 8;
				}
				//Imgproc.line(aux2, point1,point2, new Scalar(0, 255, 0 ), 5);
			}
			
			if(point1.x==point2.x){
				if(point1.y<point2.y){
					point1.x -= 10;
					point2.x -= 10;
				}else{
					point1.x += 10;
					point2.x += 10;
				}
				//Imgproc.line(aux2, point1,point2, new Scalar(255, 0, 0 ), 5);
			}
			aux = aux2;
			aux2 = find_object(objectImage,aux);
		}

		processor.appendDetail("Updated in image " + outputImage);
		Highgui.imwrite(outputImage, aux);
		try {

			processor.updateImage(ImageIO.read(new File(outputImage)));

		} catch (IOException e) {
			e.printStackTrace();
		}

		processor.appendDetail("No. of objects of type " + objName + " found: " + nrObjects);


		return output;
	}


	public void detectAllObject()
	{
		processor.appendDetail("Started detecting objects.");

		String outputImage = TEMPLATE_OUTPUT_PATH;

		// get all files from template folder
		String[] templates = new File(TEMPLATE_INPUT_PATH).list();
		String path;

		Integer processStep = (int) Math.floor( 100.0 / (double)templates.length);
		Integer currentProgress = 0;
		processor.setProgress(10);

		for (int i = 0; i < templates.length; i++) {

			String template = templates[i];
			
			if(!(template.endsWith(".jpg") || template.endsWith(".png"))) continue;

			if(i == 1)
				filePath = outputImage;

			path = TEMPLATE_INPUT_PATH + "//" + template;

			if(template.contains("door")){

				List<PointsWrapper> doors = removeObject("door", path, filePath, outputImage);
				
				for(int k = 0; k < doors.size(); k++)
				{

					this.processor.imageRepresentation.addDoor(
							new Door(
									new Coordinates((float)doors.get(k).point1.x,(float)doors.get(k).point1.y),
									new Coordinates((float)doors.get(k).point2.x,(float)doors.get(k).point2.y)));
					
				}

				//add to representation
				
			}
			else if(template.contains("stair")){

				List<PointsWrapper> stairs = removeObject("stair", path, filePath, outputImage);
				for (PointsWrapper points : stairs) {
					
					List<Coordinates> corners = new ArrayList<>();
					Coordinates start 	= new Coordinates((float)points.point1.x,(float)points.point1.y);
					Coordinates end 	= new Coordinates((float)points.point2.x,(float)points.point2.y);
					Coordinates other1	= new Coordinates((float)points.point3.x,(float)points.point3.y);
					Coordinates other2	= new Coordinates((float)points.point4.x,(float)points.point4.y);
					
					corners.addAll(Arrays.asList(start, end, other1, other2));
					
					this.processor.imageRepresentation.addStair(new Stairs(start, end, corners));
				}
				//add to representation
			}
			else if(template.contains("window")){

				List<PointsWrapper> windows = removeObject("window", path, filePath, outputImage);
				//add to representation
				
				for(int k = 0; k < windows.size(); k++)
				{
					this.processor.imageRepresentation.addWindow(
							new Window(
									new Coordinates((float)windows.get(k).point1.x,(float)windows.get(k).point1.y),
									new Coordinates((float)windows.get(k).point2.x,(float)windows.get(k).point2.y)));
				}
			}
			else {
				//remove any other possible objects found there
				String name = template.substring(0, template.lastIndexOf('.'));
				removeObject(name, path, filePath, outputImage);
			}

			currentProgress = currentProgress + processStep;
			currentProgress = currentProgress > 100 ? 100 : currentProgress;
			processor.setProgress(currentProgress);
		}

		processor.appendDetail("Done. Detected all objects.");

		try {

			processor.updateImage(ImageIO.read(new File(TEMPLATE_OUTPUT_PATH)));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}