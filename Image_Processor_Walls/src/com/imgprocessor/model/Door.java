package com.imgprocessor.model;

public class Door extends BuildingPart {


    private float openingDimension;

    public Door(Coordinates start, Coordinates end) {
        super(start,end);
        this.openingDimension = this.getStart().getDistance(this.getEnd());
    }
    public Door()
    {
    	super(new Coordinates(0,0),new Coordinates(0,0));
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Float.floatToIntBits(this.openingDimension);
        return hash;
    }


    public float getOpeningDimension() {
        return openingDimension;
    }

    public void setOpeningDimension(float openingDimension) {
        this.openingDimension = openingDimension;
    }




}