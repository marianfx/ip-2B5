/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.controller;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author tifuivali
 */
public class ImageUpdateAction {
    
    BufferedImage updatedImage;
    
    public ImageUpdateAction(BufferedImage image)
    {
        this.updatedImage=image;
    }
    
    
    public BufferedImage getImage()
    {
        return this.updatedImage;
    }
    
    
    public Image getFXImage()
    {
       Image image= SwingFXUtils.toFXImage(updatedImage, null);
       return image;
    }
}
