/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImagePreProcesingPackage;

/**
 *
 * @author tifuivali
 * Exceptie ce va fi aruncata atunci cand se apeleaza getProcesedImage din ImagePreprocesor dar nu au fost apelate functia de
 * trunchiere
 */
public class TruncatingException extends Exception{
    
    public TruncatingException()
    {
        super("Image cannot be truncated!");
    }
    
}
