/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tifuivali
 * 
 * Aceasta tine toate obiectele pe care le procesam noi ..cand gasim un obiect il adaugam il sista aici.
 * un obiect de acest tip va fi returnat de api. cealalta echipa doar se va folosi de aceste date ca sa creeze
 * camere and others.
 */
public class ImageProcessedRepresentation {
    
    private List<Door> listDoors = null;
    private List<Stairs> listStairs = null;
    private List<Wall> listWalls = null;
    private List<Window> listWindows = null;
    
    /**
     * Create a ImageProcessedRepresentation model that contains all pats of building.
     * A list with doors , a list with Walls ... .
     */
    public ImageProcessedRepresentation()
    {
        this.listDoors=new ArrayList<>();
        this.listStairs=new ArrayList<>();
        this.listWalls=new ArrayList<>();
        this.listWindows=new ArrayList<>();
    }
    
    public void addDoor(Door door)
    {
        this.listDoors.add(door);
    }
    
    public void addStair(Stairs stairs)
    {
        this.listStairs.add(stairs);
    }
    
    public void addWall(Wall wall)
    {
        this.listWalls.add(wall);
    }
    
    public void addWindow(Window window)
    {
        this.listWindows.add(window);
    }
    /**
     * @return the listDoors
     */
    public List<Door> getListDoors() {
        return listDoors;
    }

    /**
     * @param listDoors the listDoors to set
     */
    public void setListDoors(List<Door> listDoors) {
        this.listDoors = listDoors;
    }

    /**
     * @return the listStairs
     */
    public List<Stairs> getListStairs() {
        return listStairs;
    }

    /**
     * @param listStairs the listStairs to set
     */
    public void setListStairs(List<Stairs> listStairs) {
        this.listStairs = listStairs;
    }

    /**
     * @return the listWalls
     */
    public List<Wall> getListWalls() {
        return listWalls;
    }

    /**
     * @param listWalls the listWalls to set
     */
    public void setListWalls(List<Wall> listWalls) {
        this.listWalls = listWalls;
    }

    /**
     * @return the listWindows
     */
    public List<Window> getListWindows() {
        return listWindows;
    }

    /**
     * @param listWindows the listWindows to set
     */
    public void setListWindows(List<Window> listWindows) {
        this.listWindows = listWindows;
    }
    
   
    
}
