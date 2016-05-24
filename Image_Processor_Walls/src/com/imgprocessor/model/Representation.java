package com.imgprocessor.model;

import java.util.ArrayList;
import java.util.List;

public class Representation {
	

	private List<Wall> walls;
	private List<Window> windows;
	private List <Door> doors;
	
	public Representation()
	{
		walls= new ArrayList<Wall>();
		windows= new ArrayList<Window>();
		doors= new ArrayList <Door>();
	}
	
	
	public void addWall(Wall a)
	{
		walls.add(a);
	}
	public void populateWalls(List<Line> lines)
	{
		
		for(int i=0;i<lines.size();i++)
		{
			this.addWall(new Wall(
					new Coordinates((float)lines.get(i).x1,(float)lines.get(i).y1),
					new Coordinates((float)lines.get(i).x2,(float)lines.get(i).y2)));
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

}
