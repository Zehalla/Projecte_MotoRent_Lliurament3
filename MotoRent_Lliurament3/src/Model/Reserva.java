/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
    private String localInicial;
    private String localFinal;
    private String clientReserva;
    private String motoReserva;
    
    public Reserva(String id,float preu,boolean penalitzacioTemps,boolean penalitzacioMoto,float penalitzacio, Data dataInicial, Data dataFinal, String localInicial, String localFinal, String clientReserva, String motoReserva){
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

    public String getClientReserva(){
        return clientReserva;
    }
        
    public String getMotoReserva(){
        return motoReserva;
    }
    
    @Override
    public String toString(){
        String str = null;
        str = "\nReserva amb ID: " + id + "\n";
        str += "--------------------------------------\n";
	str += "Client: " + clientReserva.toString() + "\n";
	str += "Moto: " + motoReserva.toString() + "\n";
        str += "Cost: " + preu + "€\n";
        str += "Local d'inici: " + localInicial.toString() + "\n";
	str += "Data d'inici: " + dataInicial.toString() + "\n";
	str += "Local de finalització: " + localFinal.toString() + "\n";
	str += "Data de finalització: " + dataFinal.toString() + "\n";
        return str;
    }
}
