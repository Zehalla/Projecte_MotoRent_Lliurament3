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
