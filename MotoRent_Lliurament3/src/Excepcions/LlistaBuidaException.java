package Excepcions;

/**
 * Classe que representa l'excepció de llista buida a l'aplicació.
 */

public class LlistaBuidaException extends Exception{
    @Override
    public String getMessage(){
        return "La llista esta buida.";
    }
}
