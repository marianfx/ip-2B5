package com.imgmodel.graphModel;

import com.imgmodel.buildingParts.Door;

import java.util.ArrayList;
import java.util.List;


public class GraphEdge {


  private List<Room> nodes;

  private Door door;

  public GraphEdge(){
    this.nodes=new ArrayList<>();
    this.door=new Door();
  }
    public GraphEdge( Room r1, Room r2, Door d ){
        this.nodes=new ArrayList<>();
        this.nodes.add(r1);
        this.nodes.add(r2);
        this.door=d;
    }



  public Door getDoor() {
    return door;
  }

  public void setDoor(Door door) {
    this.door = door;
  }

  public List<Room> getNodes() {
    return nodes;
  }

  public void setNodes(List<Room> nodes) {
    this.nodes = nodes;
  }
}