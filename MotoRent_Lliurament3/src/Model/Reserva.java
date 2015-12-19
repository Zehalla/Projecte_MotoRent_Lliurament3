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
public class Reserva {
    private final String id;
    private float preu;
    private boolean penalitzacioTemps;
    private boolean penalitzacioMoto;
    private float penalitzacio;
    private Data dataInicial;
    private Data dataFinal;
    private Local localInicial;
    private Local localFinal;
    private Client clientReserva;
    private Moto motoReserva;
    
    public Reserva(String id,float preu,boolean penalitzacioTemps,boolean penalitzacioMoto,float penalitzacio, Data dataInicial, Data dataFinal, Local localInicial, Local localFinal, Client clientReserva, Moto motoReserva){
        this.id = id;
        this.preu = preu;
        this.penalitzacioTemps = penalitzacioTemps;
        this.penalitzacioMoto = penalitzacioMoto;
        this.penalitzacio = penalitzacio;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.localInicial = localInicial;
        this.localFinal = localFinal;
        this.clientReserva = clientReserva;
        this.motoReserva = motoReserva;
    }
    

    
    public float getPreu(){
        return preu;
    }
    
    public float getPenalitzacio(){
        return penalitzacio;
    }

    public Client getClientReserva(){
        return clientReserva;
    }
        
    public Moto getMotoReserva(){
        return motoReserva;
    }
    
    public Local getLocalInicial(){
        return localInicial;
    }
    
    public Local getLocalFinal(){
        return localFinal;
    }
    
    public int obtenirDataIniciReserva(){
        return Integer.parseInt(this.dataInicial.getMes());
        
    }
    
    public void generarInformeReserva(){
        Consola.escriu(this.toString());
    }
    
    @Override
    public String toString(){
        String str;
        str = "\nReserva amb ID: " + id + "\n";
        str += "--------------------------------------\n";
	str += "Client: " + clientReserva.getId() + "\n";
	str += "Moto: " + motoReserva.getIdMoto() + "\n";
        str += "Cost: " + preu + "€\n";
        if (penalitzacioMoto){
            str += "La moto té algun desperfecte.\n";
        }else{
            str += "La moto s'ha retornat amb bon estat.\n";
        }
        if (penalitzacioTemps){
            str += "La moto no s'ha retornat a temps.\n";
        }else{
            str += "La moto s'ha retornat a temps.\n";
        }
        str += "Local d'inici: " + localInicial.toString() + "\n";
	str += "Data d'inici: " + dataInicial.toString() + "\n";
	str += "Local de finalització: " + localFinal.toString() + "\n";
	str += "Data de finalització: " + dataFinal.toString() + "\n";
        return str;
    }

    public void calcularPreu() {
        int hores;
        hores = this.dataFinal.calcularDiferencia(dataInicial);
        this.preu = (hores/24)*15 + hores%24;
        Consola.escriu("La reserva te un preu de: ");
        Consola.escriu(this.preu);
        Consola.escriu("€.\n");
        Consola.escriu("Duracio de la reserva: ");
        Consola.escriu(hores/24);
        Consola.escriu(" dia/es i ");
        Consola.escriu(hores%24);
        Consola.escriu(" hora/es.\n");
    }
}
