package com.imggraph;

import com.imgmodel.buildingParts.BuildingPart;

public class ConnectHelper {

	String room;
	BuildingPart bp;
	
	public void setRoom(String room){
		this.room = room;
	}
	
	public void setBuildingPart(BuildingPart b){
		this.bp = b;
	}
	
	public String getRoom(){
		return room;
	}
	
	public BuildingPart getBuildingPart(){
		return bp;
	}
	
}
