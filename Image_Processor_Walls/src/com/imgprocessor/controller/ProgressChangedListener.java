/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imgprocessor.controller;

import com.imgprocessor.controller.ProgressChangedAction;

/**
 *
 * @author tifuivali
 */
public interface ProgressChangedListener {
    
    void onValueChanged(ProgressChangedAction a);
}
