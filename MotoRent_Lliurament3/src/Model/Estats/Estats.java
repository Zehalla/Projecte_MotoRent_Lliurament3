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
        return new EstatClientAmbReserva();
    }

    public static EstatClientDesactivat getEstatClientDesactivat() {
        return new EstatClientDesactivat();
    }

    public static EstatClientSenseReserva getEstatClientSenseReserva() {
        return new EstatClientSenseReserva();
    }

    public static EstatMotoDisponible getEstatMotoDisponible() {
        return new EstatMotoDisponible();
    }

    public static EstatMotoReparant getEstatMotoReparant() {
        return new EstatMotoReparant();
    }

    public static EstatMotoReservada getEstatMotoReservada() {
        return new EstatMotoReservada();
    }

    
}
