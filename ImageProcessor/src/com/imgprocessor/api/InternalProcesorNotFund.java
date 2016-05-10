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
public class InternalProcesorNotFund  extends Exception{
    
    public InternalProcesorNotFund()
    {
        super("Internal processor not found or intern image is unitialized!");
    }
}
