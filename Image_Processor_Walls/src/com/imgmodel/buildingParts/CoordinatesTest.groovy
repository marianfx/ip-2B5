package buildingParts

import org.junit.Test

/*
import static org.junit.Assert.assertEquals;
import org.junit.Test;
 */

/**
 * Created by Admin on 21.05.2016.
 */
class CoordinatesTest extends GroovyTestCase {

    @Test
    void testEquals() {
        // BuildingPart is tested
        Coordinates first = new Coordinates(2,3);
        Coordinates second = new Coordinates(2,3);

        // assert statements
        assertEquals("coordinates must be equal", true, first.equals(second));
        //assertEquals("end must be identical with no set used", end, newEnd);

    }
/*
    void testIsInList() {

    }*/

    @Test
    void testGetDistance() {
        // BuildingPart is tested
        Coordinates first = new Coordinates(2,2); //baseline and to itself
        Coordinates second = new Coordinates(2,3); //simple test
        Coordinates third = new Coordinates(-2,2); //negative test
        Coordinates fourth = new Coordinates(200004,2); //big test

        // assert statements
        assertEquals("distance equal to 0", 0, first.getDistance(first));
        assertEquals("distance equal to 1", 1, first.getDistance(second));
        assertEquals("distance equal to 4", 4, first.getDistance(third));
        assertEquals("distance equal to 200002", 200002, first.getDistance(fourth));

    }

    @Test
    void testGetDistance1() {
        // BuildingPart is tested
        Coordinates first = new Coordinates(2,2); //baseline and to itself
        Coordinates second = new Coordinates(2,3); //simple test
        Coordinates third = new Coordinates(-2,2); //negative test
        Coordinates fourth = new Coordinates(200004,2); //big test

        // assert statements
        assertEquals("distance equal to 0", 0, first.getDistance(first, first));
        assertEquals("distance equal to 1", 1, first.getDistance(first, second));
        assertEquals("distance equal to 4", 4, first.getDistance(first, third));
        assertEquals("distance equal to 200002", 200002, first.getDistance(first, fourth));

    }
}
