package Model.Estats;

/**
 * S'ha implementat el patró Singleton per a cada un dels estats.
 */
public class EstatMotoReservada extends EstatMoto{
    private static EstatMotoReservada instance = null;
    /**
     * Constructor de la classe
     * Esta privat perque ningú apart de la propia classe
     * el pugui cridar
     */
    private EstatMotoReservada(){

    }
    /**
     * Metode que instancia la classe si es la primera vegada que s'ha cridat
     * si no, retorna la instancia ja creada de la classe
     * @return instance
     */
    static EstatMotoReservada getInstance(){
        if (instance == null){
            instance = new EstatMotoReservada();
        }
        return instance;        
    }
}
