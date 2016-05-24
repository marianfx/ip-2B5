package com.imgmodel.serialization;

import javafx.stage.FileChooser;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer
{

  public FileChooser saveFileChooser; //tine de logica GUI-ului, nu prea are treaba aici
  private File file;

  public Serializer(File file){
    this.file=file;
  }

  public void saveXML(Object o) throws IOException  //nu inteleg de ce ati facut sa returneze Boolean metodele astea
  {
    XMLEncoder xmlEncoder=new XMLEncoder(new FileOutputStream(file));
    xmlEncoder.writeObject(o);
    xmlEncoder.close();
  }

  public File getFile(){return file;}
  public void setFile(File file){this.file=file;}
}