package Model;

import Excepcions.LlistaPlenaException;
import Vista.Consola;

public class Reserva {
    private final String id;
    private float preu;
    private boolean penalitzacioTemps;
    private boolean penalitzacioMoto;
    private float penalitzacio;
    private final Data dataInicial;
    private final Data dataFinal;
    private final Local localInicial;
    private final Local localFinal;
    private final Client clientReserva;
    private final Moto motoReserva;
    
    public Reserva(String id,float preu,boolean penalitzacioTemps,boolean penalitzacioMoto,float penalitzacio, String dataInicial, String horaInicial, String dataFinal, String horaFinal, Local localInicial, Local localFinal, Client clientReserva, String idMoto){
        this.id = id;
        this.preu = preu;
        this.penalitzacioTemps = penalitzacioTemps;
        this.penalitzacioMoto = penalitzacioMoto;
        this.penalitzacio = penalitzacio;
        this.dataInicial = Data.crearData(dataInicial, horaInicial);
        this.dataFinal = Data.crearData(dataFinal, horaFinal);
        this.localInicial = localInicial;
        this.localFinal = localFinal;
        this.clientReserva = clientReserva;
        if (localFinal.getMoto(idMoto) != null){
            this.motoReserva = localFinal.getMoto(idMoto);
        }else{
            this.motoReserva = localInicial.getMoto(idMoto);
        }
           
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
        String diaActual, horaActual;
        Consola.escriu("Introdueix la data 'actual' (dd/mm/aaaa):"); 
        diaActual = Consola.llegeixString();
        Consola.escriu("Introdueix la hora 'actual' (hh:mm:ss): ");
        horaActual = Consola.llegeixString();
        Data dataEntrega = Data.crearData(diaActual, horaActual);
        
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
                motoReserva.setEstat("Avariada");
                
                Consola.escriu("Introdueix el preu de la reparacio: ");
                reparacio = Float.parseFloat(Consola.llegeixString()); //Si no funciona partir en dos declaraciones
                
                preu += reparacio;
                penalitzacioMoto = true;
                
                clientReserva.afegirFalta();
            }  
        }
    }
    
    
    /**
     * UC 4_1 Metode que comprova si una reserva es activa segons la hora passada per parametre.
     * La hora passada per parametre sera o la hora actual o una introduida per un usuari.
     * @param diaActual Data que es suposa la actual.
     * @return Retorna si aquesta data es troba dins del periode de la reserva.
     */
    public boolean isActiva(String diaActual, String horaActual) {
        Data dataActual = Data.crearData(diaActual, horaActual);
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
        this.motoReserva.setEstat(estat);
    }
    
    /**
     *Metode que posa la moto de la reserva al seu local final.
     */
    public void afegirMotoLocalFinal(){
        try{
            localFinal.afegirMoto(motoReserva);
        }catch (LlistaPlenaException ex){
            Consola.escriu(ex.getMessage());
        }
        
    }
}
