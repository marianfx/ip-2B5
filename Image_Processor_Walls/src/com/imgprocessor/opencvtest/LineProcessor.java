package com.imgprocessor.opencvtest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.imgprocessor.model.Gauss_Jordan_Elimination;
import com.imgprocessor.model.Line;
import com.imgprocessor.model.Line.Equation;
import com.imgprocessor.model.Line.line_type;
import com.imgprocessor.processor.DetectObject.PointsWrapper;
import com.imgprocessor.processor.ImageProcessorImpl;

public class LineProcessor {

	List<Line> lines;
	public static Integer drawID = 0;
	
	public static volatile Integer WALL_MIN_WIDTH;
	public static volatile Integer WALL_MAX_WIDTH;
	
	
	/**
	 * Inits a new line processor.
	 * @param input is the list of lines
	 */
	public LineProcessor(List<Line> input) {
		
		this.lines = input;
	}
	
	
	/**
	 * En-rightens lines which are slightly dizzy.
	 * @param lines is the list of line
	 * @param error is the error rate, in pixels, which represents the distance between two points so they are still considered to define the same line.
	 * @return
	 */
	public List<Line> makeLinesRight(List<Line> lines, Integer error){
		
		
		for(int i = 0; i < lines.size(); i++){
			
			Line l = lines.get(i);
			
			//horizontal line has almost same y
			if(Math.abs(l.y1 - l.y2) <= error){
				
				l.y1 = l.y2 = Math.max(l.y1, l.y2);
			}
			
			if(Math.abs(l.x1 - l.x2) <= error){
				
				l.x1 = l.x2 = Math.max(l.x1, l.x2);
			}
		}
		
		return lines;
	}
	
	
	/**
	 * Removes lines which are smaller than minSizeToKeep
	 * @param lines
	 * @param minSizeToKeep
	 * @return
	 */
	public List<Line> removeSmallLines(List<Line> lines, Integer minSizeToKeep){
		
		List<Line> output = new ArrayList<>();
		
		for(int i = 0; i < lines.size(); i++){
			
			Line line = lines.get(i);
			
			if(line.getLength() >= minSizeToKeep)
				output.add(line);
		}
		
		return output;
	}
	
	
	/**
	 * Unite lines that are approx parallel and have close-ends (basically represent almost the same line
	 * @param theLines
	 * @param limitSoTheyAreTheSame
	 * @return
	 */
	public List<Line> filterLiter(List<Line> theLines, Integer limitSoTheyAreTheSame){

		boolean found = false;
		
		theLines.sort(new Line(1, 1, 1, 1));
		
		
		for(int x = 0; x < theLines.size(); x++){

			Line l1 = theLines.get(x);
			l1.normalizeAfterXAxys();
			
			found = false;
			double TheMinimum = 1000000000.0;
			int minIndex = x;
			Line outLine = null;
			
			for(int y = x + 1; y < theLines.size(); y++)
			{
				Line l2 = theLines.get(y);
				l2.normalizeAfterXAxys();

				Point l1Start 	= new Point(l1.x1, l1.y1);
				Point l1End		= new Point(l1.x2, l1.y2);
				
				Point l2Start	= new Point(l2.x1, l2.y1);
				Point l2End		= new Point(l2.x2, l2.y2);
				
				double distanceSS 	= getDistance(l1Start, l2Start);
				double distanceEE 	= getDistance(l1End, l2End);
				double distanceSE 	= getDistance(l1Start, l2End);
				double distanceES 	= getDistance(l1End, l2Start);
				
				double minDist = Math.min(Math.min(distanceEE, distanceSS), Math.min(distanceES, distanceSE));
				
				Point maxStart = null;
				Point maxEnd   = null;

				Line otherLine = null;
				
				if(minDist == distanceSS){
					otherLine   = new Line(l1End, l2End);
				}
				else if(minDist == distanceEE){
					otherLine   = new Line(l1Start, l2Start);
				}
				else if(minDist == distanceSE){
					otherLine   = new Line(l1End, l2Start);
				}
				else if(minDist == distanceES){
					otherLine   = new Line(l1Start, l2End);
				}
				
				

				maxStart = otherLine.getStartingPoint();
				maxEnd = otherLine.getEndingPoint();
				
				double maxDist = otherLine.getLength();
				double maxLength = Math.min(getDistance(l1Start, l1End), getDistance(l2Start, l2End));
				
				
				//first unite the heads
				if(l1.isParallelWith(l2) && minDist < TheMinimum &&  minDist < limitSoTheyAreTheSame && maxDist > maxLength + 1 ){
					
//					if((Math.abs(minStart.x - minEnd.x) <= limitSoTheyAreTheSame) || Math.abs(minStart.y - minEnd.y) <= limitSoTheyAreTheSame){
					
						System.out.println("Found aprox equal lines: " + l1.toString() + "; " + l2.toString());
	
						//add the points with the biggest distance (they are parallel, and we unite the points with the biggest distance)
						outLine = new Line(maxStart, maxEnd);
						
						found = true;
						minIndex = y;
						TheMinimum = minDist;
						
//					}
					
				} 
			}
			
			if(found){

				theLines.remove(minIndex);
				if(minIndex != x)
					theLines.remove(x);
				x--;
				theLines.add(outLine);
				
			}
		}


		return theLines;
	}
	

	
	List<Line> uniteLinesLinear(Mat toDrawOn, List<Line> theLines, Integer limitSoTheyAreTheSame){

		theLines.sort(new Line(1, 1, 1, 1));
		
		
		for(int x = 0; x < theLines.size(); x++){

			Line l1 = theLines.get(x);
			l1.normalizeAfterXAxys();
			
			for(int y = x + 1; y < theLines.size(); y++)
			{
				Line l2 = theLines.get(y);
				l2.normalizeAfterXAxys();

				Point l1Start 	= new Point(l1.x1, l1.y1);
				Point l1End		= new Point(l1.x2, l1.y2);
				
				Point l2Start	= new Point(l2.x1, l2.y1);
				Point l2End		= new Point(l2.x2, l2.y2);
				
				double distanceSS 	= getDistance(l1Start, l2Start);
				double distanceEE 	= getDistance(l1End, l2End);
				double distanceSE 	= getDistance(l1Start, l2End);
				double distanceES 	= getDistance(l1End, l2Start);
				
				double minDist = Math.min(Math.min(distanceEE, distanceSS), Math.min(distanceES, distanceSE));
				
				Point maxStart = null;
				Point maxEnd   = null;
				Point minStart = null;
				Point minEnd   = null;

				Line minLine   = null;
				Line otherLine = null;
				
				if(minDist == distanceSS){
					minLine 	= new Line(l1Start, l2Start);
					otherLine   = new Line(l1End, l2End);
				}
				else if(minDist == distanceEE){
					minLine 	= new Line(l1End, l2End);
					otherLine   = new Line(l1Start, l2Start);
				}
				else if(minDist == distanceSE){
					minLine 	= new Line(l1Start, l2End);
					otherLine   = new Line(l1End, l2Start);
				}
				else if(minDist == distanceES){
					minLine 	= new Line(l1End, l2Start);
					otherLine   = new Line(l1Start, l2End);
				}
				
				

				maxStart 	= otherLine.getStartingPoint();
				maxEnd 		= otherLine.getEndingPoint();
				minStart	= minLine.getStartingPoint();
				minEnd		= minLine.getEndingPoint();
				
				double maxDist = otherLine.getLength();
				double maxLength = Math.min(getDistance(l1Start, l1End), getDistance(l2Start, l2End));
				
				
				//first unite the heads
				if((l1.isParallelWith(l2)) &&  minDist < limitSoTheyAreTheSame && maxDist > maxLength + 1 ){
					
//					if((Math.abs(minStart.x - minEnd.x) <= limitSoTheyAreTheSame) || Math.abs(minStart.y - minEnd.y) <= limitSoTheyAreTheSame){
					
						System.out.println("Found aprox equal lines: " + l1.toString() + "; " + l2.toString());
	
						Imgproc.circle(toDrawOn, minStart, 10, new Scalar(0, 0, 255), 2);
						Imgproc.circle(toDrawOn, minEnd, 10, new Scalar(0, 0, 255), 2);
						drawID++;
						Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - mins.png", toDrawOn);
						
						Imgproc.circle(toDrawOn, maxStart, 10, new Scalar(0, 0,255), 2);
						Imgproc.circle(toDrawOn, maxEnd, 10, new Scalar(0, 0, 255), 2);
						drawID++;
						Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - maxs.png", toDrawOn);
						
//					}
					
				} 
			}
		}


		return theLines;
	}
	

	public static List<Line> uniteLinesFinal(Mat toDrawOn, List<Line> theLines, Integer limitSoTheyAreTheSame, boolean draw){

		theLines.sort(new Line(1, 1, 1, 1));
		
		for(int x = 0; x < theLines.size(); x++){

			Line l1 = theLines.get(x);
			l1.normalizeAfterXAxys();
			
			for(int y = x + 1; y < theLines.size(); y++)
			{
				Line l2 = theLines.get(y);
				l2.normalizeAfterXAxys();
				
				// compute the two line's intersection
				Equation fleq = l1.getEquation();
				Equation sleq = l2.getEquation();
				
				double a = fleq.a;
				double b = fleq.b;
				double c = - fleq.c;

				double a1 = sleq.a;
				double b1 = sleq.b;
				double c1 = - sleq.c;
				
				
				double [][] mat = new double[][]{
		        	{a,b},
		        	{a1,b1}
		        	};
		        double []constants = new double[]
		        		{c,c1};
		            
		        
		        Point intersection = Gauss_Jordan_Elimination.getPoint(mat,constants);
		        
		        
		        // cases to break
//		        if(!l1.isPerpendicularWith(l2)) continue;
		        
		        if(intersection == null) continue;
		        

		        intersection = new Point((int)intersection.x, (int)intersection.y);
				

				Point l1Start 	= l1.getStartingPoint();
				Point l1End		= l1.getEndingPoint();
				
				Point l2Start	= l2.getStartingPoint();
				Point l2End		= l2.getEndingPoint();
				
				double distanceSS 	= getDistance(l1Start, l2Start);
				double distanceEE 	= getDistance(l1End, l2End);
				double distanceSE 	= getDistance(l1Start, l2End);
				double distanceES 	= getDistance(l1End, l2Start);
				
				double minDist = Math.min(Math.min(distanceEE, distanceSS), Math.min(distanceES, distanceSE));
				
				Point minStart = null;
				Point minEnd   = null;
				Point maxStart = null;
				Point maxEnd   = null;

				Line minLine   = null;
				Line otherLine = null;
				
				int l1type = 0; // 1 == update the end, 2 == update the start
				int l2type = 0;
				
				if(minDist == distanceSS){
					minLine 	= new Line(l1Start, l2Start);
					otherLine   = new Line(l1End, l2End);
					// update start on both
					l1type 		= 1;
					l2type		= 1;
				}
				else if(minDist == distanceEE){
					minLine 	= new Line(l1End, l2End);
					otherLine   = new Line(l1Start, l2Start);
					// update end on both
					l1type 		= 2;
					l2type		= 2;
				}
				else if(minDist == distanceSE){
					minLine 	= new Line(l1Start, l2End);
					otherLine   = new Line(l1End, l2Start);
					// update start on first, end on second
					l1type 		= 1;
					l2type		= 2;
				}
				else if(minDist == distanceES){
					minLine 	= new Line(l1End, l2Start);
					otherLine   = new Line(l1Start, l2End);
					// update start on first, end on second
					l1type 		= 2;
					l2type		= 1;
				}
				
				minStart 	= minLine.getStartingPoint();
				minEnd 		= minLine.getEndingPoint();
				maxStart 	= otherLine.getStartingPoint();
				maxEnd		= otherLine.getEndingPoint();
				
				minStart = new Point((int)minStart.x, (int)minStart.y);
				minEnd = new Point((int)minEnd.x, (int)minEnd.y);
				maxStart = new Point((int)maxStart.x, (int)maxStart.y);
				maxEnd = new Point((int)maxEnd.x, (int)maxEnd.y);
				
				// if already the same, do not unite
				if(minStart.x == minEnd.x && minStart.y == minEnd.y) continue;
				

				// now we know the max distance too
				double maxDist = otherLine.getLength();
				double maxLength = Math.min(getDistance(l1Start, l1End), getDistance(l2Start, l2End));
				
				
				
		        
		        if(minDist > limitSoTheyAreTheSame) continue;
		        
		        if(maxDist < maxLength + 1) continue;
		        
				
		        //draw the ends if all ok
//	        	minStart = minEnd = intersection;
//				Line nextL1 = new Line(minStart, maxStart);
//				Line nextL2 = new Line(minEnd, maxEnd);
				
				// remove the old ones, add the new ones
				if(l1type == 1)
					l1.setStartingPoint(intersection);
				else
					l1.setEndingPoint(intersection);
				
				if(l2type == 1)
					l2.setStartingPoint(intersection);
				else
					l2.setEndingPoint(intersection);
				
				if(draw){
					
	//				Imgproc.line(toDrawOn, minStart, maxStart, new Scalar(0, 255, 0), 2);
	//				Imgproc.line(toDrawOn, minEnd, maxEnd, new Scalar(0, 255, 0), 2);
					System.out.format("United: (%d, %d) -> (%d, %d) with (%d, %d) -> (%d, %d)\n", (int)minStart.x, (int)minStart.y, (int)maxStart.x, (int)maxStart.y, (int)minEnd.x, (int)minEnd.y, (int)maxEnd.x, (int)maxEnd.y);
					Imgproc.circle(toDrawOn, minStart, 10, new Scalar(255, 0, 255), 2);
					Imgproc.circle(toDrawOn, maxStart, 10, new Scalar(255, 0, 255), 2);
					drawID++;
					Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - ONE.png", toDrawOn);
					
					Imgproc.circle(toDrawOn, minEnd, 10, new Scalar(128, 255, 0), 2);
					Imgproc.circle(toDrawOn, maxEnd, 10, new Scalar(128, 255, 0), 2);
					drawID++;
					Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - TWO.png", toDrawOn);
					
	
					Imgproc.circle(toDrawOn, intersection, 10, new Scalar(255, 0, 0), 2);
					drawID++;
					Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - INTERSECT.png", toDrawOn);
				}
				
				
			}
		}


		return theLines;
	}
	
	
	public static List<Line> uniteObjectsWithWalls(Mat toDrawOn, List<Line> theLines, List<Line> theObjects, Integer limitSoTheyAreTheSame, boolean draw){

		theLines.sort(new Line());
		theObjects.sort(new Line());
		
		
		for(int i = 0; i < theObjects.size(); i++){
			
			Line object = theObjects.get(i);
			object.normalizeAfterXAxys();
			
			for(int x = 0; x < theLines.size(); x++){
	
				Line l1 = theLines.get(x);
				l1.normalizeAfterXAxys();
				
				for(int y = x + 1; y < theLines.size(); y++)
				{
					Line l2 = theLines.get(y);
					l2.normalizeAfterXAxys();
					
					
					Point intersWithFirstLine = isCloseOnAtLeastOneEnd(object, l1, limitSoTheyAreTheSame);
					Point intersWithSecndLine = isCloseOnAtLeastOneEnd(object, l2, limitSoTheyAreTheSame);
					
					//if the door is not close to BOTH of the walls, it means it's not good
					if(intersWithFirstLine == null || intersWithSecndLine == null)
						continue;
					
					object.setStartingPoint(intersWithFirstLine);
					object.setEndingPoint(intersWithSecndLine);
					
			        
					
					if(draw){
						
		//				Imgproc.line(toDrawOn, minStart, maxStart, new Scalar(0, 255, 0), 2);
		//				Imgproc.line(toDrawOn, minEnd, maxEnd, new Scalar(0, 255, 0), 2);
						System.out.format("United OBJECT into (%d, %d) -> (%d, %d)\n", (int)object.getStartingPoint().x, (int)object.getStartingPoint().y, (int)object.getEndingPoint().x, (int)object.getEndingPoint().y);
						Imgproc.circle(toDrawOn, object.getStartingPoint(), 10, new Scalar(255, 0, 255), 2);
						Imgproc.circle(toDrawOn, object.getEndingPoint(), 10, new Scalar(255, 0, 255), 2);
						drawID++;
						Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - THE OBJECT.png", toDrawOn);
						
					}
					
					//stop, because I found the walls
					y = theLines.size();
					x = theLines.size();
					
				}
			}
		}


		return theObjects;
	}
	
	
	/**
	 * Checks if the two ones are close eough, and so, returns the point from the wall closer to the door (will represent the intersection).
	 * @param door
	 * @param wall
	 * @param maxDist
	 * @return
	 */
	public static Point isCloseOnAtLeastOneEnd(Line object, Line wall, Integer maxDist){
		
		Point output = null;
		
		Point l1Start 	= object.getStartingPoint();
		Point l1End		= object.getEndingPoint();
		
		Point l2Start	= wall.getStartingPoint();
		Point l2End		= wall.getEndingPoint();
		
		double distanceSS 	= getDistance(l1Start, l2Start);
		double distanceEE 	= getDistance(l1End, l2End);
		double distanceSE 	= getDistance(l1Start, l2End);
		double distanceES 	= getDistance(l1End, l2Start);
		
		double minDist = Math.min(Math.min(distanceEE, distanceSS), Math.min(distanceES, distanceSE));
		
		if (minDist > maxDist) return null;
		
		if(minDist == distanceSS){
			output = wall.getStartingPoint();
		}
		else if(minDist == distanceEE){
			output = wall.getEndingPoint();
		}
		else if(minDist == distanceSE){
			output = wall.getEndingPoint();
		}
		else if(minDist == distanceES){
			output = wall.getStartingPoint();
		}
		
		return output;
		
	}
	
	
	
	private static double getDistance(Point p1, Point p2)
	{
		return Math.sqrt((p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y));
	}
	
	
 	public List<Line> getLinesWithThisThickness(Mat toDraw, List<Line> theLines){
		
		theLines.sort(new Line(1, 1, 1, 1));
		
		List<Line> output = new ArrayList<>();
		
		boolean removed = false;
		
		for(int x = 0; x < theLines.size(); x++){
			
			Line l1 = theLines.get(x);
			l1.normalizeAfterXAxys();
			removed = false;
			
			for(int y = x + 1; y < theLines.size(); y++)
			{
				Line l2 = theLines.get(y);
				l2.normalizeAfterXAxys();
				
				Line outLine = new Line();
				
				//get the two line's length
				double length1 = l1.getLength();
				double length2 = l2.getLength();
				
				//decide which one is longer
				Line minLine = length1 <= length2 ? new Line(l1) : new Line(l2);
				Line maxLine = length1 <= length2 ? new Line(l2) : new Line(l1);
				
//				Point l1Start 	= new Point(l1.x1, l1.y1);
//				Point l1End		= new Point(l1.x2, l1.y2);
				Point l1Mid		= new Point((l1.x2 + l1.x1) / 2.0, (l1.y2 + l1.y1) / 2.0);
//				
//				Point l2Start	= new Point(l2.x1, l2.y1);
//				Point l2End		= new Point(l2.x2, l2.y2);
				Point l2Mid		= new Point((l2.x2 + l2.x1) / 2.0, (l2.y2 + l2.y1) / 2.0);
				
				//compute the minimum distance from the shorter one to the longer one.
				
				//now hold the minimum distance
				
				//aflu proiectia ambelor capete pe dreapta maxLine
				Point minStart = minLine.getStartingPoint();
				double k = ((maxLine.y2 - maxLine.y1) * (minStart.x - maxLine.x1) - (maxLine.x2 - maxLine.x1) * (minStart.y - maxLine.y1)) / ((maxLine.y2 - maxLine.y1) * (maxLine.y2 - maxLine.y1) + (maxLine.x2 - maxLine.x1) * (maxLine.x2 - maxLine.x1) );
				double x4 = minStart.x - k * (maxLine.y2 - maxLine.y1);
				double y4 = minStart.y + k * (maxLine.x2 - maxLine.x1);
				Point minStartProj = new Point(x4, y4);
				
				Point minEnd = minLine.getEndingPoint();
				k = ((maxLine.y2 - maxLine.y1) * (minEnd.x - maxLine.x1) - (maxLine.x2 - maxLine.x1) * (minEnd.y - maxLine.y1)) / ((maxLine.y2 - maxLine.y1) * (maxLine.y2 - maxLine.y1) + (maxLine.x2 - maxLine.x1) * (maxLine.x2 - maxLine.x1) );
				x4 = minEnd.x - k * (maxLine.y2 - maxLine.y1);
				y4 = minEnd.y + k * (maxLine.x2 - maxLine.x1);
				Point minEndProj = new Point(x4, y4);
				

				
				
				Equation fleq = l1.getEquation();
				Equation sleq = l2.getEquation();
				
				double distance = length1 <= length2 ? Line.getDistanceToLine(l1Mid, sleq) : Line.getDistanceToLine(l2Mid, fleq);
				
				//iei cea mai mica din drepte.
				
				//first unite the heads
				if(l1.isNotPerpendicular(l2) && distance >= WALL_MIN_WIDTH && distance <= WALL_MAX_WIDTH ){
					
//					if((Math.abs(minStart.x - minEnd.x) <= limitSoTheyAreTheSame) || Math.abs(minStart.y - minEnd.y) <= limitSoTheyAreTheSame){
					
						//add the points with the biggest distance (they are parallel, and we unite the points with the biggest distance)
						Point midStart = null; //new Point((start1.x + p1.x) / 2.0, (start1.y + p1.y) / 2.0);
						Point midEnd   = null; //new Point((end1.x + p2.x) / 2.0, (end1.y + p2.y) / 2.0 );
						
						midStart = minLine.getStartingPoint();
						midEnd = minLine.getEndingPoint();
//						
						outLine = new Line(midStart, midEnd);
						
						//draw lines to unite
//						Imgproc.line(toDraw, l1.getStartingPoint(), l1.getEndingPoint(), new Scalar(0, 255, 0), 3);
//						Imgproc.line(toDraw, l2.getStartingPoint(), l2.getEndingPoint(), new Scalar(0, 255, 0), 3);
//						drawID++;
//						Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - first2.png", toDraw);
//						
//						//draw final
//						Imgproc.line(toDraw, midStart, midEnd, new Scalar(0, 0, 255), 5);
//						drawID++;
//						Imgcodecs.imwrite("lines/" + drawID + ". lineDetection - FINAL.png", toDraw);
						output.add(outLine);
						
						System.out.println(l1.toString() + " + " + l2.toString() + " = " + outLine.toString());
						
						if(length1 <= length2){
							theLines.remove(x);
							x--;
							removed = true;
							break;
						}
						else{
							theLines.remove(y);
							y--;
						}
						
//					}
					
				} 
				
			}
			
			if(!removed){
				theLines.remove(x);
				x--;
			}

			
		}
		
		return output;
			
	}
	
	
	//remove the ones which are onSegment and distance < 4
	
	/**
	 * Draw the specified lines on the specified image.
	 */
	public static void drawLines(List<Line> lines, Mat imageToDrawTo, Scalar color, Integer thickness, boolean drawAll, boolean drawFinal, ImageProcessorImpl processor){
		
		//go through the matrix columns
//		System.out.println("Number of lines detected: " + lines.size());
		
		for(int x = 0; x < lines.size(); x++){
			
			Line l = lines.get(x); //each cell of the matrix is a result, a pair of two points' coordinates
			
			
			//compute min and max so we can crop
			
//			System.out.println(String.format("Line [%d] detected: (%d, %d) -> (%d, %d).", x + 1, (int)l.x1, (int)l.y1, (int)l.x2, (int)l.y2));
			
			Point start = new Point(l.x1, l.y1);
			Point end   = new Point(l.x2, l.y2);
			
			//Write the data to the edge-detected-gray-3channel-image
			Imgproc.line(imageToDrawTo, start, end, color, thickness);
			
			if(drawAll)
				Imgcodecs.imwrite("lines\\line00" + x + ".png", imageToDrawTo);
			
		}
		
		if(drawFinal){

			drawID++;
			Imgcodecs.imwrite(drawID + ". lineDetection.png", imageToDrawTo);
			try {
				processor.updateImage(ImageIO.read(new File(drawID + ". lineDetection.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
