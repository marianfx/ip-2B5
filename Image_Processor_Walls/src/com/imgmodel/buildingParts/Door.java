package com.imgmodel.buildingParts;

public class Door extends BuildingPart {


    private float openingDimension;

    public Door(){
        super(new Coordinates((float)0,(float)0),new Coordinates((float)0,(float)0));
    }

    public Door(Coordinates start, Coordinates end) {
        super(start,end);
        this.openingDimension = this.getStart().getDistance(this.getEnd());
    }

    @Override
    public String toString() {
        return "Door: starting point:"+this.getStart().toString()+"; "+"end point:"+this.getEnd().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Door)) return false;
        else{
            Door d=(Door) o;
            if (!this.getStart().equals(d.getStart()) || !this.getEnd().equals(d.getEnd())
                    || this.openingDimension!=d.getOpeningDimension()) return  false;
        }
        return true;
    }


    public float getOpeningDimension() {
        return openingDimension;
    }

    public void setOpeningDimension(float openingDimension) {
        this.openingDimension = openingDimension;
    }




}