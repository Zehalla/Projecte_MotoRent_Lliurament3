package Model.Estats;

/**
 *S'ha implementat el patró Singleton per a cada un dels estats.
 */
public class EstatClientSenseReserva extends EstatClient{
    private static EstatClientSenseReserva instance = null;
    
    private EstatClientSenseReserva(){
        
    }

    static EstatClientSenseReserva getInstance() {
        if (instance == null){
            instance = new EstatClientSenseReserva();
        }
        return instance;
    }
}
