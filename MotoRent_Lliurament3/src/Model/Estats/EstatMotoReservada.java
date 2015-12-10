/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Estats;

import Vista.Consola;

/**
 *
 * @author Adry
 */
public class EstatMotoReservada extends EstatMoto{
    private static EstatMotoReservada instance = null;
    
    private EstatMotoReservada(){
        this.infoMoto = "Reservada";
    }

    public static EstatMotoReservada getInstance(){
        if (instance == null){
            instance = new EstatMotoReservada();
        }
        return instance;        
    }
    
    @Override
    public void mostrarInfoMoto() {
        Consola.escriu(infoMoto);
    }

    @Override
    public boolean esDisponible() {
        return false;
    }
}
