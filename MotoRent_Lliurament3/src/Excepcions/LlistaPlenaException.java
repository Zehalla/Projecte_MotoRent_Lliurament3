/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepcions;

/**
 *
 * @author Adry
 */
public class LlistaPlenaException extends Exception{
    @Override
    public String getMessage(){
        return "La llista a la que intentes afegir un objecte està plena.";
    }
}
