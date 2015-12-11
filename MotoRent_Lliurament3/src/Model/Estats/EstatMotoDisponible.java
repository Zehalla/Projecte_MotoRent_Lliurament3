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
