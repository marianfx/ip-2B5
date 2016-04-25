package com.imgprocessor.view;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;

import com.imgprocessor.processor.ImageProcessor;

/**
 * 
 */
public class InterpretorInterface extends JFrame implements Interpretor {

	private static final long serialVersionUID = 1L;


	/**
     * Default constructor
     */
    public InterpretorInterface() {
    	
    }

	public JSplitPane mainSplitPane;
    public JButton loadButton;
    public JButton processButton;
    public JFileChooser openFileDialog;
    public JProgressBar progressBar;
    public JLabel imageDisplay;
    public ImageProcessor imgProcessor;




    /**
     * 
     */
    public void run() {
        // TODO implement here
    }

    
    /**
     * @return
     */
    public ImageProcessor getImgProcessor() {
        // TODO implement here
        return null;
    }

    
    /**
     * @param proc
     */
    public void setImgProcessor(ImageProcessor proc) {
        // TODO implement here
    }

}