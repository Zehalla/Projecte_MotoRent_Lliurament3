/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Estats;

/**
 *S'ha implementat el patró Singleton per a cada un dels estats.
 * @author Adry
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
