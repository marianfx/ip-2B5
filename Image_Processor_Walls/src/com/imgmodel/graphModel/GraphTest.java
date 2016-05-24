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
 * Created by lucai on 5/17/2016.
 */
public class GraphTest {
    @Test
    public void getListOfRoomsStringTest1() throws Exception {
        System.out.println("Adding 4 rooms to graph, 1 room having it's components defined");
        Graph graph=new Graph();
        Room room1 = new Room("camera 1","parter");
        room1.addBuildingPart(new Wall(new Coordinates(0,0),new Coordinates(0,5)));
        room1.addBuildingPart(new Wall(new Coordinates(0,5),new Coordinates(5,5)));
        room1.addBuildingPart(new Window(new Coordinates(3,0),new Coordinates(4,0)));
        room1.addBuildingPart(new Door(new Coordinates(5,2),new Coordinates(5,3)));
        Room room2=new Room("camera 2","parter");

        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");
        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);

        List<String> expectedResult=new ArrayList<>();

        expectedResult.add("camera 1 parter");
        expectedResult.add("camera 2 parter");
        expectedResult.add("camera 3 etaj 1");
        expectedResult.add("camera 4 etaj 2");


        assertEquals(expectedResult,graph.getListOfRoomsString());
    }

    @Test
    public void getListOfRoomsStringTest2() throws Exception {
        System.out.println("Comparing an empty graph to an empty list");
        Graph graph = new Graph();
        List<String> expectedResult=new ArrayList<>();
        assertEquals(graph.getListOfRoomsString(),expectedResult);
    }

    @SuppressWarnings("serial")
	@Test
    public void bfsTest1() throws Exception {
        System.out.println("Room1 is connected only with room2");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");
        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);


        room1.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}});
        room2.setAdjacentRooms(new ArrayList<Room>(){{add(room1);}});

        List<Room> expectedResult = new ArrayList<Room>();
        expectedResult.add(room1);
        expectedResult.add(room2);

        assertEquals(graph.bfs(room1),expectedResult);
        //System.out.println(graph.bfs(room1));
    }


    @Test
    public void bfsTest2() throws Exception {
        System.out.println("Room1 is an isolated node");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");
        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);

        List<Room> expectedResult = new ArrayList<Room>();
        expectedResult.add(room1);

        assertEquals(graph.bfs(room1),expectedResult);
        //System.out.println(graph.bfs(room1));
    }


    @SuppressWarnings("serial")
	@Test
    public void bfsTest3() throws Exception {
        System.out.println("Room1 is connected with 2 other rooms, while one of them is connected with another room");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");

        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);

        room1.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}{add(room4);}});
        room2.setAdjacentRooms(new ArrayList<Room>(){{add(room1);}{add(room3);}});
        room3.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}});
        room4.setAdjacentRooms(new ArrayList<Room>(){{add(room1);}});

        List<Room> expectedResult = new ArrayList<Room>();
        expectedResult.add(room1);
        expectedResult.add(room2);
        expectedResult.add(room4);
        expectedResult.add(room3);

        assertEquals(graph.bfs(room1),expectedResult);
        //System.out.println(graph.bfs(room1));
    }


    @SuppressWarnings("serial")
	@Test
    public void bfsTest4() throws Exception {
        System.out.println("Graph forms a cycle");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");

        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);

        room1.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}{add(room4);}});
        room2.setAdjacentRooms(new ArrayList<Room>(){{add(room1);}{add(room3);}});
        room3.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}{add(room4);}});
        room4.setAdjacentRooms(new ArrayList<Room>(){{add(room3);}{add(room1);}});

        List<Room> expectedResult = new ArrayList<Room>();
        expectedResult.add(room1);
        expectedResult.add(room2);
        expectedResult.add(room4);
        expectedResult.add(room3);

        assertEquals(graph.bfs(room1),expectedResult);
       //System.out.println(graph.bfs(room1));
    }

    @SuppressWarnings("serial")
	@Test
    public void splitGraphTest1() throws Exception {
        System.out.println("All nodes form a connected component");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");

        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);

        room1.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}{add(room4);}});
        room2.setAdjacentRooms(new ArrayList<Room>(){{add(room1);}{add(room3);}});
        room3.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}});
        room4.setAdjacentRooms(new ArrayList<Room>(){{add(room1);}});

        assertEquals(graph.splitGraph(),1);
        //System.out.println(graph.splitGraph());
    }


    @SuppressWarnings("serial")
	@Test
    public void splitGraphTest2() throws Exception {
        System.out.println("2 connected components in graph");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");

        graph.addNode(room1);
        graph.addNode(room4);
        graph.addNode(room2);
        graph.addNode(room3);


        room1.setAdjacentRooms(new ArrayList<Room>(){{add(room4);}});
        room2.setAdjacentRooms(new ArrayList<Room>(){{add(room3);}});
        room3.setAdjacentRooms(new ArrayList<Room>(){{add(room2);}});
        room4.setAdjacentRooms(new ArrayList<Room>(){{add(room1);}});

        assertEquals(graph.splitGraph(),2);
        //System.out.println(graph.splitGraph());
    }


    @Test
    public void splitGraphTest3() throws Exception {
        System.out.println("All nodes are isolated so each node forms a connected component");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
//        Room room3=new Room("camera 3","etaj 1"); //room3 not added to graph
        Room room4=new Room("camera 4","etaj 2");

        graph.addNode(room1);
        graph.addNode(room4);
        graph.addNode(room2);

        assertEquals(graph.splitGraph(),3);
        //System.out.println(graph.splitGraph());
    }



    @Test
    public void splitRoom() throws Exception {
        System.out.println("Splting a room test");
        Graph graph=new Graph();
        Room room1=new Room("camera 1","parter");
        Room room2=new Room("camera 2","parter");
        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");
        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);

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


        List<String> expectedResult=new ArrayList<>();


        expectedResult.add("camera 2 parter");
        expectedResult.add("camera 3 etaj 1");
        expectedResult.add("camera 4 etaj 2");
        expectedResult.add("camera 1_1 parter"); //splitted rooms are addes to the end of the list
        expectedResult.add("camera 1_2 parter");

        if(graph.splitRoom(room1,2)) {
            //System.out.println(graph.getListOfRoomsString());
            assertEquals(graph.getListOfRoomsString(),expectedResult);
        }


    }

}