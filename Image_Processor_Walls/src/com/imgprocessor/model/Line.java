package com.imgprocessor.model;

import java.util.Comparator;

import org.opencv.core.Point;



public class Line implements Comparator<Line> {

	public enum line_type {WALL, DOOR, WINDOW};
	
	public double x1, y1;
	public double x2, y2;
	public line_type type;
	public boolean marked = false;
	
	public class Equation{
		
		public double a;
		public double b;
		public double c;
		
		public Equation(Point p1, Point p2) {
			// TODO Auto-generated constructor stub
			a = p1.y - p2.y;
			b = p2.x - p1.x;
			c = (p1.x * p2.y) - (p2.x * p1.y);
		}
	}
	
	
	public Line(double x1, double x2, double y1, double y2){
		
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	

	public Line(Point p1, Point p2){
		
		this.x1 = p1.x;
		this.y1 = p1.y;
		this.x2 = p2.x;
		this.y2 = p2.y;
	}
	
	public Line(Line toCopy){
		this(toCopy.getStartingPoint(), toCopy.getEndingPoint());
	}
	
	public Line(){
		
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
	}
	
	
	
	public Point getStartingPoint(){
		
		return new Point(this.x1, this.y1);
	}
	
	public void setStartingPoint(Point p){
		
		this.x1 = p.x;
		this.y1 = p.y;
	}
	
	public Point getEndingPoint(){
		
		return new Point(this.x2, this.y2);
	}

	public void setEndingPoint(Point p){
		
		this.x2 = p.x;
		this.y2 = p.y;
	}
	
	
	public Equation getEquation(){
		
		return new Equation(getStartingPoint(), getEndingPoint());
	}
	
	public static double getDistanceToLine(Point p, Equation line){
		
		return (Math.abs(line.a * p.x + line.b * p.y + line.c) / Math.sqrt(line.a * line.a + line.b * line.b));
	}
	
	
	public void normalizeAfterXAxys(){
		
		Point sstart = new Point(x1, y1);
		Point send   = new Point (x2, y2);
		
		Point start, end;
		
		start = sstart.x < send.x ? sstart : (sstart.x == send.x ? (sstart.y < send.y ? sstart : send) : send);
		end = sstart.x > send.x ? sstart : (sstart.x == send.x ? (sstart.y > send.y ? sstart : send) : send);
		
		this.x1 = start.x;
		this.y1 = start.y;
		this.x2 = end.x;
		this.y2 = end.y;
	}
	
	
	public static Point getSmallestPointAfterXAxis(Point p1, Point p2){
		
		return p1.x < p2.x ? p1 : (p1.x == p2.x ? (p1.y < p2.y ? p1 : p2) : p2);
	}
	
	
	public static Point getLargestPointAfterXAxis(Point p1, Point p2){
		
		return p1.x > p2.x ? p1 : (p1.x == p2.x ? (p1.y > p2.y ? p1 : p2) : p2);
	}
	
	
	
	public boolean isParallelWith(Line l2){
		
		if(this.equals(l2)) return false;
		
		if(this.x1 == this.x2 && l2.x1 == l2.x2) {
			// vertical both
			return true;
		}
		
		double panta1 = (this.y2 - this.y1)/(this.x2 - this.x1);
		double panta2 = (l2.y2 - l2.y1)/(l2.x2 - l2.x1);
		
		if (panta1 == panta2){
			
			return true;
		}
		
		return false;
	}
	
	
	public boolean isPerpendicularWith(Line l2){
		
		if(this.equals(l2)) return false;
		
		if(this.isParallelWith(l2)) return false;
		
		double panta1 = (this.y2 - this.y1)/(this.x2 - this.x1);
		double panta2 = (l2.y2 - l2.y1)/(l2.x2 - l2.x1);
		
		if (panta1 * panta2 == -1){
			
			return true;
		}
		
		return false;
	}
	
	
	public boolean isNotPerpendicular(Line l2){
		
		if(this.equals(l2))
			return true;
		
		if(this.x1 == this.x2 && l2.x1 == l2.x2) {
			// vertical both
			return true;
		}
		
		double panta1 = (this.y2 - this.y1)/(this.x2 - this.x1);
		double panta2 = (l2.y2 - l2.y1)/(l2.x2 - l2.x1);
		
		if(panta1 * panta2 == -1)
			return false;
		
		double tang = Math.abs((panta1 - panta2 ) / (1 + panta1 * panta2));
		
		if(tang > 0.0874 ) return false;
		
		return true;
		
	}
	
	
	public double getLength()
	{
		Point p1 = getStartingPoint();
		Point p2 = getEndingPoint();
		
		return Math.sqrt((p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y));
	}
	
	private double getDistance(Point p1, Point p2)
	{
		return Math.sqrt((p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y));
	}
	
	
	Line isAproxSameWith(Line l2){
		
		Point thisStart 	= new Point(this.x1, this.y1);
		Point thisEnd		= new Point(this.x2, this.y2);
		
		Point l2Start		= new Point(l2.x1, l2.y1);
		Point l2End			= new Point(l2.x2, l2.y2);
		
		double length1 = this.getLength();
		double length2 = l2.getLength();
		
		// if P0 in first line, P1 in second line, L1 = length of first, L2 = length of second => (P0, P1) ~= L1 + L1 (din inegalitatea triunghiului) triungiul se contopeste intr-o singura linie.
		// Vom testa cu o precizie mica.
		
		double distance1 = getDistance(thisStart, l2Start);
		double distance2 = getDistance(thisStart, l2End);
		double distance3 = getDistance(thisEnd, l2Start);
		double distance4 = getDistance(thisEnd, l2End);
		
		
		double maxDist = Math.max(Math.max(distance1, distance2), Math.max(distance3, distance4));

		//l1 should have the endpoints wiith the biggest distance
		Line newLine = null;
		
		if(maxDist == distance1)
			newLine =  new Line(thisStart, l2Start);
		else if(maxDist == distance2)
			newLine =  new Line(thisStart, l2End);
		else if(maxDist == distance3)
			newLine =  new Line(thisEnd, l2Start);
		else if(maxDist == distance4)
			newLine =  new Line(thisEnd, l2End);
		
		if(Math.abs(maxDist - length1 - length2) < 1){
			//aproximate same
			System.out.println("Found aprox lines: " + this.toString() + "; " + l2.toString());
			System.out.println("NEW LINE: " + newLine.toString());
			return newLine;
		}
		
		return null;
	}
	
	
	public Double getPanta(){
		
		if(x2 - x1 == 0) return null;
		
		return ((y2 - y1) / (x2 - x2));
	}
	
	
	/**
	 * Given three collinear points, check wether q is on the pr segment.
	 * @param p
	 * @param q
	 * @param r
	 * @return
	 */
	public static boolean onSegment(Point p, Point q, Point r){
		
		if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
		        q.y <=Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
		       return true;
		 
	    return false;
	}
	
	
	/**
	 * Given three points p,q,r, find their orientation.
	 * @param p
	 * @param q
	 * @param r
	 * @return 	0 -> p,q,r are collinear
	 * 			1 -> clockwise
	 * 			2 -> counter clockwise
	 */
	public static Integer orientation(Point p, Point q, Point r){
		
		int val = (int) ((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y));
	 
	    if (val == 0) return 0;  // colinear
	 
	    return (val > 0)? 1: 2; // clock or counterclock wise
	}
	
	
	/**
	 * Check if the two line segments intersect.
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static boolean doIntersect(Line l1, Line l2){
		
		Point p1 = new Point(l1.x1, l1.y1);
		Point q1 = new Point(l1.x2, l1.y2);

		Point p2 = new Point(l2.x1, l2.y1);
		Point q2 = new Point(l2.x2, l2.y2);
		
		 // Find the four orientations needed for general and
	    // special cases
	    int o1 = orientation(p1, q1, p2);
	    int o2 = orientation(p1, q1, q2);
	    int o3 = orientation(p2, q2, p1);
	    int o4 = orientation(p2, q2, q1);
	 
	    // General case
	    if (o1 != o2 && o3 != o4)
	        return true;
	 
	    // Special Cases
	    // p1, q1 and p2 are colinear and p2 lies on segment p1q1
	    if (o1 == 0 && onSegment(p1, p2, q1)) return true;
	 
	    // p1, q1 and p2 are colinear and q2 lies on segment p1q1
	    if (o2 == 0 && onSegment(p1, q2, q1)) return true;
	 
	    // p2, q2 and p1 are colinear and p1 lies on segment p2q2
	    if (o3 == 0 && onSegment(p2, p1, q2)) return true;
	 
	     // p2, q2 and q1 are colinear and q1 lies on segment p2q2
	    if (o4 == 0 && onSegment(p2, q1, q2)) return true;
	 
	    return false; // Doesn't fall in any of the above cases
	}
	
	
	
	@Override
	public boolean equals(Object obj) {

		if(!(obj instanceof Line))
			return false;
		
		Line l = (Line)obj;
		return (l.x1 == this.x1 && l.x2 == this.x2 && l.y1 == this.y1 && l.y2 == this.y2);
	}
	
	@Override
	public String toString() {
		
		return "(" + (int)x1 + "," + (int)y1 + ") -> (" + (int)x2 + "," +(int) y2 + ")" + " -->" + (int)this.getLength();
	}
	

	@Override
	public int compare(Line o1, Line o2) {
		
		return (int) (o2.getLength() - o1.getLength());
	}

}
