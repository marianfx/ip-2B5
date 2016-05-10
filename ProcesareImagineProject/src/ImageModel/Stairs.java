package ImageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stairs extends BuildingPart {

    private List<Coordinates> corners;


    public Stairs(Coordinates start, Coordinates end, List<Coordinates> corners) {
        super(start, end);
        this.corners = corners;

    }

    public Stairs(Coordinates start, Coordinates end) {
        super(start, end);
        this.corners = new ArrayList<>();

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
            if (!this.corners.stream().noneMatch((c) -> (!c.isInList(s.getCorners())))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.corners);
        return hash;
    }
}