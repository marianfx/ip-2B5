package com.imgmodel.buildingParts;

import java.util.List;

public class Coordinates {

    private float x;
    private float y;

    public Coordinates(){
        this.x=0;
        this.y=0;
    }

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean equals(Object o){
        if (!(o instanceof Coordinates ) ) return false;
        else {
            Coordinates c=(Coordinates)o;
            if (c.x!=this.x || c.y!=this.y) return false;
        }
        return true;
    }

    public boolean isInList(List<Coordinates> coordinatesList){
        for(Coordinates c: coordinatesList){
            if (this.equals(c)) return true;
        }
        return false;
    }

    public float getDistance(Coordinates b) {
        return (float) Math.sqrt((double) ((this.x - b.x) * (this.x - b.x) + (this.y - b.y) * (this.y - b.y)));
    }

    public static float getDistance(Coordinates a, Coordinates b) {
        return (float) Math.sqrt((double) ((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)));
    }
    public String toString(){
        return "x="+this.x+","+"y="+this.y;
    }
}