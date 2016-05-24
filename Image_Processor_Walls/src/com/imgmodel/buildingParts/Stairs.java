package com.imgmodel.buildingParts;

import java.util.ArrayList;
import java.util.List;

public class Stairs extends BuildingPart {

    private List<Coordinates> corners;

    public Stairs(){
        super();
        this.corners=new ArrayList<>();
    }


    public Stairs(Coordinates start, Coordinates end, List<Coordinates> corners) {
        super(start, end);
        this.corners = corners;

    }

    public Stairs(Coordinates start, Coordinates end) {
        super(start, end);
        this.corners = new ArrayList<>();

    }

    @Override
    public String toString() {
        return "Stairs: starting point:"+this.getStart().toString()+"; "+"end point:"+this.getEnd().toString();
    }

    public List<Coordinates> getCorners() {
        return corners;
    }

    public void setCorners(List<Coordinates> corners) {
        this.corners = corners;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stairs)) return false;
        else{
            Stairs s=(Stairs) o;
            if (!this.getStart().equals(s.getStart()) || !this.getEnd().equals(s.getEnd())) return  false;
            for (Coordinates c:this.corners){
                if (!c.isInList(s.getCorners())) return false;
            }
        }
        return true;
    }
}