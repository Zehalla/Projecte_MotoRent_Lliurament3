package Model.Estats;

/**
 * S'ha implementat el patró Singleton per a cada un dels estats.
 */
public class EstatMotoReservada extends EstatMoto{
    private static EstatMotoReservada instance = null;
    
    private EstatMotoReservada(){

    }

    static EstatMotoReservada getInstance(){
        if (instance == null){
            instance = new EstatMotoReservada();
        }
        return instance;        
    }
}
