package com.imgmodel.graphView.controller;

import com.imgmodel.buildingParts.*;
import com.imgmodel.graphModel.GraphEdge;
import com.imgmodel.graphModel.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import com.imgmodel.main.GraphMain;
import com.imgmodel.serialization.Deserializer;
import com.imgmodel.serialization.Serializer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.filechooser.FileSystemView;

/**
 * Created by Valeria on 5/10/2016.
 */
public class MainController implements Initializable {
    private GraphMain graph;
    @FXML
    private ListView<Room> nodeList;
    @FXML
    private ListView<Object> partList;
    private boolean room;
    private boolean level;
    @FXML
    private TextField insert;
    @FXML
    private Text insertText;
    @FXML
    private Button submit;
    private String roomName;
    private String roomLevel;
    @FXML
    private Button add;
    @FXML
    private ChoiceBox<String> chooseComp;
    @FXML
    private Text infoText;
    @FXML
    private Text invalidText;
    @FXML
    private Button modify;
    @FXML
    private Label width;
    @FXML
    private TextField widthValue;
    @FXML
    private TextField startX;
    @FXML
    private TextField startY;
    @FXML
    private TextField endX;
    @FXML
    private TextField endY;
    @FXML
    private TextField cornerX;
    @FXML
    private TextField cornerY;
    @FXML
    private Label chooseRoomText;
    @FXML
    private ChoiceBox<Room> chooseRoom;
    @FXML
    private Button submitAdd;
    @FXML
    private Button submitModify;
    @FXML
    private Label cornerText;
    @FXML
    private Label startText;
    @FXML
    private Label endText;
    @FXML
    private Text s_x;
    @FXML
    private Text s_y;
    @FXML
    private Text e_x;
    @FXML
    private Text e_y;
    @FXML
    private Text c_x;
    @FXML
    private Text c_y;
    @FXML
    private ChoiceBox<Room> chooseRoomMerge;
    @FXML
    private Text unableToMerge;
    @FXML
    private TextField mergeTextField;


    public GraphMain getGraph() {
        return graph;
    }

    public void setGraph(GraphMain graph) {
        this.graph = graph;

    }

	@Override
    public void initialize(URL location, ResourceBundle resources) {
        this.room=false;
        this.level=false;

        this.insertText.setVisible(false);
        this.insert.setText(null);
        this.add.setVisible(false);
        this.chooseComp.setVisible(false);
        this.chooseComp.setItems(FXCollections.observableArrayList(
                "Window", "Wall","Door","Stairs","Corner" ));
//        this.infoText.setVisible(false);
        this.invalidText.setVisible(false);
        this.modify.setVisible(false);
        this.startX.setText(null);
        this.startY.setText(null);
        this.endY.setText(null);
        this.endX.setText(null);

        this.hideInsertBoxes();

        //textArea.se (textArea, Priority.ALWAYS);
//        ObservableList<String> nodeList= FXCollections.observableArrayList("camera 1- parter","camera 2- parter","camera 3-parter");
//        this.nodeList.setItems(nodeList);
    }

    public void hideInsertBoxes(){
        this.cornerX.setVisible(false);
        this.cornerY.setVisible(false);
        this.cornerText.setVisible(false);
        this.startX.setVisible(false);
        this.startY.setVisible(false);
        this.endX.setVisible(false);
        this.endY.setVisible(false);
        this.startText.setVisible(false);
        this.endText.setVisible(false);
        this.chooseRoom.setVisible(false);
        this.chooseRoomText.setVisible(false);
        this.s_x.setVisible(false);
        this.s_y.setVisible(false);
        this.e_x.setVisible(false);
        this.e_y.setVisible(false);
        this.c_x.setVisible(false);
        this.c_y.setVisible(false);
        this.width.setVisible(false);
        this.widthValue.setVisible(false);
        this.submitAdd.setVisible(false);
        this.submitModify.setVisible(false);
        this.mergeTextField.setText(null);
        this.unableToMerge.setVisible(false);

    }

    public void setNodeListValues(){
        this.unableToMerge.setVisible(false);
        ObservableList<Room> nodeList= FXCollections.observableArrayList(this.graph.getGraph().getNodes());
        this.nodeList.setItems(nodeList);
        this.nodeList.refresh();

    }

    public ListView<Room> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ListView<Room> nodeList) {
        this.nodeList = nodeList;
    }

    @FXML
    public void show(Event event) {
        this.setNodeListValues();
    }

	public void setPartList(){
        List<Object> partListAux=new ArrayList<>();
        partListAux.addAll(nodeList.getSelectionModel().getSelectedItem().getCorners());
        partListAux.addAll(nodeList.getSelectionModel().getSelectedItem().getParts());
        ObservableList<Object> partListAux1= FXCollections.observableArrayList(partListAux);
        this.partList.setItems(partListAux1);
        this.add.setVisible(true);
        this.chooseComp.setVisible(true);
        this.partList.refresh();
    }

	@FXML
    public void selectNode(Event event) {
        this.unableToMerge.setVisible(false);
        this.hideInsertBoxes();
        this.partList.setItems(null);
        if (nodeList.getSelectionModel()!=null) {
            this.width.setVisible(false);
            this.widthValue.setVisible(false);
            this.setPartList();
            List <Room> roomList=new ArrayList<>();
            Room r=this.nodeList.getSelectionModel().getSelectedItem();
            for (Room room :this.graph.getGraph().getNodes()){
                if (room.getLevel().compareTo(r.getLevel())==0 && !room.equals(r)){
                    roomList.add(room);
                    //TODO: save here
                    AutoSave.specialSave(this.graph.getGraph());
                    AutoSave.debug();
                }
            }
            this.chooseRoomMerge.setItems(FXCollections.observableArrayList(roomList));

        }
    }

    @FXML
    public void add(Event event) {
        this.insert.setVisible(true);
        this.insertText.setText("Insert room name");
        this.insertText.setVisible(true);
        this.submit.setVisible(true);
        this.room=true;
    }
    @FXML
    public void clickSubmit(Event event) {
        if (level) {
            if (insert.getText() != null && insert.getText().compareTo("")!=0) {
                this.roomLevel = insert.getText();
                System.out.println("room level: " + this.roomLevel);
                this.level = false;
                this.insertText.setVisible(false);
                this.insert.setText(null);
                this.insert.setVisible(false);
                this.submit.setVisible(false);
                this.graph.getGraph().addNode(new Room(this.roomName,this.roomLevel));
                setNodeListValues();
                //TODO: save here
                AutoSave.specialSave(this.graph.getGraph());
                AutoSave.debug();

            } else {
                this.insertText.setText("Room level wasn't inserted. Insert room level");
            }
        }
        if (room){
            if (insert.getText()!=null){
                this.roomName=insert.getText();
                System.out.println("room name: "+this.roomName);
                this.room=false;
                this.level=true;
                this.insertText.setText("Insert level");
                this.insert.setText(null);
                //TODO: save here
                AutoSave.specialSave(this.graph.getGraph());
                AutoSave.debug();
            }
            else{
                this.insertText.setText("Room name wasn't inserted. Insert room name");
            }


        }
    }

	public void addClick(Event event) {
        this.removeText();
        this.submitModify.setVisible(false);
        this.submitModify.setDisable(true);
        this.hideInsertBoxes();
        this.invalidText.setVisible(false);
        if (this.chooseComp.getSelectionModel().getSelectedIndex()<0){
            this.invalidText.setText("Choose a building part!");
            this.invalidText.setVisible(true);
        }else {
            this.submitAdd.setDisable(false);
            this.submitAdd.setVisible(true);
//            this.infoText.setVisible(false);
            if (this.chooseComp.getSelectionModel().getSelectedIndex()==4) {
                this.cornerText.setVisible(true);
                this.cornerX.setVisible(true);
                this.cornerY.setVisible(true);
                this.c_x.setVisible(true);
                this.c_y.setVisible(true);
            }
            else{
                this.s_x.setVisible(true);
                this.s_y.setVisible(true);
                this.e_x.setVisible(true);
                this.e_y.setVisible(true);
                this.startY.setVisible(true);
                this.startX.setVisible(true);
                this.startText.setVisible(true);
                this.endX.setVisible(true);
                this.endY.setVisible(true);
                this.endText.setVisible(true);
                if (this.chooseComp.getSelectionModel().getSelectedIndex()==1){
                    this.width.setVisible(true);
                    this.widthValue.setVisible(true);
                }
                if (this.chooseComp.getSelectionModel().getSelectedIndex()==2){
                    List <Room> roomList=new ArrayList<>();
                    Room r=this.nodeList.getSelectionModel().getSelectedItem();
                    for (Room room :this.graph.getGraph().getNodes()){
                        if (room.getLevel().compareTo(r.getLevel())==0 && !room.equals(r)){
                            roomList.add(room);
                            //TODO: save here
                            AutoSave.specialSave(this.graph.getGraph());
                            AutoSave.debug();
                        }
                    }
                    this.chooseRoom.setItems(FXCollections.observableArrayList(roomList));
                    this.chooseRoomText.setVisible(true);
                    this.chooseRoom.setVisible(true);
                }

            }

        }
    }

    public void clickStartX(Event event) {
    }

    public void clickStartY(Event event) {
    }

    public void clickEndY(Event event) {
    }

    public void clickEndX(Event event) {
    }

    public void clickWidth(Event event) {
    }

    public void clickModify(Event event) {
        this.removeText();
        this.submitAdd.setVisible(false);
        this.submitAdd.setDisable(true);
        this.invalidText.setVisible(false);
        if  (partList.getSelectionModel()!=null) {
            this.submitModify.setDisable(false);
            this.submitModify.setVisible(true);
            if( partList.getSelectionModel().getSelectedItem() instanceof  Coordinates){
                this.c_x.setVisible(true);
                this.c_y.setVisible(true);
                this.cornerText.setVisible(true);
                this.cornerX.setVisible(true);
                this.cornerY.setVisible(true);
                this.cornerX.setText(((Coordinates) partList.getSelectionModel().getSelectedItem()).getX()+"");
                this.cornerY.setText(((Coordinates) partList.getSelectionModel().getSelectedItem()).getY()+"");
                //TODO: save here
                AutoSave.specialSave(this.graph.getGraph());
                AutoSave.debug();
            }
            else{
                this.s_x.setVisible(true);
                this.s_y.setVisible(true);
                this.e_x.setVisible(true);
                this.e_y.setVisible(true);
                this.startY.setVisible(true);
                this.startX.setVisible(true);
                this.startText.setVisible(true);
                this.endX.setVisible(true);
                this.endY.setVisible(true);
                this.endText.setVisible(true);
                this.startX.setText(((BuildingPart)partList.getSelectionModel().getSelectedItem()).getStart().getX()+"");
                this.startY.setText(((BuildingPart)partList.getSelectionModel().getSelectedItem()).getStart().getY()+"");
                this.endX.setText(((BuildingPart)partList.getSelectionModel().getSelectedItem()).getEnd().getX()+"");
                this.endY.setText(((BuildingPart)partList.getSelectionModel().getSelectedItem()).getEnd().getY()+"");
                //TODO: save here
                AutoSave.specialSave(this.graph.getGraph());
                AutoSave.debug();
                if (partList.getSelectionModel().getSelectedItem() instanceof  Wall){
                    this.width.setVisible(true);
                    this.widthValue.setVisible(true);
                    this.widthValue.setText(((Wall)partList.getSelectionModel().getSelectedItem() ).getWidth()+"");
                    //TODO: save here
                    AutoSave.specialSave(this.graph.getGraph());
                    AutoSave.debug();
                    }
                }
            }
        else{
        this.invalidText.setText("Invalid value! Insert a new value!");
        this.invalidText.setVisible(true);

        }


    }

    public void selectPart(Event event) {
        this.hideInsertBoxes();
        this.modify.setVisible(true);
        if (partList.getSelectionModel().getSelectedItem() instanceof Wall){
            //this.width.setVisible(true);
            //this.widthValue.setVisible(true);
            //this.startX.setText();
        }
        else {
            this.width.setVisible(false);
            this.widthValue.setVisible(false);
        }

    }

    public void clickSubmitAdd(Event event) {

            try {
                this.invalidText.setVisible(false);
                switch (this.chooseComp.getSelectionModel().getSelectedIndex()) {
                    case 0: {
                        if (this.startX.getText()==null || this.startY.getText()==null ||
                                this.endX.getText()==null || this.endY.getText()==null){
                            this.invalidText.setText("Invalid value! Insert a new value!");
                            this.invalidText.setVisible(true);
                            break;
                        }else{
                            Window w = new Window(new Coordinates(Float.parseFloat(this.startX.getText()),
                                    Float.parseFloat(this.startY.getText())),
                                    new Coordinates(Float.parseFloat(this.endX.getText()),
                                            Float.parseFloat(this.endY.getText())));
                            this.nodeList.getSelectionModel().getSelectedItem().addBuildingPart(w);
                            //TODO: save here
                            AutoSave.specialSave(this.graph.getGraph());
                            AutoSave.debug();
                            break;
                        }

                    }
                    case 1: {
                        if (this.startX.getText()==null || this.startY.getText()==null ||
                                this.endX.getText()==null || this.endY.getText()==null){
                            this.invalidText.setText("Invalid value! Insert a new value!");
                            this.invalidText.setVisible(true);
                            break;
                        }else {
                            Wall w = new Wall(new Coordinates(Float.parseFloat(this.startX.getText()),
                                    Float.parseFloat(this.startY.getText())),
                                    new Coordinates(Float.parseFloat(this.endX.getText()),
                                            Float.parseFloat(this.endY.getText())));
                            w.setWidth(Float.parseFloat(this.widthValue.getText()));
                            this.nodeList.getSelectionModel().getSelectedItem().addBuildingPart(w);
                            //TODO: save here
                            AutoSave.specialSave(this.graph.getGraph());
                            AutoSave.debug();
                            break;
                        }
                    }
                    case 2: {
                        if (this.startX.getText()==null || this.startY.getText()==null ||
                                this.endX.getText()==null || this.endY.getText()==null){
                            this.invalidText.setText("Invalid value! Insert a new value!");
                            this.invalidText.setVisible(true);
                            break;
                        }else {
                            Door d = new Door(new Coordinates(Float.parseFloat(this.startX.getText()),
                                    Float.parseFloat(this.startY.getText())),
                                    new Coordinates(Float.parseFloat(this.endX.getText()),
                                            Float.parseFloat(this.endY.getText())));
                            this.nodeList.getSelectionModel().getSelectedItem().addBuildingPart(d);
                            ((Room) this.chooseRoom.getSelectionModel().getSelectedItem()).addBuildingPart(d);
                            GraphEdge g=new GraphEdge(this.nodeList.getSelectionModel().getSelectedItem(),
                                    (Room) this.chooseRoom.getSelectionModel().getSelectedItem(),d);
                            this.graph.getGraph().addEdge(g);
                            //TODO: save here
                            AutoSave.specialSave(this.graph.getGraph());
                            AutoSave.debug();
                            break;
                        }
                    }
                    case 3:{
                        if (this.startX.getText()==null || this.startY.getText()==null ||
                                this.endX.getText()==null || this.endY.getText()==null){
                            this.invalidText.setText("Invalid value! Insert a new value!");
                            this.invalidText.setVisible(true);
                            break;
                        }else {
                            Stairs s = new Stairs(new Coordinates(Float.parseFloat(this.startX.getText()),
                                    Float.parseFloat(this.startY.getText())),
                                    new Coordinates(Float.parseFloat(this.endX.getText()),
                                            Float.parseFloat(this.endY.getText())));
                            this.nodeList.getSelectionModel().getSelectedItem().addBuildingPart(s);
                            //TODO: save here
                            AutoSave.specialSave(this.graph.getGraph());
                            AutoSave.debug();
                            break;
                        }
                    }
                    case 4:{
                        if (this.cornerX.getText()==null || this.cornerY.getText()==null ) {
                            this.invalidText.setText("Invalid value! Insert a new value!");
                            this.invalidText.setVisible(true);
                            break;
                        }
                            else{
                                this.nodeList.getSelectionModel().getSelectedItem().getCorners().
                                        add(new Coordinates(Float.parseFloat(this.cornerX.getText()),
                                                Float.parseFloat(this.cornerX.getText())));
                            //TODO: save here
                            AutoSave.specialSave(this.graph.getGraph());
                            AutoSave.debug();
                                break;
                            }
                    }
                }
            }
            catch (NumberFormatException e){
                this.invalidText.setVisible(true);
                System.out.println("Add: Invalid format");
            }
            this.setPartList();
            this.hideInsertBoxes();
            this.removeText();
        //TODO: save here ???
        AutoSave.specialSave(this.graph.getGraph());
        AutoSave.debug();
    }

    public void removeText(){
        this.startX.setText(null);
        this.startY.setText(null);
        this.endX.setText(null);
        this.endY.setText(null);
        this.cornerX.setText(null);
        this.cornerY.setText(null);
        this.widthValue.setText(null);
    }

    public void clickSubmitModify(Event event) {



                try {
                        if(partList.getSelectionModel().getSelectedItem() instanceof Coordinates) {
                            if (this.cornerX.getText()==null || this.cornerY.getText()==null){
                                this.invalidText.setVisible(true);
                            }
                            else{
                                this.invalidText.setVisible(false);
                                 ((Coordinates) partList.getSelectionModel().getSelectedItem()).
                                        setX(Float.parseFloat(this.cornerX.getText()));
                                ((Coordinates) partList.getSelectionModel().getSelectedItem()).
                                        setY(Float.parseFloat(this.cornerY.getText()));
                            }
                            //TODO: save here
                            AutoSave.specialSave(this.graph.getGraph());
                            AutoSave.debug();
//                            this.c_x.setVisible(true);
//                            this.c_y.setVisible(true);
//                            this.cornerX.setVisible(true);
//                            this.cornerY.setVisible(true);
//                            this.cornerText.setVisible(true);

                        }

                            else {

                            if (this.startX.getText() == null || this.startY.getText() == null || this.endX.getText() == null ||
                                    this.endY.getText() == null) {
                                this.invalidText.setVisible(true);
                            } else {
                                this.invalidText.setVisible(false);
                                if (partList.getSelectionModel().getSelectedItem() instanceof Wall) {

                                    ((Wall) partList.getSelectionModel().getSelectedItem()).
                                            setStart(new Coordinates(Float.parseFloat(this.startX.getText()),
                                                    Float.parseFloat(this.startY.getText())));
                                    ((Wall) partList.getSelectionModel().getSelectedItem()).
                                            setEnd(new Coordinates(Float.parseFloat(this.endX.getText()),
                                                    Float.parseFloat(this.endY.getText())));
                                    ((Wall) partList.getSelectionModel().getSelectedItem()).
                                            setWidth(Float.parseFloat(this.widthValue.getText()));
                                    //TODO: save here
                                    AutoSave.specialSave(this.graph.getGraph());
                                    AutoSave.debug();
                                }
                                else{
                                    ((BuildingPart) partList.getSelectionModel().getSelectedItem()).
                                            setStart(new Coordinates(Float.parseFloat(this.startX.getText()),
                                                    Float.parseFloat(this.startY.getText())));
                                    ((BuildingPart) partList.getSelectionModel().getSelectedItem()).
                                            setEnd(new Coordinates(Float.parseFloat(this.endX.getText()),
                                                    Float.parseFloat(this.endY.getText())));
                                    //TODO: save here
                                    AutoSave.specialSave(this.graph.getGraph());
                                    AutoSave.debug();
                                }
                            }
                        }


                }
                        catch (NumberFormatException e){
                            System.out.println("Invalid format");
                            this.invalidText.setVisible(true);
                        }



        this.setPartList();
        this.removeText();
        this.hideInsertBoxes();
        //TODO: save here ???
        AutoSave.specialSave(this.graph.getGraph());
        AutoSave.debug();
    }

    public void clickSave(Event event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(this.graph.getStage());

        if(file != null){
            SaveFile(file);
        }
    }

    private void SaveFile( File file){
        Serializer ser=new Serializer(file);
        try {
            ser.saveXML(this.graph.getGraph());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clickLoad(Event event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(this.graph.getStage());

        if(file != null){
            Deserializer des=new Deserializer(file);
            try {
                this.graph.setGraph(des.loadXML());
                System.out.println("Finished deserialize");
                this.setNodeListValues();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void deleteNode(Event event) {
        if (this.getNodeList().getSelectionModel().getSelectedItem()!=null){
            Room r=(Room)this.getNodeList().getSelectionModel().getSelectedItem();
            for (GraphEdge g:this.graph.getGraph().getEdges()){
                if (g.getNodes().contains(r)){
                    for (Room node:g.getNodes()){
                        if(!node.equals(r)){
                            node.deleteBuildingPart(g.getDoor());
                        }
                    }
                    this.graph.getGraph().deleteEdge(g);
                }
            }
            this.graph.getGraph().deleteNode(r);
        }
        this.setNodeListValues();
        //TODO: save here
        AutoSave.specialSave(this.graph.getGraph());
        AutoSave.debug();
    }

    public void deleteComp(Event event) {
        if (this.partList.getSelectionModel().getSelectedItem() instanceof Coordinates){
            this.nodeList.getSelectionModel().getSelectedItem().getCorners().
                    remove(this.partList.getSelectionModel().getSelectedItem());
            //TODO: save here
            AutoSave.specialSave(this.graph.getGraph());
            AutoSave.debug();
        }else{
            if (this.partList.getSelectionModel().getSelectedItem() instanceof Door){
                this.graph.getGraph().removeEdge((Door)this.partList.getSelectionModel().getSelectedItem());
            }
            else{
                this.nodeList.getSelectionModel().getSelectedItem().deleteBuildingPart
                        ((BuildingPart)this.partList.getSelectionModel().getSelectedItem());
            }
            this.setPartList();
            AutoSave.specialSave(this.graph.getGraph());
            AutoSave.debug();
        }


    }

    public void clickMerge(Event event) {
        this.unableToMerge.setVisible(false);
        if (this.chooseRoomMerge.getSelectionModel().getSelectedIndex()<0 || this.mergeTextField.getText()==null){
            this.unableToMerge.setVisible(true);
        }
        else{

            Room r=(Room)this.chooseRoomMerge.getSelectionModel().getSelectedItem();
            if (!this.graph.getGraph().mergeRooms(r, (Room)this.nodeList.getSelectionModel().getSelectedItem()
                    ,this.mergeTextField.getText())){
                this.unableToMerge.setVisible(true);

            }
            else{
                this.setNodeListValues();
                //TODO: save here
                AutoSave.specialSave(this.graph.getGraph());
                AutoSave.debug();
            }
            this.mergeTextField.setText(null);
        }
    }

    public void clickSplit(Event event) {
    }
}

final class AutoSave
{
    private static Map<Integer,File> previousVersions=new TreeMap<>();
    private static int count=0;

    private static File makeFile() {
        //return new File("version"+count+".xml");
        File file = null;
        try {
            FileSystemView fileSystemView=FileSystemView.getFileSystemView();
            String desktop=fileSystemView.getHomeDirectory().toString();
            file=new File(desktop+"\\version"+count+".xml");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    static void specialSave(Object o)
    {

        Serializer serializer=new Serializer(AutoSave.makeFile());
        try {
            serializer.saveXML(o);
            previousVersions.put(count,serializer.getFile());
            count++;
        } catch (IOException e) {
            e.printStackTrace();
        }

       // previousVersions.put(count,makeFile());
       // count++;
    }
    static void debug(){
        //System.out.print("Hello static");
        for (Map.Entry<Integer,File> entry : previousVersions.entrySet()) {
            System.out.println("Key: " + entry.getKey());
         //   FileSystemView fileSystemView=FileSystemView.getFileSystemView();
           // String desktop=fileSystemView.getHomeDirectory().toString();
         //   System.out.print(desktop);
        }
    }
}
