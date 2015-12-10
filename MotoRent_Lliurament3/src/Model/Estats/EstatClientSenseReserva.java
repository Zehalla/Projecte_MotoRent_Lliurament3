/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Estats;

/**
 *
 * @author Adry
 */
public class EstatClientSenseReserva extends EstatClient{
    private static EstatClientSenseReserva instance = null;

    private EstatClientSenseReserva() {
        this.tipus = "Sense Reserva";
    }
    
    public static EstatClientSenseReserva getInstance(){
        if (instance == null){
            instance = new EstatClientSenseReserva();
        }
        return instance;
    }
    
    @Override
    public String getTipus(){
        return this.tipus;
    }
}
