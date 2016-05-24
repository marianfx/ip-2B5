package com.imgprocessor.model;

import java.util.Objects;


public abstract class BuildingPart {

    private Coordinates start;

    private Coordinates end;


    public BuildingPart(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
    }
    
    public Coordinates getStart() {
        return start;
    }

    public void setStart(Coordinates start) {
        this.start = start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public void setEnd(Coordinates end) {
        this.end = end;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.start);
        hash = 83 * hash + Objects.hashCode(this.end);
        return hash;
    }


    @Override
    public abstract boolean equals(Object o);
}