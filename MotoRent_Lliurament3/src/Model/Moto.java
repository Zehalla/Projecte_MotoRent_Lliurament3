/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Estats.*;
import Vista.Consola;

/**
 *
 * @author atorraag7.alumnes
 */
public class Moto {
    private String idMoto;
    private String matricula;
    private String model;
    private String color;
    private EstatMoto estatMoto;

    public Moto(){
    }
    
    public Moto(String idMoto, String matricula, String model, String color, EstatMoto estatMoto){
        this.idMoto = idMoto;
        this.matricula = matricula;
        this.model = model;
        this.color = color;
        this.estatMoto = estatMoto;
    }
    
    
    public boolean checkEstat() {
        return true;  //TODO this
    }

    public String getEstat(){
        return this.estatMoto.getEstat();
    }
    
    
    public String getIdMoto(){
        return idMoto;
    }
    
    public void setEstat(String estat){
        switch (estat) {
            case "reservada":
                this.estatMoto = Estats.getEstatMotoReservada();
                break;
            case "disponible":
                this.estatMoto = Estats.getEstatMotoDisponible();
                break;
            default:
                this.estatMoto = Estats.getEstatMotoReparant();
                break;
        }
    }
    
    public String toString(){
        String str;
        str = "\nmoto amb ID: " + idMoto + "\n";
	str += "--------------------------------------\n";
	str += "Matrícula: " + matricula + "\n";
        str += "Model: " + model + "\n";
        str += "Color: " + color + "\n";
        str += "Estat: " + estatMoto.getEstat() + "\n";
        return str;
    }
    
}
