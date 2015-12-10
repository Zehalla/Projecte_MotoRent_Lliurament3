/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import Excepcions.LlistaBuidaException;
import Model.Estats.Estats;
import Vista.Consola;
import java.util.ArrayList;

/**
 *
 * @author atorraag7.alumnes
 */
public class Client extends Usuari{
    private String idClient;
    private boolean vip;
    private final float descompteVip = 0.10f;
    private int faltes;
    private Direccio direccio;
    private Data dataRegistre = new Data();
    private ArrayList<Reserva> listReserva;
    private EstatClient estatClient;

    
    public Client(){
        
    }
    
    public Client(String idClient, String nom, String cognom1, String cognom2, String DNI, String userName, String password, boolean vip, int faltes, Direccio direccio, Data dataRegistre, ArrayList<Reserva> listReserva, EstatClient estatClient){
        this.idClient = idClient;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.DNI = DNI;
        this.userName = userName;
        this.password = password;
        this.vip = vip;
        this.faltes = faltes;
        this.direccio = direccio;
        this.dataRegistre = dataRegistre;
        this.listReserva = new ArrayList<>();
        this.estatClient = estatClient;
    }


   
    @Override
    public String getTipus() {
        return "Client";
    }
    
    public void setEstat(String tipus){
        if (tipus.equals("reserva")){
            this.estatClient = new EstatClientAmbReserva();
        }else if (tipus.equals("no reserva")){
            this.estatClient = new EstatClientSenseReserva();
        }else{
            this.estatClient = new EstatClientDesactivat();
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
        this.dataRegistre.dateToData(Consola.llegeixDataSistema());
        
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

    public void afegirReserva(Reserva reserva){
        listReserva.add(reserva);
    }    
    public boolean checkEstatDisponible() {
        return true;
    }
    
    public String mostrarReservesClient() throws LlistaBuidaException{
        int i;
        String str = "";
        if (listReserva.size() != 0){
            for (i = 0; i < listReserva.size(); i++){
                str += listReserva.get(i).toString();
            }
        }else{
            throw new LlistaBuidaException();
        }
        return str;
    }
    
    public String getIdClient(){
        return idClient;
    }
    
    @Override
    public String toString(){
        String str;
        str = "\nClient ID: " + idClient + "\n";
	str += "-----------------\n";
	str += "Nom: " + nom + "\n";
        str += "Cognom: "+cognom1+ "\n";
        str += "Segon Cognom: "+cognom2+"\n";
	str += "Usuari: " + userName + "\n";
	str += "Dni: " + DNI + "\n";
	str += direccio.toString() + "\n";
	str += "Password: " + password + "\n";
	str += "Es VIP: " + vip + "\n";
	//str += "Renovació automàtica: " + renovacio + "\n";
	str += "Nombre de faltes: " + faltes + "\n";
        str += "Estat: "+estatClient.getTipus() +"\n";
        return str;
    }
}
