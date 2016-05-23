/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.processor;

/**
 *
 * @author tifuivali
 * exceptie ce va fi aruncata atunci cand se apleaza functia de trunchiere inainte sa fie apelata functia de validare.
 */
public class ValidatingException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6780377128639881691L;

	public ValidatingException()
    {
        super("Not Valid Image!");
    }
}
