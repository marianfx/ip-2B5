package com.imgmodel.graphModel;

import com.imgmodel.buildingParts.BuildingPart;
import com.imgmodel.buildingParts.Coordinates;
import com.imgmodel.buildingParts.Door;
import com.imgmodel.buildingParts.Window;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private List<BuildingPart> parts;

    private List<Room> adjacentRooms;

    private String name;

    private List<Coordinates> corners;

    private String level;

    private boolean visited;

    public Room(){
        this.parts=new ArrayList<>();
        this.adjacentRooms=new ArrayList<>();
        this.name="";
        this.level="";
        this.corners=new ArrayList<>();
        this.visited=false;
    }

    public Room(String name, String level) {
        this.name = name;
        this.level = level;
        this.parts = new ArrayList<>();
        this.adjacentRooms = new ArrayList<>();
        this.corners = new ArrayList<>();
        this.visited=false;
    }

    public List<BuildingPart> getParts() {
        return parts;
    }

    public void setParts(List<BuildingPart> parts) {
        this.parts = parts;
    }

    public List<Room> getAdjacentRooms() {
        return adjacentRooms;
    }

    public void setAdjacentRooms(List<Room> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coordinates> getCorners() {
        return corners;
    }

    public void setCorners(List<Coordinates> corners) {
        this.corners = corners;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void addBuildingPart(BuildingPart part) {
        this.parts.add(part);
    }

    public void deleteBuildingPart(BuildingPart part) {
        this.parts.remove(part);
    }

    public List<Door> getDoors() {
        List<Door> doors = new ArrayList<>();
        for (BuildingPart b : parts) {
            if (b instanceof Door) {
                doors.add((Door) b);
            }
        }
        return doors;
    }

    public List<Window> getWindows() {
        List<Window> doors = new ArrayList<>();
        for (BuildingPart b : parts) {
            if (b instanceof Door) {
                doors.add((Window) b);
            }
        }
        return doors;
    }

    public String toString(){
        return this.name+" "+this.level;
    }

    public String roomInfo(){
        String info=this.toString()+":\nComponents:\n";
        for (Coordinates c:corners){
            info=info+"Corner: "+c.toString()+"\n";
        }
        for (BuildingPart b:parts){
            info=info+b.toString()+"\n";
        }
        return info;

    }

    public Room getUnvisitedChildNode(){
        for (Room r:adjacentRooms){
            if (!r.isVisited()) return r;
        }
        return null;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public float triangleArea(Coordinates a,Coordinates b){
        float determinant=a.getX()*b.getY()-b.getX()*a.getY();
        determinant=Math.abs(determinant);
        return determinant/2;
    }

    public BuildingPart nextBuidingPart(BuildingPart b){
        for (BuildingPart part:parts){
            if (b.getEnd().equals(part.getStart())  ) return part;
        }
        return null;
    }

    public double area(){
            double area=0;
            List <Coordinates> sortedCoordinates=new ArrayList<>();
            BuildingPart startingPart=parts.get(0);
            BuildingPart currentPart=startingPart;
            BuildingPart nextPart=nextBuidingPart(startingPart);
            while (!startingPart.equals(nextPart)){
                for (Coordinates c:corners){
                    if (currentPart.getStart().equals(c)){
                        sortedCoordinates.add(c);
                    }
                }
                currentPart=nextPart;
                nextPart=nextBuidingPart(currentPart);

            }

        for (int i=0;i<sortedCoordinates.size()-1;i++){
            area=area+triangleArea(sortedCoordinates.get(i),sortedCoordinates.get(i+1));
        }

        return area;
    }
}