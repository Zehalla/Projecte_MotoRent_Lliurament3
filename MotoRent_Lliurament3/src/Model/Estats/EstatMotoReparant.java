package Model.Estats;

/**
 * S'ha implementat el patró Singleton per a cada un dels estats.
 */
public class EstatMotoReparant extends EstatMoto{
    private static EstatMotoReparant instance = null;
    /**
     * Constructor de la classe
     * Esta privat perque ningú apart de la propia classe
     * el pugui cridar
     */
    private EstatMotoReparant(){

    }
    /**
     * Metode que instancia la classe si es la primera vegada que s'ha cridat
     * si no, retorna la instancia ja creada de la classe
     * @return instance
     */
    static EstatMotoReparant getInstance(){
        if (instance == null){
            instance = new EstatMotoReparant();
        }
        return instance;        
    }
}
