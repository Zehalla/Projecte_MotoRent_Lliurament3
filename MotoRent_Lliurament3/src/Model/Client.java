package Model;


import Excepcions.LlistaBuidaException;
import Model.Estats.*;
import Vista.Consola;
import java.util.ArrayList;

public class Client extends Usuari{
    private boolean vip;
    private final float descompteVip = 0.10f;
    private int faltes;
    private Direccio direccio;
    private Data dataRegistre;
    private ArrayList<Reserva> listReserva = new ArrayList<>();
    private EstatClient estatClient;

    /**
     * Constructor buit usat a l'hora de registrar un nou client
     * @param id
     */
    public Client(String id){
        this.id = id;
    }
    /**
     * Constructor d'un client a partir de les dades donades en el fitxer xml
     * @param idClient
     * @param nom
     * @param cognom1
     * @param cognom2
     * @param DNI
     * @param userName
     * @param password
     * @param vip
     * @param faltes
     * @param direccio 
     */
    public Client(String idClient, String nom, String cognom1, String cognom2, String DNI, String userName, String password, boolean vip, int faltes, String[] direccio){
        this.id = idClient;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.DNI = DNI;
        this.userName = userName;
        this.password = password;
        this.vip = vip;
        this.faltes = faltes;
        this.direccio = crearDireccio(direccio);
        this.dataRegistre = new Data();
        this.estatClient = Estats.getEstatClientSenseReserva();
    }
    
    /**
     * Crea una direccio a partir d'una adreca donada
     * @param adreca
     * @return 
     */
    private Direccio crearDireccio(String[] adreca){
            return new Direccio(adreca[0], adreca[1], adreca[2], adreca[3]);
    }
    
    @Override
    /**
     * Retorna la id del client
     */
    public String getId(){
        return id;
    }
    
    @Override
    /**
     * Retorna un string indicant que es un Client
     */
    public String getTipus() {
        return "Client";
    }
    /**
     * Indica si el client es vip o no
     * @return 
     */
    public boolean getVip(){
        return this.vip;
    }
    /**
     * Mètode que afegeix una falta al client
     */
    public void afegirFalta(){
        this.faltes += 1;
        if(this.faltes >= 3){
            this.setEstat("Desactivat");
            Consola.escriu("El client s'ha desactivat perque te 3 faltes o mes.");
        }               
    }
    
    @Override
    /**
     * Metode que indica en quin estat es troba el client
     */
    public String getEstat(){
        if(this.estatClient instanceof EstatClientAmbReserva){
            return "Amb Reserva";
        }else if(this.estatClient instanceof EstatClientDesactivat){
            return "Desactivat";
        }else{
            return "Sense Reserva";   
        }
    }
    /**
     * Metode que canvia l'estat del client
     * @param estat 
     */
    public void setEstat(String estat){
        estat = estat.toUpperCase();
        switch (estat) {
            case "RESERVA":
                this.estatClient = Estats.getEstatClientAmbReserva();
                break;
            case "NO RESERVA":
                this.estatClient = Estats.getEstatClientSenseReserva();
                break;
            default:
                this.estatClient = Estats.getEstatClientDesactivat();
                break;
        }
    }
    
    /**
     * Metode que retorna les reserves que ha fet el client en un mes donat
     * @param mes 
     */
    public void obtenirReservesClient(int mes){
        int i, numReserves = 0, mesReservaActual;
        float total = 0;
        for (i = 0; i < listReserva.size(); i++){
            mesReservaActual = listReserva.get(i).obtenirDataIniciReserva();
            if (mes == mesReservaActual){
                numReserves++;
                listReserva.get(i).generarInformeReserva();
                total += listReserva.get(i).getPreu() + listReserva.get(i).getPenalitzacio();
        } 
        }
        if (numReserves != 0){
            Consola.escriu("\nEl numero de reserves és de: "+numReserves+"\n");
            Consola.escriu("El total a facturar és de: "+total+"\n");
        }else{
            Consola.escriu("\nAquest client no ha realitzat reserves en el mes seleccionat.\n");
        }
    }
    
    /**
     * Métode que permet introduir les dades d'un client.
     * @param userName 
     */
    public void introduirDades(String userName){
        this.userName = userName;
        this.vip = false;
        this.faltes = 0;
        this.estatClient = Estats.getEstatClientSenseReserva();
        this.dataRegistre = new Data();
        
        Consola.escriu("Introdueixi la constrasenya: ");
        this.password = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el DNI: ");
        this.DNI = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el seu nom: ");
        this.nom = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el seu primer cognom: ");
        this.cognom1 = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el seu segon cognom: ");
        this.cognom2 = Consola.llegeixString();
        
        this.direccio = new Direccio();
        this.direccio.introduirDades();
    }
    /**
     * Metode que afegeix una reserva al client
     * @param reserva 
     */
    public void afegirReserva(Reserva reserva){
        listReserva.add(reserva);
        this.estatClient = Estats.getEstatClientAmbReserva();
    }
    /**
     * Metode que mostra totes les reserves que ha fet un client
     * @return
     * @throws LlistaBuidaException 
     */
    public String mostrarReservesClient() throws LlistaBuidaException{
        int i;
        String str = "";
        if (!listReserva.isEmpty()){
            for (i = 0; i < listReserva.size(); i++){
                str += listReserva.get(i).toString();
            }
        }else{
            throw new LlistaBuidaException();
        }
        return str;
    }
    /**
     * Metode que retorna el nombre de faltes que ha comes el client
     * @return faltes
     */
    public int getFaltes(){
        return faltes;
    }
    
    @Override
    /**
     * Metode que retorna tota la informacio sobre un client en forma d'String
     */
    public String toString(){
        String str;
        str = "\nClient ID: " + this.id + "\n";
	str += "-----------------\n";
	str += "Nom: " + nom + "\n";
        str += "Cognom: "+cognom1+ "\n";
        str += "Segon Cognom: "+cognom2+"\n";
	str += "Usuari: " + userName + "\n";
	str += "Dni: " + DNI + "\n";
	str += direccio.toString() + "\n";
	str += "Password: " + password + "\n";
	str += "Es VIP: " + vip + "\n";
	str += "Nombre de faltes: " + faltes + "\n";
        str += "Estat: "+ getEstat() +"\n";
        return str;
    }
}
