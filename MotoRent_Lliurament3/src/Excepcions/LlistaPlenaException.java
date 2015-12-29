package Excepcions;

/**
 * Classe que representa l'excepció de llista plena a l'aplicació.
 */
public class LlistaPlenaException extends Exception{
    @Override
    public String getMessage(){
        return "La llista a la que intentes afegir un objecte està plena.";
    }
}
