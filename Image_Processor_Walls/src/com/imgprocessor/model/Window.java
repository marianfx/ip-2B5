package com.imgprocessor.model;


public class Window extends BuildingPart {

    private float length;

  public Window(Coordinates start, Coordinates end){
    super(start,end);
    this.length = this.getStart().getDistance(this.getEnd());
  }
  public Window()
  {
	  super(new Coordinates(0,0),new Coordinates(0,0));
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
            Window w = (Window) o;
            if (!this.getStart().equals(w.getStart()) || !this.getEnd().equals(w.getEnd())
                    || this.length!=w.getLength()) return  false;
        }
        return true;
    }

}