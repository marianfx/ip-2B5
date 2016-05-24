package com.imgmodel.serialization;
import com.imgmodel.graphModel.Graph;
import javafx.stage.FileChooser;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Deserializer
{
  public Deserializer(File file){
    this.file=file;
  }

  public FileChooser loadFileChooser; //tine de logica GUI-ului, nu prea are treaba aici
  private File file;

  public Graph loadXML() throws IOException
  {
    XMLDecoder xmlDecoder=new XMLDecoder(new FileInputStream(file));
    Graph g= (Graph) xmlDecoder.readObject();
    xmlDecoder.close();
    return g;
  }

  public void setFile(File file){this.file=file;}
  public File getFile(){return file;}
}