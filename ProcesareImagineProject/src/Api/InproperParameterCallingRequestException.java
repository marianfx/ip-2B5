/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Api;

/**
 *
 * @author tifuivali
 */
public class InproperParameterCallingRequestException extends Exception{
    
    public InproperParameterCallingRequestException()
    {
        super("Canot calling request with first parameter null if second parameter type is External!");
    }
}
