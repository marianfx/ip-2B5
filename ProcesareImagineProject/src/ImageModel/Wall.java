package ImageModel;

public class Wall extends BuildingPart {

  private float length;

  private float width;

    public Wall(Coordinates start,Coordinates end){
        super(start,end);
        this.length=this.getStart().getDistance(this.getEnd());
        this.width=0;

    }


    public Wall(Coordinates start,Coordinates end,float width){
        super(start,end);
        this.length=this.getStart().getDistance(this.getEnd());
        this.width=width;

    }


    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Wall)) return false;
        else{
            Wall w=(Wall) o;
            if (!this.getStart().equals(w.getStart()) || !this.getEnd().equals(w.getEnd())
                    || this.length!=w.getLength()) return  false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Float.floatToIntBits(this.length);
        return hash;
    }


}