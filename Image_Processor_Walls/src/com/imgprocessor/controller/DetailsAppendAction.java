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
public class DetailsAppendAction {
    
    private String detailsAppended;
    
    public DetailsAppendAction()
    {
        detailsAppended = "";
    }
    
    public DetailsAppendAction(String detail)
    {
        this.detailsAppended = detail;
    }

    /**
     * @return the detailsAppended
     */
    public String getDetailsAppended() {
        return detailsAppended;
    }

  
    
}
