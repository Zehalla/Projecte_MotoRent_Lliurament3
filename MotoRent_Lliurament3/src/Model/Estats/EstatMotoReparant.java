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
public class EstatMotoReparant extends EstatMoto{
    private static EstatMotoReparant instance = null;
    
    private EstatMotoReparant(){
        this.infoMoto = "En reparacio";
    }
    
    public static EstatMotoReparant getInstance(){
        if (instance == null){
            instance = new EstatMotoReparant();
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
