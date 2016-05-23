/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.api;

/**
 *
 * @author tifuivali
 */
public class NotSupportedFileFormatException extends Exception{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -312469944824267789L;

	public NotSupportedFileFormatException(String fileFormat)
    {
        super("File format: " + fileFormat + " not supported.");
    }
}
