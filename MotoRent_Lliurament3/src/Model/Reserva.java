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
    public Reserva(String id,float preu,boolean penalitzacioTemps,boolean penalitzacioMoto,float penalitzacio, String dataInicialS, String dataFinalS, Local localInicial, Local localFinal, Client clientReserva, int numMotoReserva){
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

    /**
     * UC 3_9 Metode que estableix el preu de una reserva basant-se en les dates, sense tenir en compte 
     * penalitzacions. Tambe es te en compte si el client es vip.
     */
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
    /**
     * UC 5_1 Metode que calcula el preu final de la reserva, tenint en compte totes les possibles penalitzacions.
     * Tambe canvia el estat de la moto.
     */
    public void cobrarReserva() {
        String horaActualS;
        
        Consola.escriu("Introdueix la data 'actual' (hh/dd/mm/aaaa):"); 
        horaActualS = Consola.llegeixString();
        Data dataEntrega = new Data(horaActualS); 
        
        //Data dataEntrega = new Data(); //es pot seleccionar aquesta opcio si es vol fer automatic.
        
        float diferencia;
        clientReserva.setEstat("NO RESERVA");
        
        diferencia = dataEntrega.calcularDiferencia(dataFinal);
        
        if(diferencia > 0.0f){
            penalitzacio = diferencia*2;
            penalitzacioTemps = true;
            preu += penalitzacio;   
        }
        
        gestionarAveria();
    }
    
    /**
     * UC 5_1_2 Metode que gestiona possibles averies de la moto quan el client la retorna de la reserva.
     * En cas de tenir averies, es gestiona la averia, i si fa falta, es desactiva el client.
     */
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
    
    
    /**
     * UC 4_1 Metode que comprova si una reserva es activa segons la hora passada per parametre.
     * La hora passada per parametre sera o la hora actual o una introduida per un usuari.
     * @param dataActualS Data que es suposa la actual.
     * @return Retorna si aquesta data es troba dins del periode de la reserva.
     */
    public boolean isActiva(String dataActualS) {
        Data dataActual = new Data(dataActualS);
        return dataActual.compara(dataInicial) > 0 && dataActual.compara(dataFinal) < 0;
    }

    /**
     * Metode que s'utilitza en ferReserva per comprovar si les dates son correctes. 
     * @return True si les dates son correctes.
     */
    public boolean comprovarDates(){
        return dataInicial.compara(dataFinal) == -1;
    }
    
    /**
     * Metode que posa el estat de la moto desde reserva. 
     * @param estat estat de la moto de la reserva que volem posar.
     */
    public void setEstatMoto(String estat){
        motoReserva.setEstat(estat);
    }
    
    /**
     *Metode que posa la moto de la reserva al seu local final.
     */
    public void afegirMotoLocalFinal(){
        localFinal.afegirMoto(motoReserva);
    }
}
