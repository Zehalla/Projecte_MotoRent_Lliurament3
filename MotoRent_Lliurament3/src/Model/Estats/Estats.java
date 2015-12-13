/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Estats;

/**
 *
 * @author aleix
 */
public class Estats {

    public static EstatClientAmbReserva getEstatClientAmbReserva() {
        return EstatClientAmbReserva.getInstance();
    }

    public static EstatClientDesactivat getEstatClientDesactivat() {
        return EstatClientDesactivat.getInstance();
    }

    public static EstatClientSenseReserva getEstatClientSenseReserva() {
        return EstatClientSenseReserva.getInstance();
    }

    public static EstatMotoDisponible getEstatMotoDisponible() {
        return EstatMotoDisponible.getInstance();
    }

    public static EstatMotoReparant getEstatMotoReparant() {
        return EstatMotoReparant.getInstance();
    }

    public static EstatMotoReservada getEstatMotoReservada() {
        return EstatMotoReservada.getInstance();
    }

    
}
