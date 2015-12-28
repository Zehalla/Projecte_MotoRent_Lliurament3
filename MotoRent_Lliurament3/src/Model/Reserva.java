/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Excepcions.LlistaPlenaException;
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
    
    public Reserva(String id,float preu,boolean penalitzacioTemps,boolean penalitzacioMoto,float penalitzacio, String dataInicial, String horaInicial, String dataFinal, String horaFinal, Local localInicial, Local localFinal, Client clientReserva, String idMoto){
        this.id = id;
        this.preu = preu;
        this.penalitzacioTemps = penalitzacioTemps;
        this.penalitzacioMoto = penalitzacioMoto;
        this.penalitzacio = penalitzacio;
        this.dataInicial = crearData(dataInicial, horaInicial);
        this.dataFinal = crearData(dataFinal, horaFinal);
        this.localInicial = localInicial;
        this.localFinal = localFinal;
        this.clientReserva = clientReserva;
        this.motoReserva = localFinal.getMoto(idMoto);
    }
        //Constructor necessari en fer Reserva. esta fet de manera que no te dependencia de dades si es fa desde MotoREnt. 
    public Reserva(String id,float preu,boolean penalitzacioTemps,boolean penalitzacioMoto,float penalitzacio, String dataInicialS, String dataFinalS, Local localInicial, Local localFinal, Client clientReserva, String numMotoReserva){
        this.id = id;
        this.preu = preu;
        this.penalitzacioTemps = penalitzacioTemps;
        this.penalitzacioMoto = penalitzacioMoto;
        this.penalitzacio = penalitzacio;
        this.dataInicial = new Data(dataInicialS);
        this.dataFinal = new Data(dataFinalS);
        this.localInicial = localInicial;
        this.localFinal = localFinal;
        this.clientReserva = clientReserva;
        this.motoReserva = localInicial.getMoto(numMotoReserva); //Si no volem restringir a que sigui un numero de posicio o la id, podem fer un try
    }
    
    public String getId(){
        return id;
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
        hores = dataFinal.calcularDiferencia(dataInicial);
        this.preu = (hores/24)*15 + hores%24;
        if (this.clientReserva.getVip()){
           this.preu = (float) (this.preu*0.9);
        }
        Consola.escriu("La reserva te un preu de: ");
        Consola.escriu(this.preu);
        Consola.escriu("€.\n");
        Consola.escriu("Duracio de la reserva: ");
        Consola.escriu(hores/24);
        Consola.escriu(" dia/es i ");
        Consola.escriu(hores%24);
        Consola.escriu(" hora/es.\n");
    }
    
    public void cobrarReserva() {
        float diferencia;
        clientReserva.setEstat("NO RESERVA");
        Data dataEntrega = new Data(); 
        diferencia = dataEntrega.calcularDiferencia(dataFinal);
        
        if(diferencia > 0.0f){
            penalitzacio = diferencia*2;
            penalitzacioTemps = true;
            preu += penalitzacio;   
        }
        
        gestionarAveria();
    }
    
    private void gestionarAveria() {
        String opcio;
        boolean error = true;
        float reparacio;
        
        while(error){
            Consola.escriu("Mostra la moto cap averia? (Y/N)");
            opcio = Consola.llegeixString();
            
            if(opcio.equals("N")){
                error = false;
                motoReserva.setEstat("Disponible");
            }else if(opcio.equals("Y")){
                error = false;
                motoReserva.setEstat("Reparant");
                
                Consola.escriu("Introdueix el preu de la reparacio: ");
                reparacio = Float.parseFloat(Consola.llegeixString()); //Si no funciona partir en dos declaraciones
                
                preu += reparacio;
                penalitzacioMoto = true;
                
                clientReserva.afegirFalta();
            }  
        }
    }

    private Data crearData(String diaComplet, String horaCompleta){
            String[] aux = diaComplet.split("/");
            String[] aux2 = horaCompleta.split(":");
            return new Data(aux[2], aux[1], aux[0], aux2[0], aux2[1], aux2[2]);
    }
    
    public boolean isActiva() {
        String dataS;
        Consola.escriu("Introdueix la data 'actual': ");
        dataS = Consola.llegeixString();
        Data dataActual = new Data(dataS);
        return dataActual.compara(dataInicial) > 0 && dataActual.compara(dataFinal) < 0;
    }

    
    public boolean comprovarDates(){
        return dataInicial.compara(dataFinal) == -1;
}
    public void setEstatMoto(String estat){
        motoReserva.setEstat(estat);
    }
    
    public void afegirMotoLocalFinal(){
        try{
            localFinal.afegirMoto(motoReserva);
        }catch (LlistaPlenaException ex){
            Consola.escriu(ex.getMessage());
        }
        
    }
}
