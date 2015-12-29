package Model.Estats;

/**
 * S'ha implementat el patró Singleton per a cada un dels estats.
 */
public class EstatMotoReparant extends EstatMoto{
    private static EstatMotoReparant instance = null;
    
    private EstatMotoReparant(){

    }
    
    static EstatMotoReparant getInstance(){
        if (instance == null){
            instance = new EstatMotoReparant();
        }
        return instance;        
    }
}
