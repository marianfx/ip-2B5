package com.imgmodel.graphModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.imgmodel.buildingParts.BuildingPart;
import com.imgmodel.buildingParts.Coordinates;
import com.imgmodel.buildingParts.Door;

public class Graph {

    private List<Room> nodes;

    private List<GraphEdge> edges;



  public Graph(List<Room> nodes, List<GraphEdge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }
  public Graph(){
    this.nodes=new ArrayList<>();
    this.edges=new ArrayList<>();
  }


    public void addNode(Room r) {
    this.nodes.add(r);
  }

  public void deleteNode( Room r) {
    this.nodes.remove(r);
  }

  public void addEdge( GraphEdge e) {
    this.edges.add(e);
  }

  public void deleteEdge( GraphEdge e) {
    this.edges.remove(e);
  }

  public List<Room> getNodes() {
    return nodes;
  }

  public void setNodes(List<Room> nodes) {
    this.nodes = nodes;
  }

  public List<GraphEdge> getEdges() {
    return edges;
  }

  public void setEdges(List<GraphEdge> edges) {
    this.edges = edges;
  }

  public List<String> getListOfRoomsString(){
    List<String> rooms=new ArrayList<>();
    for (Room r:this.nodes){
      rooms.add(r.toString());
    }
    return rooms;
  }

    public List<Room> bfs(Room rootNode)
    {
        List <Room> connectedPart=new ArrayList<>();
        Queue<Room> queue = new LinkedList<Room>();
        queue.add(rootNode);
        connectedPart.add(rootNode);
        rootNode.setVisited(true);
        while(!queue.isEmpty()) {
            Room node = (Room)queue.remove();
            Room child=null;
            while((child=node.getUnvisitedChildNode())!=null) {
                child.setVisited(true);
                connectedPart.add(child);
                queue.add(child);
            }
        }
        return connectedPart;

    }

    public int splitGraph(){
        int nrParts=0;
        List<Room> connectedPart= new ArrayList<>();
        connectedPart=bfs(this.nodes.get(0));
        nrParts++;
        if (connectedPart.size()!=this.nodes.size()){
            List<Room> part=new ArrayList<>();
            for (Room r:nodes){
                if (!r.isVisited()){
                    part.add(r);
                }
            }
            this.nodes=connectedPart;
            while(!part.isEmpty()){
                List<Room> newPart=bfs(part.get(0));
                nrParts++;
                Graph g=new Graph();
                g.setNodes(newPart);
                for (Room r:part){
                    if (r.isVisited()){
                        part.remove(r);
                    }
                }
            }
        }
        return nrParts;
    }


    public boolean splitRoom(Room r,int nrParts){
        this.nodes.remove(r);
        double intitialArea=r.area();
        double newArea=0;
        for (int i=0;i<nrParts;i++){
            Room newRoom= new Room(r.getName()+"_"+(i+1),r.getLevel());
            this.nodes.add(newRoom);
            newArea=newArea+newRoom.area();
        }
        if (intitialArea!=newArea) return  false;

        return true;
    }

    public void removeEdge(Door d){
        for (GraphEdge g:this.getEdges()){
            if (g.getDoor().equals(d)){
                this.getEdges().remove(g);
                for (Room r : g.getNodes()){
                    r.deleteBuildingPart(d);
                }
                return;
            }
        }
    }

    public boolean mergeRooms(Room r1, Room r2, String newName){
        boolean ableToMerge=false;
        List <BuildingPart>buildingParts=new ArrayList<>();
        for (BuildingPart b1: r1.getParts()){
            if (r2.getParts().contains(b1)){
                ableToMerge=true;
               buildingParts.add(b1);
            }
        }
        for (BuildingPart b: buildingParts){
            r2.getParts().remove(b);
            r1.getParts().remove(b);

        }

        if (ableToMerge) {
            List <Coordinates> coordinatesList=new ArrayList<>();
            for (Coordinates c1 : r1.getCorners()) {
                if (r2.getCorners().contains(c1)) {
                    coordinatesList.add(c1);
                }
            }

            for (Coordinates c: coordinatesList){
                r2.getCorners().remove(c);
                r1.getCorners().remove(c);
            }
            r1.getAdjacentRooms().remove(r2);
            r2.getAdjacentRooms().remove(r1);
            for (Room r :r1.getAdjacentRooms()){
                if (r2.getAdjacentRooms().contains(r)){
                    r2.getAdjacentRooms().remove(r);
                }
            }
            Room newRoom=new Room(newName, r1.getLevel());
            newRoom.setParts(r1.getParts());
            newRoom.getParts().addAll(r2.getParts());
            newRoom.setCorners(r1.getCorners());
            newRoom.getCorners().addAll(r2.getCorners());
            newRoom.setAdjacentRooms(r1.getAdjacentRooms());
            newRoom.getAdjacentRooms().addAll(r2.getAdjacentRooms());
            for (Room r:this.getNodes()){
                if (r.getAdjacentRooms().contains(r1)){
                    r.getAdjacentRooms().remove(r1);
                    r.getAdjacentRooms().add(newRoom);
                }
                if (r.getAdjacentRooms().contains(r2)){
                    r.getAdjacentRooms().remove(r2);
                    r.getAdjacentRooms().add(newRoom);
                }
            }
            for (GraphEdge g: this.getEdges()){
                if (g.getNodes().contains(r1)){
                    g.getNodes().remove(r1);
                    g.getNodes().add(newRoom);
                }
                if (g.getNodes().contains(r2)){
                    g.getNodes().remove(r2);
                    g.getNodes().add(newRoom);
                }
            }
            this.getNodes().remove(r1);
            this.getNodes().remove(r2);
            this.getNodes().add(newRoom);
        }

        return ableToMerge;
    }


}