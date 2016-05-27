package com.imggraph;

import java.util.ArrayList;
import java.util.List;

import com.imgmodel.buildingParts.BuildingPart;
import com.imgmodel.buildingParts.Door;
import com.imgmodel.graphModel.GraphEdge;
import com.imgmodel.graphModel.Room;

public class Connector {

	List<Room> rooms 				= new ArrayList<Room>();
	List<BuildingPart> bp 			= new ArrayList<BuildingPart>();
	List<ConnectHelper> c 			= new ArrayList<ConnectHelper>();
	List<Door> door 				= new ArrayList<Door>();
	static List<GraphEdge> gEdge 	= new ArrayList<GraphEdge>();
	
	
	public Connector(List<Room> rooms){
		this.rooms = rooms;
	}
	
	public void connect(){
		
		for(int i = 0; i < rooms.size(); i++){
			for(int j = 0; j < rooms.get(i).getParts().size(); j++){
				
				if(rooms.get(i).getParts().get(j).toString().equals("door")){
					
					ConnectHelper ch = new ConnectHelper();
					ch.setBuildingPart(rooms.get(i).getParts().get(j));
					ch.setRoom(String.valueOf(i));
					c.add(ch);
				}
			}
		}
		
		/*for(int i=0;i<c.size()-1;i++){
			List<Room> adjacentRooms = new ArrayList<Room>();
			for(int j=i+1;j<c.size();j++){
				if(c.get(i).getBuildingPart().equals(c.get(j).getBuildingPart())){
					adjacentRooms.add(rooms.get(Integer.parseInt((c.get(j).room))));		
				}
			}
			rooms.get(i).setAdjacentRooms(adjacentRooms);
		}*/
		
		System.out.println("\nCamere in total:  " + c.size() + "\n" );
		System.out.println(rooms.size());
		List<Room> adjacentRooms = new ArrayList<Room>();
		
		for(int i = 0; i < rooms.size(); i++){//Luam fiecare camera
			
			System.out.print("Camerele adiacente cu camera " + String.valueOf(i+1) + ":") ;
			
			for(int j = 0; j < rooms.get(i).getParts().size(); j++){
				
				adjacentRooms = new ArrayList<Room>();
				
				if(rooms.get(i).getParts().get(j).toString().equals("door")){//Am gasit usa
					
					for(int k = 0; k < c.size();k++){//Cautam lista de usi
						//System.out.println(i + " " + c.get(k).getRoom());
						if(i!=((Integer.parseInt(c.get(k).getRoom())))){//Verificam sa nu fie in aceeasi camera
							/*System.out.println(rooms.get(i).getParts().get(j).getStart().getX() + " " + rooms.get(i).getParts().get(j).getStart().getY() + " " +
									rooms.get(i).getParts().get(j).getEnd().getX() + " " + rooms.get(i).getParts().get(j).getEnd().getY() + "-->" + 
									c.get(k).getBuildingPart().getStart().getX() + " " + c.get(k).getBuildingPart().getStart().getY() + " " +
									c.get(k).getBuildingPart().getEnd().getX() + " " + c.get(k).getBuildingPart().getEnd().getY());*/
							if(rooms.get(i).getParts().get(j).getStart().getX()==c.get(k).getBuildingPart().getStart().getX() && 
									rooms.get(i).getParts().get(j).getStart().getY() == c.get(k).getBuildingPart().getStart().getY() &&
									rooms.get(i).getParts().get(j).getEnd().getX() == c.get(k).getBuildingPart().getEnd().getX() &&
									rooms.get(i).getParts().get(j).getEnd().getY() == c.get(k).getBuildingPart().getEnd().getY()){
							//if(rooms.get(i).getParts().get(j).equals(c.get(k).getBuildingPart())){//Am gasit doua usi cu aceleasi coordonate
//								System.out.print("   " + rooms.get(k).getName() + " ");
								
								Room roo = rooms.get(Integer.parseInt((c.get(k).room)));
								System.out.println("   " + roo.getName());
								adjacentRooms.add(roo);	
								door.add(new Door(rooms.get(i).getParts().get(j).getStart(),rooms.get(i).getParts().get(j).getEnd()));
							}
						}
					}
				}
			}
			System.out.println("");
			rooms.get(i).setAdjacentRooms(adjacentRooms);
		}
		
		
		for(int i=0;i<rooms.size();i++){
			System.out.print("Camera " + rooms.get(i).getName() + ":");
			List<Room> mRooms = rooms.get(i).getAdjacentRooms();
			for(int j=0;j<mRooms.size();j++){
				System.out.print(mRooms.get(j).getName() + " ");
			}
			System.out.println("");
			
		}
		
		for(int i=0;i<rooms.size();i++){
			List<Room> mRooms = rooms.get(i).getAdjacentRooms();
			for(int j=0;j<mRooms.size();j++){
				//System.out.print(mRooms.get(j).getName() + " ");
				gEdge.add(new GraphEdge(rooms.get(i),mRooms.get(j),door.get(i)));
			}
			
		}
		for(int i=0;i<gEdge.size();i++){
			System.out.print("Edge:");
			for(int j = 0;j<gEdge.get(i).getNodes().size();j++){
				System.out.print(gEdge.get(i).getNodes().get(j).getName() + " ");
			}
			System.out.println(" ");
		}
		
		System.out.print("Rooms:");
		for(int i=0;i<rooms.size();i++){
			System.out.print(rooms.get(i).getName() + " ");
		}
		
		System.out.println("");
	}
	
	public List<Room> getRooms (){
		return rooms;
	}
	
	public List<Door> getDoors(){
		return door;
	}
	
	public List<GraphEdge> getGraphEdges(){
		return gEdge;
	}
	
	
	
	
}
