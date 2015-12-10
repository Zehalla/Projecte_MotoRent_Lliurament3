/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Vista.Consola;

/**
 *
 * @author atorraag7.alumnes
 */
public class Direccio {
    private String carrer;
    private String numero;
    private String codiPostal;
    private String poblacio;
    
    
    public Direccio(){
        
    }
    public Direccio(String carrer, String numero, String codiPostal, String poblacio){
        this.carrer = carrer;
        this.numero = numero;
        this.codiPostal = codiPostal;
        this.poblacio = poblacio;
    }
    
    /**
     * Métode que permet introduir les dades d'una Direcció.
     */
    public void introduirDades(){
        Consola.escriu("Introdueixi el carrer: ");
        this.carrer = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el numero de portal: ");
        this.numero = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el codi postal: ");
        this.codiPostal = Consola.llegeixString();
        
        Consola.escriu("Introdueixi la poblacio: ");
        this.poblacio = Consola.llegeixString();    
    }
    
    public void mostrarDades(){
        Consola.escriu("-Direccio:");
        Consola.escriu("Carrer: "+carrer);
        Consola.escriu("codiPostal: " + codiPostal);
        Consola.escriu("numero: " + numero);
        Consola.escriu("poblacio: " + poblacio);
    }
    @Override
    public String toString(){
        String str = null;
        str = "Carrer: "+carrer+"\n";
        str += "Número: "+numero+"\n";
        str += "Codi Postal: "+codiPostal+"\n";
        str += "Població: "+poblacio;
        return str;
    }
}
