/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Excepcions.LlistaBuidaException;
import Vista.Consola;
import Model.*;
import Model.Estats.Estats;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author atorraag7.alumnes dkjfla
 */
public class MotoRent {
    private final ArrayList<Local> llistaLocal;
    private final ArrayList<Usuari> llistaUsuaris;
    private final ArrayList<Reserva> llistaReserves;
    private Usuari usuariLogat;

    /**
     * Constructor de MotoRent que inicialitza les dues llistes.
     */
    public MotoRent() {
        this.llistaLocal = new ArrayList<>();
        this.llistaUsuaris = new ArrayList<>();
        this.llistaReserves = new ArrayList<>();
    }
    /*----------------------------------------------------------------
    ---------------METODES QUE GUARDEN DADES--------------------------
    -----------------------------------------------------------------*/
    public void guardarLocal(String idLocal, int capacitat, Direccio direccioLocal, String idGestor){
        llistaLocal.add(new Local(idLocal, capacitat, direccioLocal, new ArrayList<Moto>(), null, idGestor));
    }
    
    public void guardarMoto(String idMoto, String matricula, String model, String color, Reserva reservaMoto, EstatMoto estatMoto){
        llistaLocal.get(llistaLocal.size()-1).afegirMoto(new Moto(idMoto, matricula, model, color, null, estatMoto));
    }
    
    public void guardarReserva(String id, String idClient, String idMoto, String cost, String falta, String local_inici, String local_fi, Data dataInicial, Data dataFinal){
        llistaReserves.add(new Reserva(id, Integer.parseInt(cost), Integer.parseInt(falta), local_inici, local_fi, idClient, idMoto, dataInicial, dataFinal));
    }
    
    public void guardarClient(String id, String nom, String cognom1, String cognom2, String DNI, String userName, String password, boolean vip, int faltes, Direccio direccio, Data dataRegistre, ArrayList<Reserva> listReserva, EstatClient estatClient){
        llistaUsuaris.add(new Client(id, nom, cognom1, cognom2, DNI, userName, password, vip, faltes, direccio, dataRegistre, listReserva, estatClient));
    }
    
    public void guardarGerent(String nom, String cognom1, String cognom2, String userName, String password, Local localAGestionar, String idEmpresa){
        llistaUsuaris.add(new Gerent(nom, cognom1, cognom2, userName, password, localAGestionar, idEmpresa));
    }
    
    public void guardarAdministrador(String nom, String cognom1, String cognom2, String userName, String password, String idEmpresa){
        llistaUsuaris.add(new Administrador(nom, cognom1, cognom2, userName, password, idEmpresa));
    }
    
    public void comprovarReservesClients(){
        int i;
        String idActual; 
        Client clientActual;
        Reserva reservaActual;
        for (i=0;i<llistaReserves.size();i++){
            reservaActual = llistaReserves.get(i);
            idActual = reservaActual.getClientReserva();
            clientActual = buscarClient(idActual);
            if (clientActual != null){
                clientActual.afegirReserva(reservaActual);
                clientActual.setEstat("reserva");
            }
        }
    }
    
    public void comprovarEstatsMotos(){
        int i;
        String idMotoActual;
        Moto motoActual;
        for (i = 0; i < llistaReserves.size(); i++){
            idMotoActual = llistaReserves.get(i).getMotoReserva();
            motoActual = buscarMoto(idMotoActual);
            motoActual.setEstat("reservada");
        }
    }
    
    
    private Moto buscarMoto(String idMoto){
        int i, j;
        for (i = 0; i < llistaLocal.size(); i++){
            for (j = 0; j < llistaLocal.get(i).getNumeroMotosActual(); j++){
                if (llistaLocal.get(i).getMoto(j).getIdMoto().equals(idMoto)){
                    return llistaLocal.get(i).getMoto(j);
                }
            }
        }
        return null;
    }
    
    private Client buscarClient(String idClient){
        int i;
        Client clientActual;
        for (i = 0; i<llistaUsuaris.size();i++){
            if (llistaUsuaris.get(i).getTipus().equals("Client")){
                clientActual = (Client) llistaUsuaris.get(i);
                if (clientActual.getIdClient().equals(idClient)){
                    return clientActual;
                }
            }
        }
        return null;
    }
    
    public void mostrarDades(){
        int i = 0;
        Consola.escriu("\n\nUSUARIS\n\n");
        for (i=0;i<llistaUsuaris.size();i++){
            Consola.escriu(llistaUsuaris.get(i).toString());
        }
        i=0;
        Consola.escriu("\n\nLOCALS\n\n");
        for (i=0;i<llistaLocal.size();i++){
            Consola.escriu(llistaLocal.get(i).toString());
            Consola.escriu("\n\nMOTOS DEL LOCAL\n\n");
            Consola.escriu(llistaLocal.get(i).mostrarMotos());
        }
        Consola.escriu("\n\nRESERVES\n\n");
        for (i=0;i<llistaReserves.size();i++){
            Consola.escriu(llistaReserves.get(i).toString());
        }
        Consola.escriu("\n\nRESERVES AMB CLIENTS\n\n");
        Client clientActual;
        for (i = 0; i<llistaUsuaris.size();i++){
            if (llistaUsuaris.get(i).getTipus().equals("Client")){
                clientActual = (Client) llistaUsuaris.get(i);
                Consola.escriu("\n"+clientActual.toString()+"\n");
                try{
                    Consola.escriu(clientActual.mostrarReservesClient());
                }catch (LlistaBuidaException ex){
                    Consola.escriu("\n"+ex.getMessage()+"\n");
                }
            }
        }
    }
    /*----------------------------------------------------------------
    ------------------------------------------------------------------
    -----------------------------------------------------------------*/
    
    
    /**
     * Mètode que permet a un Usuari logar-se.
     * @return tipus: Retorna el tipus d'Usuari que s'ha logat.
     */
    public String logIn(){
        String myUserName, myPass, tipus;
        Usuari ui;
        boolean trobat = false;
        int n = llistaUsuaris.size();
        int i = 0;
        
        tipus = "NoTrobat";
        Consola.escriu("Introdueixi el seu nom d'usuari: ");
        myUserName = Consola.llegeixString();
        Consola.escriu("Introdueixi la seva contrasenya: ");
        myPass = Consola.llegeixString();
        
        while(!trobat && i<n){
            ui = llistaUsuaris.get(i);    
            if(ui.checkUserName(myUserName) && ui.checkPassword(myPass)){
                trobat = true;
                this.usuariLogat = ui;
            }
            i += 1;
        }
        if (trobat){
            tipus = usuariLogat.getTipus();
            return tipus;
        }else{
            Consola.escriu("El nom d'usuari o la contrasenya son incorrectes\n");
            return tipus;
        }
    }
    
    /**
     * Métode que permet registrar un Client
     * @return tornarErrere: en cas de que l'usuari canvii d'idea i no es registri
     */
    public boolean registrarUsuari(){
       String newUserName = null;
       Client newClient;
       boolean error = false;
       boolean tornarErrere = false;
            
       while (!error){
           Consola.escriu("Introdueixi el nom d'usuari: ");
           newUserName = Consola.llegeixString();
           error = !checkUserName(newUserName);
           if (!error){
               Consola.escriu("Aquest usuari no es troba disponible. Escolliu-ne un altre.\n");
               tornarErrere = !Consola.reintroduirDades();
               if (tornarErrere){
                   return tornarErrere;
               }
           }
       }
       newClient = new Client();
       newClient.introduirDades(newUserName);
       llistaUsuaris.add(newClient);
       this.usuariLogat = newClient;
       return tornarErrere;
   }
    
   /**
    * Métode que comprova si el newUserName ja esta en ús.
    * @param newUserName
    * @return trobat
    */ 
   private boolean checkUserName(String newUserName) {
        boolean trobat = false;
        int n = llistaUsuaris.size();
        int i = 0;
        
        while (!trobat && i<n){
            trobat = llistaUsuaris.get(i).checkUserName(newUserName);
            i += 1;
        }
        return trobat;
    }
   
   private void ferReserva(){
       int num = 0;
       int i = 0;
       boolean correcte  = false;
       int idLocalInici = 0;
       int idLocalFinal = 0;
       int idMoto = 0;
       int capacitat;
       int comp = 0;
       String opcio;
       String dataIniciS;
       String dataFinalS;
       
       Local localInici = new Local();
       Local localFinal = new Local();
       Moto moto = new Moto();
       Data dataInicial = null;
       Data dataFinal = null;
       Reserva r;

        if(usuariLogat.getTipus() != "Client"){
            Consola.escriu("Error. El usuari no es un client. No es pot completar la reserva.");
            return;//Tornem al menu principal.
        }
        Client clientLogat = (Client) usuariLogat;
        if(!clientLogat.checkEstatDisponible()){    //TODO arreglar aixo
            Consola.escriu("Error. No es pot completar la reserva perque ja tens una en curs.");
            return;
        }           
        //------------------LOCAL DE INICI------------------
        Consola.escriu("Selecciona el local de sortida: ");
        Iterator itr = llistaLocal.iterator();
        while(itr.hasNext()){
            Local l = (Local) itr.next();
            num = l.getNMotosDisp();
            if(num > 0){
                i++;
                Consola.escriu("Local n"+i+": ");
                l.mostrarDadesLocal();
            }
        }
        while(!correcte){//Nomes es sortira del bucle quan s'escriu un numero valid.
            //i ara conté el numero de locals que son disponibles
            idLocalInici = Consola.llegeixInt();
            if(idLocalInici>0 && idLocalInici<i){
                correcte = true;
            }else{
                Consola.escriu("Escriu el valor un altre cop.");
            }
        }
        localInici = getLocalInicial(idLocalInici-1);
        //------------------MOTO------------------
        
        Consola.escriu("Selecciona una moto:");
        localInici.mostrarMotos();
        
        num = localInici.getNMotosDisp();
        correcte = false;
        while(!correcte){//Nomes es sortira del bucle quan s'escriu un numero valid.
            idMoto = Consola.llegeixInt();
            if(idMoto>0 && idMoto<num){
                correcte = true;
            }else{
                Consola.escriu("Escriu el valor un altre cop.");
            }
        }
        moto = localInici.getMoto(idMoto-1);
        //------------------LOCAL DE DESTI------------------
        Consola.escriu("Selecciona el local de desti: ");
        i = 0;
        Iterator itr2 = llistaLocal.iterator();
        while(itr2.hasNext()){
            Local l = (Local) itr2.next();
            num = l.getNMotos();  //No getNMotosDisp
            capacitat = l.getCapacitat();
            if(num < capacitat){
                i++;
                Consola.escriu("Local n"+i+": ");
                l.mostrarDadesLocal();
            }
        }
        correcte = false;
        while(!correcte){//Nomes es sortira del bucle quan s'escriu un numero valid.
            idLocalFinal = Consola.llegeixInt();
            if(idLocalFinal>0 && idLocalFinal<i){
                correcte = true;
            }else{
                Consola.escriu("Escriu el valor un altre cop.");
            }
        }
        localFinal = getLocalFinal(idLocalFinal-1);
        //------------------DATES------------------
        
        while(comp<=0){
            Consola.escriu("Selecciona la data de sortida (hh-dd-mm-aaaa): ");
            dataIniciS = Consola.llegeixString();
            Consola.escriu("Selecciona la data de desti (hh-dd-mm-aaaa): ");
            dataFinalS = Consola.llegeixString();
            
            dataInicial = new Data(dataIniciS);
            dataFinal = new Data(dataFinalS);
            
            
            comp = dataInicial.compara(dataFinal);
            
            if(comp<=0){
                Consola.escriu("Les dades introduides no son correctes: la data final es inferior o igual a la data inicial.");
            }
        }   
        
        //------------------CONFIRMACIO------------------
        correcte = false;
        while(!correcte){
            Consola.escriu("Vols confirmar la reserva amb aquestes dates? (Y/N)");
            opcio = Consola.llegeixString();
            if(opcio == "N"){
                return;
            }else if(opcio == "Y"){
                correcte = true;
            }else{
                Consola.escriu("Escriu la resposta un altre cop.");
            }
        }
        
        //------------------CREACIO DE RESERVA------------------
        
        moto.setTipus("Reservada");
        localInici.eliminarMoto(idMoto);
        localInici.afegirMoto(moto);
        Random rand = null;  
        num = rand.nextInt(10000);
        
        Client clientReserva = (Client) usuariLogat;
        r = new Reserva(Integer.toString(num), dataInicial, dataFinal, localInici, localFinal, clientReserva, moto);
        //localInici.afegirReserva(r);
        //localFinal.afegirReserva(r);
        llistaReserves.add(r);
        clientReserva.afegirReserva(r);
        
        Consola.escriu("Reserva creada. El codi de la reserva es: " + num);
    }
           //El metode pot ser que retorni null si el local buscat no esta.
           //estara ja que comprovem que el numero introduit sigui correcte
    private Local getLocalInicial(int index){ 
        int i = 0;
        boolean trobat = false;
        int num;
        Local localInicial= null;  
        Iterator itr = llistaLocal.iterator();
        while(itr.hasNext() && !trobat){
            Local l = (Local) itr.next();
            num = l.getNMotosDisp();
            if(num>0){
                i++;
            }
            if(i == index){
                trobat = true;
                localInicial = l;
            }
        }
        return localInicial;
    }
    
    //El metode pot ser que retorni null si el local buscat no esta.
    //estara ja que comprovem que el numero introduit sigui correcte
    private Local getLocalFinal(int index){
        int i = 0;
        boolean trobat = false;
        int num;
        int capacitat;
        
        Local localFinal= null;  
        Iterator itr = llistaLocal.iterator();
        while(itr.hasNext() && !trobat){
            Local l = (Local) itr.next();
            
            num = l.getNMotos();
            capacitat = l.getCapacitat();
            if(num<capacitat){
                i++;
            }
            if(i == index){
                trobat = true;
                localFinal = l;
            }
        }
        return localFinal;
    }
    
}
