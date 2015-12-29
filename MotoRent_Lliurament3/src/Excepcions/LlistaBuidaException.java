package Excepcions;

/**
 * Classe que representa l'excepció de llista buida a l'aplicació.
 */

public class LlistaBuidaException extends Exception{
    
    @Override
    /**
     * Mostra un missatge d'error relatiu a l'excepció que es tracta.
     */
    public String getMessage(){
        return "La llista esta buida.";
    }
}
