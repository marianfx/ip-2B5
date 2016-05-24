/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

/**
 *
 * @author tifuivali
 * exceptie care poate aprea in timpul procesarii
 */
public class ProcessingException extends Exception{
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 8327829810381453200L;

	public ProcessingException()
    {
        super("A erorr on processing image has ocurred!");
    }
    
    public ProcessingException(String message)
    {
        super(message);
    }
    
}
