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
public class NotSuportedFileFormatException extends Exception{
    
    public NotSuportedFileFormatException(String fileFormat)
    {
        super("Not Suport file format:"+fileFormat);
    }
}
