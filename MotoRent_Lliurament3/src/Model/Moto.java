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
    private Reserva reservaMoto;
    private EstatMoto estatMoto;

    public boolean checkEstat() {
        return true;  //TODO this
    }

    void mostrarDadesMoto() {
        Consola.escriu("-Moto:");
        Consola.escriu("matricula: " + matricula);
        Consola.escriu("model :" + model);
        Consola.escriu("color :" + color);
    }

    public String getTipus() {
        if(estatMoto instanceof EstatMotoDisponible){
            return "Disponible";
        }else if(estatMoto instanceof EstatMotoReservada){
            return "Reservada";
        }else if(estatMoto instanceof EstatMotoReparant){
            return "Reparant";
        }
        return "Error tipus moto";
    }
    
    public void setTipus(String tipus) {
        switch (tipus) {
            case "Disponible":
                estatMoto = new EstatMotoDisponible();
                break;
        //Si arribem aqui, error
            case "Reservda":
                estatMoto = new EstatMotoReservada();
                break;
            case "Reparant":
                estatMoto = new EstatMotoReparant();
                break;
        }
    }
    public Moto(String idMoto, String matricula, String model, String color, Reserva reservaMoto, EstatMoto estatMoto){
        this.idMoto = idMoto;
        this.matricula = matricula;
        this.model = model;
        this.color = color;
        this.reservaMoto = reservaMoto;
        this.estatMoto = estatMoto;
    }
    
    public String getIdMoto(){
        return idMoto;
    }
    
    
    public void setEstat(String estat){
        switch (estat) {
            case "reservada":
                this.estatMoto = new EstatMotoReservada();
                break;
            case "disponible":
                this.estatMoto = new EstatMotoDisponible();
                break;
            default:
                this.estatMoto = new EstatMotoReparant();
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
        str += "Estat: " + estatMoto.getTipus() + "\n";
        return str;
    }
    
}
