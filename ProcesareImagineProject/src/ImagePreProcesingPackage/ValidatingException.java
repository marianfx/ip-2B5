/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImagePreProcesingPackage;

/**
 *
 * @author tifuivali
 * exceptie ce va fi aruncata atunci cand se apleaza functia de trunchiere inainte sa fie apelata functia de validare.
 */
public class ValidatingException extends Exception {
    
    public ValidatingException()
    {
        super("Not Valid Image!");
    }
}
