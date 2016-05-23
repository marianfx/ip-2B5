/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.controller;

/**
 *
 * @author tifuivali
 */
public class ProgressChangedAction {
    
    private double progress=0;
    
    public ProgressChangedAction()
    {
        progress=0;
    }
    
    public  ProgressChangedAction(double value)
    {
        progress=value;
    }

    /**
     * @return the progress
     */
    public double getProgress() {
        return progress;
    }
}
