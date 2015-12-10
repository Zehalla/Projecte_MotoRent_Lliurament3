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
public class EstatMotoDisponible extends EstatMoto{
    private static EstatMotoDisponible instance = null;
    
    private EstatMotoDisponible(){
        this.infoMoto = "Disponible";
    }
    public static EstatMotoDisponible getInstance(){
        if (instance == null){
            instance = new EstatMotoDisponible();
        }
        return instance;
    }

    @Override
    public void mostrarInfoMoto() {
        Consola.escriu(this.infoMoto);
    }

    @Override
    public boolean esDisponible() {
        return true;
    }
}
