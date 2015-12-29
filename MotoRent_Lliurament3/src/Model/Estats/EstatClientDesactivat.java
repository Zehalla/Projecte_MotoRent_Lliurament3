package Model.Estats;

/**
 *S'ha implementat el patró Singleton per a cada un dels estats.
 *
 */
public class EstatClientDesactivat extends EstatClient{
    private static EstatClientDesactivat instance = null;
    
    private EstatClientDesactivat(){
        
    }

    static EstatClientDesactivat getInstance() {
        if (instance == null){
            instance = new EstatClientDesactivat();
        }
        return instance;
    }
}
