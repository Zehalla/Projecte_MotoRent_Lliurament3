package Model.Estats;

/**
 *S'ha implementat el patró Singleton per a cada un dels estats.
 *
 */
public class EstatClientDesactivat extends EstatClient{
    private static EstatClientDesactivat instance = null;
    /**
     * Constructor de la classe
     * Esta privat perque ningú apart de la propia classe
     * el pugui cridar
     */
    private EstatClientDesactivat(){
        
    }
    /**
     * Metode que instancia la classe si es la primera vegada que s'ha cridat
     * si no, retorna la instancia ja creada de la classe
     * @return instance
     */
    static EstatClientDesactivat getInstance() {
        if (instance == null){
            instance = new EstatClientDesactivat();
        }
        return instance;
    }
}
