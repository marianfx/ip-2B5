/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcesing;

/**
 *
 * @author tifuivali
 * exceptie care poate aprea in timpul procesarii
 */
public class ProcesingException extends Exception{
    
    public ProcesingException()
    {
        super("A erorr on processing image has ocurred!");
    }
    
    public ProcesingException(String message)
    {
        super(message);
    }
    
}
