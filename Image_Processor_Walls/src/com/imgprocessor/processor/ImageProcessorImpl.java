/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

import com.imggraph.Algorithm;
import com.imggraph.Connector;
import com.imgmodel.buildingParts.Coordinates;
import com.imgmodel.buildingParts.Door;
import com.imgmodel.buildingParts.Window;
import com.imgmodel.graphModel.Graph;
import com.imgmodel.graphModel.Room;
import com.imgmodel.main.GraphMain;
import com.imgmodel.serialization.Serializer;
import com.imgprocessor.controller.DetailsAppendAction;
import com.imgprocessor.controller.DetailsAppendListener;
import com.imgprocessor.controller.ImageUpdateAction;
import com.imgprocessor.controller.ImageUpdateListener;
import com.imgprocessor.controller.ProgressChangedAction;
import com.imgprocessor.controller.ProgressChangedListener;
import com.imgprocessor.model.Line;
import com.imgprocessor.model.Line.line_type;
import com.imgprocessor.model.Representation;
import com.imgprocessor.opencvtest.HoughLineDetection;
import com.imgprocessor.opencvtest.LineProcessor;
import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;

import java.awt.image.BufferedImage;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
/**
 *
 * @author tifuivali
 */
public class ImageProcessorImpl implements ImageProcessor {
    
    private ImagePreProcessor imagePreProcessor;
    
    private Vector<DetailsAppendListener> detailsAppendListeners;
    private Vector<ProgressChangedListener> progressChangedListeners;
    private Vector<ImageUpdateListener> imageChangedListeners;
    
    private File ImageFile;
    private ImageProcessorImpl thisReff = this;
    public static boolean DETECT_ONLY_WALLS = false;
    public volatile static String SAVED_XML_PATH = null;
    
    public volatile Representation imageRepresentation;
    
    
    
    public ImageProcessorImpl(File imageFile) throws FileNotFoundException {
    	
        this.imagePreProcessor = new ImagePreProcessorImpl(imageFile);
        this.detailsAppendListeners = new Vector<>();
        this.progressChangedListeners = new Vector<>();
        this.imageChangedListeners = new Vector<>();
    	this.ImageFile = imageFile;
    	
    }
    
    /**
     * @return the imagePreprocesor
     */
    public ImagePreProcessor getImagePreprocessor() {
        return imagePreProcessor;
    }

    /**
     * @param imagePreprocesor the imagePreprocesor to set
     */
    public void setImagePreprocessor(ImagePreProcessor imagePreprocessor) {
        this.imagePreProcessor = imagePreprocessor;
    }

    @Override
    public void process() 
    		throws 	ValidatingException, TruncatingException, ProcessingException {
    	
    	// here, order kindof matters
    	//// loads the opencv 249 library for features2d
		System.loadLibrary("opencv_java249");
		// loads the opencv 310 library for fillConvexPoly and all the others
		System.loadLibrary("opencv_java310");
		
		imageRepresentation = new Representation();
		
		
		// run template detection && line detection in another thread
		new Thread(){
			
			public void run() {
				
				// object detection
				if(!DETECT_ONLY_WALLS){
					DetectObject objectDetector = new DetectObject(ImageFile.getAbsolutePath(), thisReff);
			        objectDetector.detectAllObject();
		        }
		        
				// line detection
		        HoughLineDetection houghLineDetection = new HoughLineDetection(DetectObject.TEMPLATE_OUTPUT_PATH, thisReff);
		        List<Line> detectedWalls = houghLineDetection.detectLines();
		        imageRepresentation.populateWalls(detectedWalls);
		        
		        thisReff.appendDetail("Walls detected: " + detectedWalls.size());
		        int k = 1;
		        for (Line line : detectedWalls) {
					thisReff.appendDetail(k + ". (" + (int)line.x1 + ", " + (int)line.y1 + ") --> " + "(" + (int)line.x2 + ", " + (int)line.y2 + ")");
					k++;
				}
		        
		        // till here, detected walls nice, united them
		        
		        //here, transform the doors & windows too.
		        List<Line> theDoors = new ArrayList<>();
		        for (Door door : imageRepresentation.getDoors()) {
		        	Coordinates start 	= door.getStart();
		        	Coordinates end 	= door.getEnd();
		        	Point s = new Point(start.getX(), start.getY());
		        	Point e = new Point(end.getX(), end.getY());
		        	Line lDoor = new Line(s, e);
		        	lDoor.type = line_type.DOOR;
		        	theDoors.add(lDoor);
				}
		        
		        Mat blackMatrix = Imgcodecs.imread(LineProcessor.drawID + ". lineDetection.png");
		        theDoors = LineProcessor.uniteObjectsWithWalls(blackMatrix, detectedWalls, theDoors, 50, true);
		        LineProcessor.drawLines(theDoors, blackMatrix, new Scalar(255, 0, 255), 2, false, true, thisReff);
		        
		        // try uniting them
		        
		        List<Line> theWindows = new ArrayList<>();
		        for(Window window: imageRepresentation.getWindows()){
		        	
		        	Coordinates start 	= window.getStart();
		        	Coordinates end 	= window.getEnd();
		        	Point s = new Point(start.getX(), start.getY());
		        	Point e = new Point(end.getX(), end.getY());
		        	Line lWindow = new Line(s, e);
		        	lWindow.type = line_type.WINDOW;
		        	theWindows.add(lWindow);
		        }
		        
		        blackMatrix = Imgcodecs.imread(LineProcessor.drawID + ". lineDetection.png");
		        theWindows = LineProcessor.uniteObjectsWithWalls(blackMatrix, detectedWalls, theWindows, 50, true);
		        LineProcessor.drawLines(theWindows, blackMatrix, new Scalar(255, 0, 0), 2, false, true, thisReff);
		       
		        
		        
		        // now all good, time to put them back (convert, fucking convert)
		        // 1. The DOORS
		        imageRepresentation.clearDoors();
		        thisReff.appendDetail("The new DOORS coordinates (fixed to the walls): ");
		        k = 1;
		        for(Line door: theDoors){
		        	
		        	Coordinates start 	= new Coordinates((float)door.getStartingPoint().x, (float)door.getStartingPoint().y);
		        	Coordinates end 	= new Coordinates((float)door.getEndingPoint().x, (float)door.getEndingPoint().y);
		        	Door theDoor = new Door(start, end);
		        	imageRepresentation.addDoor(theDoor);
		        	thisReff.appendDetail(k + ". (" + (int)start.getX() + ", " + (int)start.getY() + ") -> (" + (int)end.getX() + ", " + (int)end.getY() + ")");
		        	k++;
		        }
		        
		        
		        
		        // 2. The WINDOWS
		        imageRepresentation.clearWindows();
		        thisReff.appendDetail("The new WINDOWS coordinates (fixed to the walls): ");
		        k = 1;
		        for(Line window: theWindows){
		        	
		        	Coordinates start 	= new Coordinates((float)window.getStartingPoint().x, (float)window.getStartingPoint().y);
		        	Coordinates end 	= new Coordinates((float)window.getEndingPoint().x, (float)window.getEndingPoint().y);
		        	Window theWindow = new Window(start, end);
		        	imageRepresentation.addWindow(theWindow);
		        	thisReff.appendDetail(k + ". (" + (int)start.getX() + ", " + (int)start.getY() + ") -> (" + (int)end.getX() + ", " + (int)end.getY() + ")");
		        	k++;
		        }
		        
		        
		        //xml encode
		        thisReff.setProgress(0);
		        thisReff.appendDetail("Serializing the representation into 'Representation.xml'...");
				try {
					  
					SAVED_XML_PATH = "Representation.xml";
					XMLEncoder  myEncoder = new XMLEncoder(new FileOutputStream (SAVED_XML_PATH));
					myEncoder.writeObject(imageRepresentation);
					myEncoder.flush();
					myEncoder.close();

			        thisReff.setProgress(100);
			        thisReff.appendDetail("Finished serialization.");
			        
			        
			        // RUN THE Graph Module Algorithm
			        runGraphALgorithm();
			        
					
				} catch (FileNotFoundException e) {
					thisReff.appendDetail("FAILED!");
					e.printStackTrace();
				}
			};
			
		}.start();
		
    }
    
    public void runGraphALgorithm(){
    	
    	// HERE, RUNNING ALGORITHM
    	thisReff.appendDetail("Running the rooms graph detection...");
    	
        Algorithm algorithm = new Algorithm();// initialized and read data from the file
        List<Room> rooms = algorithm.getRoomList();
        
        if(rooms.size() == 0){
        	
        	thisReff.appendDetail("No rooms detected.");
        }
        else{
        	
        	thisReff.appendDetail("Rooms: ");
        	
			for(int i=0;i<rooms.size();i++){
				
				String strin = "Room " + (i+1) + ": \n";
				
				for(int j = 0; j < rooms.get(i).getParts().size(); j++){
					
					strin += "     " + rooms.get(i).getParts().get(j).toString() + " (" + 
							(int)rooms.get(i).getParts().get(j).getStart().getX()+", " + 
							(int)rooms.get(i).getParts().get(j).getStart().getY() + ") --> (" + 
							(int)rooms.get(i).getParts().get(j).getEnd().getX()+", " + 
							(int)rooms.get(i).getParts().get(j).getEnd().getY() + ")\n";
					
				}
				
				appendDetail(strin);
				
			}
			
        }
        
        Connector c = new Connector(rooms);
		c.connect();
		algorithm.getStairsMatch();
		
		rooms = c.getRooms();
		
		Graph g = new Graph(rooms, c.getGraphEdges());
		GraphMain.graph = g;
		
		Serializer serializer = new Serializer(new File("Graph.xml"));
		try {
			serializer.saveXML(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		thisReff.appendDetail("Finished. Now you can close this window.");
    }

    
    public void updateImage(BufferedImage img)
    {
    	for(ImageUpdateListener listener: imageChangedListeners)
        {
            listener.onUpdatePerformed(new ImageUpdateAction(img));
        }
    }
    
    
    public void appendDetail(String detail)
    {
       for(DetailsAppendListener listener:detailsAppendListeners)
       {
           listener.onAppendPerformed(new DetailsAppendAction(detail + "\r\n"));
       }
    }
    
    public void setProgress(double value)
    {
       for(ProgressChangedListener listener:progressChangedListeners)
       {
           listener.onValueChanged(new ProgressChangedAction(value));
       }
    }

    @Override
    public void addProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.add(listener);
        this.getImagePreprocessor().addProgressChangedListener(listener);
    }

    @Override
    public void removeProgressChangedListener(ProgressChangedListener listener) {
        this.progressChangedListeners.remove(listener);
        this.getImagePreprocessor().removeProgressChangedListener(listener);
    }

    @Override
    public void addDetailsAppendListener(DetailsAppendListener listener) {
    	
       this.detailsAppendListeners.add(listener);
       this.getImagePreprocessor().addDetailsAppendListener(listener);
    }
    
    public void addImageChangeListers(ImageUpdateListener listener) {
    	
        this.imageChangedListeners.add(listener);
     }

    @Override
    public void removeDetailsAppendListener(DetailsAppendListener listener) {
       this.detailsAppendListeners.remove(listener);
       this.getImagePreprocessor().removeDetailsAppendListener(listener);
    }

    
}
