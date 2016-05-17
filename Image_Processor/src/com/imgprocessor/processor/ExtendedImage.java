package com.imgprocessor.processor;

import java.io.File;

import javafx.scene.image.Image;

/**
 * 
 */
public class ExtendedImage extends Image {
	
	
    private ImageState state;
    
    private int totalWidth;
    private int totalHeight;

    private int truncWidth;
    private int truncHeight;

    private int truncX;
    private int truncY;

	public ExtendedImage(File imageFile) {
		
		super(imageFile.getAbsolutePath());
		
		this.totalWidth = (int)getWidth();
		this.totalHeight = (int)getHeight();
		
		this.state = ImageState.Loaded;
	}

	/**
	 * @return the state
	 */
	public ImageState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ImageState state) {
		this.state = state;
	}
	
	/*no setters for width and height since you can't set it from anymore else*/

	/**
	 * @return the totalWidth
	 */
	public int getTotalWidth() {
		return totalWidth;
	}

	/**
	 * @return the totalHeight
	 */
	public int getTotalHeight() {
		return totalHeight;
	}

	/**
	 * @return the truncWidth
	 */
	public int getTruncWidth() {
		return truncWidth;
	}

	/**
	 * @param truncWidth the truncWidth to set
	 */
	public void setTruncWidth(int truncWidth) {
		this.truncWidth = truncWidth;
	}

	/**
	 * @return the truncHeight
	 */
	public int getTruncHeight() {
		return truncHeight;
	}

	/**
	 * @param truncHeight the truncHeight to set
	 */
	public void setTruncHeight(int truncHeight) {
		this.truncHeight = truncHeight;
	}

	/**
	 * @return the truncX
	 */
	public int getTruncX() {
		return truncX;
	}

	/**
	 * @param truncX the truncX to set
	 */
	public void setTruncX(int truncX) {
		this.truncX = truncX;
	}

	/**
	 * @return the truncY
	 */
	public int getTruncY() {
		return truncY;
	}

	/**
	 * @param truncY the truncY to set
	 */
	public void setTruncY(int truncY) {
		this.truncY = truncY;
	}

}