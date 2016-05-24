package com.imgmodel.graphModel;

import com.imgmodel.buildingParts.Coordinates;
import com.imgmodel.buildingParts.Door;
import com.imgmodel.buildingParts.Wall;
import com.imgmodel.buildingParts.Window;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by shull on 5/16/2016.
 */
public class RoomTest {

    @Test
    public void roomInfoTest1() throws Exception {

        System.out.println("Coords are good.");

        Room room1=new Room("camera 1","parter");
        room1.addBuildingPart(new Wall(new Coordinates(0,0),new Coordinates(0,5)));
        room1.addBuildingPart(new Wall(new Coordinates(0,5),new Coordinates(5,5)));
        room1.addBuildingPart(new Window(new Coordinates(3,0),new Coordinates(4,0)));
        room1.addBuildingPart(new Door(new Coordinates(5,2),new Coordinates(5,3)));

        String processedResult=room1.roomInfo();
        String expectedResult="camera 1 parter:\n"+
        "Components:\n"+
        "Wall: starting point:x=0.0,y=0.0; end point:x=0.0,y=5.0\n"+
        "Wall: starting point:x=0.0,y=5.0; end point:x=5.0,y=5.0\n"+
        "Window: starting point:x=3.0,y=0.0; end point:x=4.0,y=0.0\n"+
        "Door: starting point:x=5.0,y=2.0; end point:x=5.0,y=3.0\n";
        assertEquals(processedResult,expectedResult);
    }

    @Test
    public void roomInfoTest2() throws Exception{
        System.out.println("Door coords are out of range but the program still works!");

        Room room2=new Room("camera 2","parter");
        room2.addBuildingPart(new Wall(new Coordinates(0,0),new Coordinates(0,5)));
        room2.addBuildingPart(new Wall(new Coordinates(0,5),new Coordinates(5,5)));
        room2.addBuildingPart(new Wall(new Coordinates(5,5),new Coordinates(5,0)));
        room2.addBuildingPart(new Wall(new Coordinates(5,0),new Coordinates(0,0)));
        room2.addBuildingPart(new Door(new Coordinates(23,10),new Coordinates(-15,12)));
        String processedResult=room2.roomInfo();
        /*String expectedResult="camera 2 parter:\n" +
                "Components:\n" +
                "Wall: starting point:x=0.0,y=0.0; end point:x=0.0,y=5.0\n" +
                "Wall: starting point:x=0.0,y=5.0; end point:x=5.0,y=5.0\n" +
                "Wall: starting point:x=5.0,y=5.0; end point:x=5.0,y=0.0\n" +
                "Wall: starting point:x=5.0,y=0.0; end point:x=0.0,y=0.0\n" +
                "Door: starting point:x=23.0,y=10.0; end point:x=-15.0,y=12.0\n";*/

        assertNull(processedResult);
    }


    @Test
    public void roomInfoTest3() throws Exception{
        System.out.println("Parameters are null");
        Room room3=new Room(null,null);
        String processedResult=room3.roomInfo();

        String expectedResult=
                "null null:\n" +
                "Components:\n";
        assertEquals(processedResult,expectedResult);
    }



    @Test
    public void triangleAreaTest1() throws Exception {
        System.out.println("Simple triangle area test");
        Room room3 = new Room("camera 3","etaj 1");
        Coordinates a = new Coordinates(3,1);
        Coordinates b = new Coordinates(1,3);
        float processedArea = room3.triangleArea(a,b);
        float expectedArea = 4;

        assertEquals(processedArea, expectedArea, 0);
    }


    @Test
    public void triangleAreaTest2() throws Exception {
        System.out.println("2 points have the same coordinates");

        Room room4 = new Room("camera 4","etaj 1");
        Coordinates a = new Coordinates(1,3);
        Coordinates b = new Coordinates(1,3);

        float processedArea = room4.triangleArea(a,b);
        float expectedArea = 0;
        //duplicate coordinates will not form a triangle
        // result will be 0

        assertEquals(processedArea,expectedArea, 0);
    }



    @Test
    public void triangleAreaTest3() throws Exception {
        System.out.println("Coordinates form a straight line");
        Room room5 = new Room("camera 5","etaj 1");
        Coordinates a = new Coordinates(3,3);
        Coordinates b = new Coordinates(5,5);

        float processedArea = room5.triangleArea(a,b);
        float expectedArea = 0;
        //a straight line will not form a triangle
        //result will be 0

        assertEquals(processedArea,expectedArea, 0);
    }


    @Test
    public void triangleAreaTest4() throws Exception {
        System.out.println("All coordinates are (0,0)");
        Room room6 = new Room("camera 6","etaj 1");
        Coordinates a = new Coordinates(0,0);
        Coordinates b = new Coordinates(0,0);

        float processedArea = room6.triangleArea(a,b);
        float expectedArea = 0;
        //all coords are (0,0)
        //result will be 0

        assertEquals(processedArea,expectedArea, 0);
    }


    @Test
    public void triangleAreaTest5() throws Exception {
        System.out.println("Coords with negative parameters");
        Room room6 = new Room("camera 7","etaj 1");
        Coordinates a = new Coordinates(-5,10);
        Coordinates b = new Coordinates(12,-7);

        float processedArea = Math.abs(room6.triangleArea(a,b));
        float expectedArea = (float) 42.5;
        //coords can contain negative parameters

        assertEquals(processedArea,expectedArea, 0);
    }





    @Test
    public void nextBuidingPartTest1() throws Exception {
        System.out.println("Fetching next part to a wall");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));
        Wall b = new Wall(new Coordinates(0,5),new Coordinates(5,5));
        Window c = new Window(new Coordinates(3,0),new Coordinates(4,0));
        Door d = new Door(new Coordinates(5,2),new Coordinates(5,3));
        room1.addBuildingPart(a);
        room1.addBuildingPart(b);
        room1.addBuildingPart(c);
        room1.addBuildingPart(d);

        //fetching the next building part
        assertEquals(room1.nextBuidingPart(a),b);
    }


    @Test
    public void nextBuidingPartTest2() throws Exception {
        System.out.println("Trying to fetch the next part for the last component with no connection");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));
        Wall b = new Wall(new Coordinates(0,5),new Coordinates(5,5));
        Window c = new Window(new Coordinates(3,0),new Coordinates(4,0));
        Door d = new Door(new Coordinates(5,2),new Coordinates(5,3));
        room1.addBuildingPart(a);
        room1.addBuildingPart(b);
        room1.addBuildingPart(c);
        room1.addBuildingPart(d);

        //last building part will return as the next part null
        assertNull(room1.nextBuidingPart(d));
    }

    @Test
    public void nextBuidingPartTest3() throws Exception {
        System.out.println("Adding the components in a different order");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));
        Wall c = new Wall(new Coordinates(6,5),new Coordinates(5,0));
        Wall b = new Wall(new Coordinates(0,5),new Coordinates(5,5));
        room1.addBuildingPart(a);
        room1.addBuildingPart(c);
        room1.addBuildingPart(b);

        //The add order doesn't matter. next component will be fetched by it's connection with the part given in the parameter
        assertEquals(room1.nextBuidingPart(a),b);
    }

    @Test
    public void nextBuidingPartTest4() throws Exception {
        System.out.println("Comparing an added part with one that hasn't been added");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));
        Wall c = new Wall(new Coordinates(6,5),new Coordinates(5,0));

        Wall b = new Wall(new Coordinates(0,5),new Coordinates(5,5));
        room1.addBuildingPart(a);
        room1.addBuildingPart(c);

        //although Wall b is instantiated and has connection with Wall a it hasn't been added
        assertNotEquals(room1.nextBuidingPart(a),b);
    }

    @Test
    public void nextBuidingPartTest5() throws Exception {
        System.out.println("Trying to fetch next component for an un-added part");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));

        //un-added rooms return null as their next building part
        assertNull(room1.nextBuidingPart(a));
    }



    @Test
    public void nextBuidingPartTest6() throws Exception {
        System.out.println("Fetching next part to a door");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));
        Wall c = new Wall(new Coordinates(6,5),new Coordinates(5,0));
        Door d1 = new Door(new Coordinates(5,2),new Coordinates(5,3));
        Door d2 = new Door(new Coordinates(5,3),new Coordinates(5,4));
        room1.addBuildingPart(a);
        room1.addBuildingPart(d1);
        room1.addBuildingPart(c);
        room1.addBuildingPart(d2);

        //testing next building part for other components
        assertEquals(room1.nextBuidingPart(d1),d2);
    }



    @Test
    public void areaTest1() throws Exception {
        System.out.println("Calculating area for a square");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));
        Wall b = new Wall(new Coordinates(0,5),new Coordinates(5,5));
        Wall c = new Wall(new Coordinates(5,5),new Coordinates(5,0));
        Wall d = new Wall(new Coordinates(5,0),new Coordinates(0,0));
        room1.addBuildingPart(a);
        room1.addBuildingPart(b);
        room1.addBuildingPart(c);
        room1.addBuildingPart(d);


        List<Coordinates> coords = new ArrayList<Coordinates>();
        coords.add(new Coordinates(0,0));
        coords.add(new Coordinates(0,5));
        coords.add(new Coordinates(5,5));
        coords.add(new Coordinates(5,0));
        room1.setCorners(coords);

        //System.out.println(room1.area());

        //area for a square
        //should return 25
        assertEquals(room1.area(),25, 0);
        //returns 0!
    }


    @Test
    public void areaTest2() throws Exception {
        System.out.println("Calculating area for a simple polygon");
        Room room1=new Room("camera 1","parter");
        Wall a = new Wall(new Coordinates(0,0),new Coordinates(0,5));
        Wall b = new Wall(new Coordinates(0,5),new Coordinates(2,7));
        Wall c = new Wall(new Coordinates(2,7),new Coordinates(7,6));
        Wall d = new Wall(new Coordinates(7,6),new Coordinates(5,5));
        Wall e = new Wall(new Coordinates(5,5),new Coordinates(2,0));
        Wall f = new Wall(new Coordinates(2,0),new Coordinates(0,0));
        room1.addBuildingPart(a);
        room1.addBuildingPart(b);
        room1.addBuildingPart(c);
        room1.addBuildingPart(d);
        room1.addBuildingPart(e);
        room1.addBuildingPart(f);

        List<Coordinates> coords = new ArrayList<Coordinates>();
        coords.add(new Coordinates(0,0));
        coords.add(new Coordinates(0,5));
        coords.add(new Coordinates(2,7));
        coords.add(new Coordinates(7,6));
        coords.add(new Coordinates(5,5));
        coords.add(new Coordinates(2,0));
        room1.setCorners(coords);

        //System.out.println(room1.area());

        //area for a simple polygon
        //should return 26
        assertEquals(room1.area(),26, 0);

    }

}