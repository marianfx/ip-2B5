/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

/**
 *
 * @author tifuivali
 * Exceptie ce va fi aruncata atunci cand se apeleaza getProcesedImage din ImagePreprocesor dar nu au fost apelate functia de
 * trunchiere
 */
public class TruncatingException extends Exception{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8672451739489881007L;

	public TruncatingException()
    {
        super("Image cannot be truncated!");
    }
    
}
