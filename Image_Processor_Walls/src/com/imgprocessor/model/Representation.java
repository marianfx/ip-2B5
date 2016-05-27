package com.imgprocessor.model;

import java.util.ArrayList;
import java.util.List;

import com.imgmodel.buildingParts.Coordinates;
import com.imgmodel.buildingParts.Door;
import com.imgmodel.buildingParts.Stairs;
import com.imgmodel.buildingParts.Wall;
import com.imgmodel.buildingParts.Window;

public class Representation {
	

	private List<Wall> 		walls;
	private List<Window> 	windows;
	private List <Door> 	doors;
	private List<Stairs> 	stairs;
	
	
	public Representation()
	{
		walls	= new ArrayList<Wall>();
		windows	= new ArrayList<Window>();
		doors	= new ArrayList <Door>();
		stairs	= new ArrayList<Stairs>();
	}
	
	
	public void addWall(Wall a)
	{
		walls.add(a);
	}
	
	
	public void populateWalls(List<Line> lines)
	{
		
		for(int i = 0; i < lines.size(); i++)
		{
			this.addWall(new Wall(
					new Coordinates((float)lines.get(i).x1, (float)lines.get(i).y1),
					new Coordinates((float)lines.get(i).x2, (float)lines.get(i).y2)));
		}
		
	}
	
	
	public void addWindow(Window w)
	{
		windows.add(w);
	}
	
	
	public void addDoor(Door d)
	{
		doors.add(d);
	}
	
	
	public void addStair(Stairs s){
		stairs.add(s);
	}
	
	public List<Wall> getWalls() {
		return walls;
	}
	
	
	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}
	
	
	public List<Window> getWindows() {
		return windows;
	}
	
	
	public void setWindows(List<Window> windows) {
		this.windows = windows;
	}
	
	
	public List <Door> getDoors() {
		return doors;
	}
	
	
	public void setDoors(List <Door> doors) {
		this.doors = doors;
	}
	
	public List <Stairs> getStairs() {
		return stairs;
	}
	
	
	public void setStairs(List <Stairs> stairs) {
		this.stairs = stairs;
	}
	
	public void clearWindows(){
		windows.clear();
		windows = new ArrayList<>();
	}
	
	public void clearDoors(){
		doors.clear();
		doors = new ArrayList<>();
	}

}
