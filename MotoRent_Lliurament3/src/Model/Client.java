/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import Excepcions.LlistaBuidaException;
import Model.Estats.*;
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
    private ArrayList<Reserva> listReserva = new ArrayList<>();
    private EstatClient estatClient;

    
    public Client(){
        
    }
    
    public Client(String idClient, String nom, String cognom1, String cognom2, String DNI, String userName, String password, boolean vip, int faltes, Direccio direccio, Data dataRegistre, EstatClient estatClient){
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
        this.estatClient = estatClient;
    }
   
    public String generarInformeClient(String mes){
        int i, numReserves = 0;
        String str = this.toString();
        float cost = 0;
        for (i = 0; i < listReserva.size(); i++){
            if (listReserva.get(i).getMesReserva().equals(mes)){
                numReserves++;
                str += listReserva.get(i).toString();
                //str += "Local Inicial de reserva:\n";
                //str += listReserva.get(i).getLocalInicial();
                // Falta imprimir per pantalla el local inicial, el final i si la moto està en bones o males condicions.
                cost += listReserva.get(i).getPreu();
            }
        }
        str += "\nEl número de reserves és de "+numReserves+"\n";
        str += "El total a facturar és de "+cost+ "€.\n";
        return str;
    }
    
    
    public String getId(){
        return idClient;
    }
    
    public void afegirFalta(){
        this.faltes += 1;
        if(faltes <= 3){
            this.setEstat("Desactivat");
            Consola.escriu("El client s'ha desactivat perque te 3 faltes o mes.");
        }


                    
    }
    
    @Override
    public String getTipus() {
        return "Client";
    }
    
    public String getEstat(){
        if(this.estatClient instanceof EstatClientAmbReserva){
            return "Amb Reserva";
        }else if(this.estatClient instanceof EstatClientDesactivat){
            return "Desactivat";
        }else{
            return "Sense Reserva";   
        }
    }
    
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
    
    public void obtenirDataInicialReserva(int mes){
        int mesReservaActual, i, numReserves = 0;
        float total = 0;
        for (i = 0; i < listReserva.size(); i++){
            mesReservaActual = Integer.parseInt(listReserva.get(i).getMesReserva());
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

    public void afegirReserva(Reserva reserva){
        listReserva.add(reserva);
        this.estatClient = Estats.getEstatClientAmbReserva();
    }    
    public boolean checkEstatDisponible() {
        return true;
    }
    
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
    
    public String getIdClient(){
        return idClient;
    }
    
    public int getFaltes(){
        return faltes;
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
        str += "Estat: "+ getEstat() +"\n";
        return str;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public Direccio getDireccio() {
        return direccio;
    }

    public void setDireccio(Direccio direccio) {
        this.direccio = direccio;
    }

    public Data getDataRegistre() {
        return dataRegistre;
    }

    public void setDataRegistre(Data dataRegistre) {
        this.dataRegistre = dataRegistre;
    }

    public ArrayList<Reserva> getListReserva() {
        return listReserva;
    }

    public void setListReserva(ArrayList<Reserva> listReserva) {
        this.listReserva = listReserva;
    }

    public EstatClient getEstatClient() {
        return estatClient;
    }

    public void setEstatClient(EstatClient estatClient) {
        this.estatClient = estatClient;
    }
    
    
    
    
}
