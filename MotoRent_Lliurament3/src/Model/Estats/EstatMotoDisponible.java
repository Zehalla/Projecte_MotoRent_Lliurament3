package Model.Estats;

/**
 * S'ha implementat el patró Singleton per a cada un dels estats.
 */
public class EstatMotoDisponible extends EstatMoto{
    private static EstatMotoDisponible instance = null;
    
    private EstatMotoDisponible(){
        
    }
    static EstatMotoDisponible getInstance(){
        if (instance == null){
            instance = new EstatMotoDisponible();
        }
        return instance;
    }
}
