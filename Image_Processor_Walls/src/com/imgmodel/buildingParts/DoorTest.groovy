package buildingParts

import org.junit.Test

/**
 * Created by Admin on 21.05.2016.
 */
class DoorTest extends GroovyTestCase {
    @Test
    void testEquals() {
        // BuildingPart is tested
        Coordinates first = new Coordinates(2,3);
        Coordinates second = new Coordinates(2,5);

        Door firstDoor = new Door(first, second);
        Door secondDoor = new Door(first, second);

        Coordinates third = new Coordinates(3,5);
        Coordinates fourth = new Coordinates(9,10);

        Door thirdDoor = new Door(third, fourth);
        Door fourthDoor = new Door(first, third);
        Door fifthDoor = new Door(third, second);


        // assert statements
        assertEquals("doors must be equal to themselves", true, firstDoor.equals(firstDoor));
        assertEquals("doors must be equal to copy", true, firstDoor.equals(secondDoor));

        //different asserts
        assertEquals("doors must not be equal to different ones", false, firstDoor.equals(thirdDoor));
        assertEquals("doors must not be equal to different ones", false, firstDoor.equals(fourthDoor));//same start
        assertEquals("doors must not be equal to different ones", false, firstDoor.equals(fifthDoor));//same end
    }
}
