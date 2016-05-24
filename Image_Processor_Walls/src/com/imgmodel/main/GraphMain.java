package com.imgmodel.main;

import com.imgmodel.buildingParts.Coordinates;
import com.imgmodel.buildingParts.Door;
import com.imgmodel.buildingParts.Wall;
import com.imgmodel.buildingParts.Window;
import com.imgmodel.graphModel.Graph;
import com.imgmodel.graphModel.Room;
import com.imgmodel.graphView.controller.MainController;
import com.imgprocessor.controller.ImageProcessorApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;

import javax.swing.filechooser.FileSystemView;

/**
 * Created by Rumpi on 5/10/2016.
 */
public class GraphMain extends Application {
    private FXMLLoader fxmlLoader;
    private Pane p;
    private MainController myController;
    public static volatile Graph graph;
    private Stage stage;

    public GraphMain(){
        Graph graph = new Graph();
        Room room1 = new Room("camera 1","parter");
        room1.addBuildingPart(new Wall(new Coordinates(0,0),new Coordinates(0,5)));
        room1.addBuildingPart(new Wall(new Coordinates(0,5),new Coordinates(5,5)));
        room1.addBuildingPart(new Window(new Coordinates(3,0),new Coordinates(4,0)));
        room1.addBuildingPart(new Door(new Coordinates(5,2),new Coordinates(5,3)));
        room1.getCorners().add(new Coordinates((float)0.0,(float)0.5));
        Room room2=new Room("camera 2","parter");

        Room room3=new Room("camera 3","etaj 1");
        Room room4=new Room("camera 4","etaj 2");
        graph.addNode(room1);
        graph.addNode(room2);
        graph.addNode(room3);
        graph.addNode(room4);

        GraphMain.graph = graph;

    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
        	
            Scene scene = new Scene(new Pane());
            fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/imgmodel/graphView/GraphView.fxml")
            );
            scene.setRoot((Parent) fxmlLoader.load());
            myController =fxmlLoader.<MainController>getController();
//            myController.initManager(this);

            
            
            myController.setGraph(this);
            primaryStage.setScene(scene);
            primaryStage.resizableProperty().setValue(Boolean.TRUE);
//            primaryStage.setWidth(900);
//            primaryStage.setHeight(700);
            primaryStage.sizeToScene();
            primaryStage.setTitle("GraphView");


            primaryStage.show();
            this.stage = primaryStage;
            

            //the new stage
            Stage newStage = new Stage();
            ImageProcessorApp processorApp = new ImageProcessorApp();
            processorApp.start(newStage);
            

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void  main(String[] args) {
       // GraphMain graphMain=new GraphMain();

        launch(args);
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        String desktop = fileSystemView.getHomeDirectory().toString();
        File folder = new File(desktop);
        Arrays.stream(folder.listFiles((f, p) -> p.endsWith(".xml"))).forEach(File::delete);
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public Pane getP() {
        return p;
    }

    public void setP(Pane p) {
        this.p = p;
    }

    public MainController getMyController() {
        return myController;
    }

    public void setMyController(MainController myController) {
        this.myController = myController;
    }

    public Graph getGraph() {
        return graph;
    }

    public  void setGraph(Graph graph) {
        GraphMain.graph = graph;
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
