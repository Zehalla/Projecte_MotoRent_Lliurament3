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
public class LlistaBuidaException extends Exception{
    @Override
    public String getMessage(){
        return "La llista esta buida.";
    }
}
