package com.imgprocessor.opencvtest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.text.AbstractDocument.LeafElement;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.imgprocessor.model.Line;
import com.imgprocessor.model.Line.Equation;
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
			
			if(getDistance(line.getStartingPoint(), line.getEndingPoint()) >= minSizeToKeep)
				output.add(line);
		}
		
		return output;
	}
	
	
	
	List<Line> filterLiter(List<Line> theLines, Integer limitSoTheyAreTheSame){

		boolean found = false;
		
		theLines.sort(new Line(1, 1, 1, 1));
		
//		for(int x = 0; x < theLines.size(); x++){
//			System.out.println(theLines.get(x));
//		}
		
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
				
				Point minStart = null;
				Point minEnd   = null;
				Point maxStart = null;
				Point maxEnd   = null;

				Line closestLine = null;
				Line otherLine = null;
				
				if(minDist == distanceSS){
					closestLine = new Line(l1Start, l2Start);
					otherLine   = new Line(l1End, l2End);
				}
				else if(minDist == distanceEE){
					closestLine =  new Line(l1End, l2End);
					otherLine   = new Line(l1Start, l2Start);
				}
				else if(minDist == distanceSE){
					closestLine =  new Line(l1Start, l2End);
					otherLine   = new Line(l1End, l2Start);
				}
				else if(minDist == distanceES){
					closestLine =  new Line(l1End, l2Start);
					otherLine   = new Line(l1Start, l2End);
				}
				
				minStart = closestLine.getStartingPoint();
				minEnd = closestLine.getEndingPoint();
				

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
	


	private double getDistance(Point p1, Point p2)
	{
		return Math.sqrt((p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y));
	}

	
	//make a function to get thicknesses from the image
	
	public List<Line> removeSameLines(List<Line> theLines, Integer dist){
		
		for(int x = 0; x < theLines.size(); x++){

			Line l1 = theLines.get(x);
			l1.normalizeAfterXAxys();
			
			for(int y = x + 1; y < theLines.size(); y++)
			{
				Line l2 = theLines.get(y);
				l2.normalizeAfterXAxys();

				double distanceStart 	= getDistance(new Point(l1.x1, l1.y1), new Point(l2.x1, l2.y1));
				double distanceEnd 		= getDistance(new Point(l1.x2, l1.y2), new Point(l2.x2, l2.y2));

				//distanta trebuie sa fie mai mica decat 6 -> sa arate mai frumos
				if(distanceStart < dist && distanceEnd < dist)
				{
					System.out.println(distanceStart);
					System.out.println("Removed some lines that looked alike.");

					//verticalLine.add(l2);
					theLines.remove(y);
					y--;
				}
			}
		}


		return theLines;
	}
	
	
 	public List<Line> getLinesWithThisThickness(List<Line> theLines){

		
		boolean found = false;
		
		theLines.sort(new Line(1, 1, 1, 1));
		
		List<Line> output = new ArrayList<>();
		
		
		for(int x = 0; x < theLines.size(); x++){
			
			Line l1 = theLines.get(x);
			l1.normalizeAfterXAxys();
			
			found = false;
			
			for(int y = x + 1; y < theLines.size(); y++)
			{
				Line l2 = theLines.get(y);
				l2.normalizeAfterXAxys();
				
				Line outLine = new Line();
				
				//get the two line's length
				double length1 = l1.getLength();
				double length2 = l2.getLength();
				
				//decide which one is longer
				Line minLine = length1 <= length2 ? l1 : l2;
				Line maxLine = length1 <= length2 ? l2 : l1;
				
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
				Point p1 = minLine.getStartingPoint();
				double k = ((maxLine.y2 - maxLine.y1) * (p1.x - maxLine.x1) - (maxLine.x2 - maxLine.x1) * (p1.y - maxLine.y1)) / ((maxLine.y2 - maxLine.y1) * (maxLine.y2 - maxLine.y1) + (maxLine.x2 - maxLine.x1) * (maxLine.x2 - maxLine.x1) );
				double x4 = p1.x - k * (maxLine.y2 - maxLine.y1);
				double y4 = p1.y + k * (maxLine.x2 - maxLine.x1);
				Point start1 = new Point(x4, y4);
				
				Point p2 = minLine.getEndingPoint();
				k = ((maxLine.y2 - maxLine.y1) * (p2.x - maxLine.x1) - (maxLine.x2 - maxLine.x1) * (p2.y - maxLine.y1)) / ((maxLine.y2 - maxLine.y1) * (maxLine.y2 - maxLine.y1) + (maxLine.x2 - maxLine.x1) * (maxLine.x2 - maxLine.x1) );
				x4 = p2.x - k * (maxLine.y2 - maxLine.y1);
				y4 = p2.y + k * (maxLine.x2 - maxLine.x1);
				Point end1 = new Point(x4, y4);
				

				double dist1 = getDistance(p1, start1);
				double dist2 = getDistance(p2, end1);
				double mindist = Math.min(dist1,  dist2);
				
				
				Equation fleq = l1.getEquation();
				Equation sleq = l2.getEquation();
				
				double distance = length1 < length2 ? Line.getDistanceToLine(l1Mid, sleq) : Line.getDistanceToLine(l2Mid, fleq);
				
				//iei cea mai mica din drepte.
				
				//first unite the heads
				// 15 - 25
				// 6 - 50
				if(l1.isNotPerpendicular(l2) && distance >= WALL_MIN_WIDTH && distance <= WALL_MAX_WIDTH ){
					
//					if((Math.abs(minStart.x - minEnd.x) <= limitSoTheyAreTheSame) || Math.abs(minStart.y - minEnd.y) <= limitSoTheyAreTheSame){
					
						//add the points with the biggest distance (they are parallel, and we unite the points with the biggest distance)
						Point midStart = new Point((start1.x + p1.x) / 2.0, (start1.y + p1.y) / 2.0);
						Point midEnd   = new Point((end1.x + p2.x) / 2.0, (end1.y + p2.y) / 2.0 );
						
						midStart = minLine.getStartingPoint();
						midEnd = minLine.getEndingPoint();
//						
						outLine = new Line(midStart, midEnd);
						output.add(outLine);
						
						System.out.println(l1.toString() + " + " + l2.toString() + " = " + outLine.toString());
						
						found = true;
						theLines.remove(y);
						y--;
						
//					}
					
				} 
				
			}
			
			if(found){

				theLines.remove(x);
				x--;
			}
			
		}
		
		return output;
			
	}
	
 	public List<Line> getLinesWithThisThickness2(List<Line> theLines, int thickness){
		
		
		List<Line> finalLines = new ArrayList<>();
		boolean found;
		
		for(int i = 0; i < theLines.size(); i++){
			
			Line l1 = theLines.get(i);
			found = false;
			
			for (int j = i + 1; j < theLines.size(); j++) {
				
				Line l2 = theLines.get(j);
				
				// two variants - strt with the most thick ones, eliminate them and then continue (verif = >= thickness)
				
				if(Math.abs(l1.y1 - l1.y2) <= 100 && Math.abs(l2.y1 - l2.y2) <= 100 
						//&& (Math.abs(l1.x1 - l2.x1) <= 100 && Math.abs(l1.x2 - l2.x2) <= 100)
						){// they might have their corners slightly splitted, error rate ~= 10, but they are the same
					//horizontal lines
					
					Double xx1 = (l1.x1 + l1.x2) / 2.0;
					Double xy1 = l1.y1;
					
					Double xx2 = (l2.x1 + l2.x2) / 2.0;
					Double xy2 = l2.y1;

					double distance 	= getDistance(new Point(xx1, xy1), new Point(xx2, xy2));
					
					if( distance >= (thickness - 4) && distance <= (thickness + 4)){
						//in the required limit, with an +-2 error rate

						Double x1 = Math.min(l1.x1, l2.x1);
						Double y1 = (l1.y1 + l2.y1) / 2.0;
						Double x2 = Math.max(l1.x2, l2.x2);
						Double y2 = (l1.y1 + l2.y1) / 2.0;
						
						Line line = new Line(x1, x2, y1, y2);
						finalLines.add(line);
						
						theLines.remove(j);
						j--;
						found = true;
						
					}
				}
				else if(Math.abs(l1.x1 - l1.x2) <= 100 && Math.abs(l2.x1 - l2.x2) <= 100
							//&& (Math.abs(l1.y1 - l2.y1) <= 100) && Math.abs(l1.y2 - l2.y2) <= 100 
							){
					//vertical lines
					
					Double xy1 = (l1.y1 + l1.y2) / 2.0;
					Double xx1 = l1.x1;
					
					Double xy2 = (l2.y1 + l2.y2) / 2.0;
					Double xx2 = l2.x1;

					double distance 	= getDistance(new Point(xx1, xy1), new Point(xx2, xy2));
					
					if( distance >= (thickness - 4) && distance <= (thickness + 4)){
							//in the required limit, with an +-2 error rate
						Double x1 = (l1.x1 + l2.x1) / 2.0;
						Double y1 = Math.min(l1.y1, l2.y1);
						Double x2 = (l1.x1 + l2.x1) / 2.0;
						Double y2 = Math.max(l1.y2, l2.y2);
						
						Line line = new Line(x1, x2, y1, y2);
						finalLines.add(line);

						theLines.remove(j);
						j--;
						found = true;
					}
					
				}
				
			}
			
			if(found){

				theLines.remove(i);
				i--;
			}
			
		}
		
		System.out.println("Big lines nr: " + finalLines.size());
		
		return finalLines;
			
	}
 	
	public List<Line> getDominantLengths(List<Line> theLines ){
		

		theLines.sort(new Line(1, 1, 1, 1));
		
		List<Line> output = new ArrayList<>();
		
		Map<Integer, Integer> sizes = new HashMap<Integer, Integer>();
		
		for(int x = 0; x < theLines.size(); x++){
			
			Line l1 = theLines.get(x);
			l1.normalizeAfterXAxys();
			
			
			for(int y = x + 1; y < theLines.size(); y++){
				
				Line l2 = theLines.get(y);
				l2.normalizeAfterXAxys();
				
				if(l1.isParallelWith(l2)){

					Point l1Mid		= new Point((l1.x1 + l1.x2) / 2.0, (l1.y1 + l1.y2) / 2.0);
					Point l2Mid		= new Point((l2.x1 + l2.x2) / 2.0, (l2.y1 + l2.y2) / 2.0);
					int midDistance	= (int)getDistance(l1Mid, l2Mid);
					
					if(midDistance < 50 && sizes.containsKey(midDistance)){
						
						Integer oldNrOfApparitions = sizes.get(midDistance);
						
						sizes.put(midDistance, oldNrOfApparitions + 1); 
					}
					else
						sizes.put(midDistance, 1);
				}
			}
			
		}

	    List<Map.Entry<Integer, Integer>> entries = new ArrayList<Map.Entry<Integer, Integer>>();
	    
		for (Map.Entry<Integer, Integer> entry : sizes.entrySet()) {
			entries.add(entry);
		}

	    
	    Collections.sort(entries, new Comparator<Map.Entry<Integer, Integer>>() {
	    	
	        public int compare(Map.Entry<Integer, Integer> a,
	                Map.Entry<Integer, Integer> b) {
	        	
	            return - a.getValue().compareTo(b.getValue());
	        }
	    });
		
	    for (int i = 0; i < 10; i++) {
	    	
			Map.Entry<Integer, Integer> entry = entries.get(i);
	    	System.out.println(entry.getKey() + " --> " + entry.getValue());
		}
		
		return output;
	}
	
	
	//remove the ones which are onSegment and distance < 4
	
	/**
	 * Draw the specified lines on the specified image.
	 */
	public void drawLines(List<Line> lines, Mat imageToDrawTo, Scalar color, Integer thickness, boolean drawAll, boolean drawFinal, ImageProcessorImpl processor){
		
		//go through the matrix columns
		System.out.println("Number of lines detected: " + lines.size());
		
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
