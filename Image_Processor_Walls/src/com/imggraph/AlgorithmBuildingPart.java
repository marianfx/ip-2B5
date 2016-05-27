package com.imggraph;

import com.imgmodel.buildingParts.Coordinates;

public class AlgorithmBuildingPart {

	float startX;
	float startY;
	float endX;
	float endY;
	String label;
	int nodeLabel1;
	int nodeLabel2;
	public AlgorithmBuildingPart(float startX,float startY,float endX,float endY,String label,int nodeLabel1,int nodeLabel2){
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.label = label;
		this.nodeLabel1 = nodeLabel1;
		this.nodeLabel2 = nodeLabel2;
	}

	public int getNodeLabel1(){
		return nodeLabel1;
	}

	public int getNodeLabel2(){
		return nodeLabel2;
	}

	public Coordinates getStart(){
		return new Coordinates(startX,startY);
	}

	public Coordinates getEnd(){
		return new Coordinates(endX,endY);
	}

	public String getLabel(){
		return label;
	}

	public void setStart(Coordinates c){
		this.startX = c.getX();
		this.startY = c.getY();
	}

	public void setEnd(Coordinates c){
		this.endX = c.getX();
		this.endY = c.getY();

	}
}




