package com.imgmodel.buildingParts;

public class Window extends BuildingPart {

    private float length;

    public Window(){
        super();
        this.length=0;
    }

  public Window(Coordinates start,Coordinates end){
    super(start,end);
    this.length=this.getStart().getDistance(this.getEnd());
  }

    @Override
    public String toString() {
        return "Window: starting point:"+this.getStart().toString()+"; "+"end point:"+this.getEnd().toString();
    }

    public float getLength() {
    return length;
  }

  public void setLength(float length) {
    this.length = length;
  }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Window)) return false;
        else{
            Window w=(Window) o;
            if (!this.getStart().equals(w.getStart()) || !this.getEnd().equals(w.getEnd())
                    || this.length!=w.getLength()) return  false;
        }
        return true;
    }

}