package Model.Estats;

/**
 * S'ha implementat el patró Singleton per a cada un dels estats.
 */
public class EstatMotoDisponible extends EstatMoto{
    private static EstatMotoDisponible instance = null;
    /**
     * Constructor de la classe
     * Esta privat perque ningú apart de la propia classe
     * el pugui cridar
     */
    private EstatMotoDisponible(){
        
    }
    /**
     * Metode que instancia la classe si es la primera vegada que s'ha cridat
     * si no, retorna la instancia ja creada de la classe
     * @return instance
     */
    static EstatMotoDisponible getInstance(){
        if (instance == null){
            instance = new EstatMotoDisponible();
        }
        return instance;
    }
}
