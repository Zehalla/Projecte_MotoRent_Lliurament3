/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Excepcions.LlistaBuidaException;
import Vista.Consola;
import Model.*;
import Model.Estats.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
    
    public void guardarMoto(String idMoto, String matricula, String model, String color, EstatMoto estatMoto){
        llistaLocal.get(llistaLocal.size()-1).afegirMoto(new Moto(idMoto, matricula, model, color, estatMoto));
    }
    
    public void guardarReserva(String id, String idClient, String idMoto, String cost, String falta, String local_inici, String local_fi, Data dataInicial, Data dataFinal){
        llistaReserves.add(new Reserva(id, Integer.parseInt(cost), false, false, Integer.parseInt(falta), dataInicial, dataFinal, local_inici, local_fi, idClient, idMoto));
    }
    
    public void guardarClient(String id, String nom, String cognom1, String cognom2, String DNI, String userName, String password, boolean vip, int faltes, Direccio direccio, Data dataRegistre, ArrayList<Reserva> listReserva, EstatClient estatClient){
        llistaUsuaris.add(new Client(id, nom, cognom1, cognom2, DNI, userName, password, vip, faltes, direccio, dataRegistre, listReserva, estatClient));
    }
    
    public void guardarGerent(String nom, String cognom1, String cognom2, String userName, String password,  String idEmpresa){
        llistaUsuaris.add(new Gerent(nom, cognom1, cognom2, userName, password, idEmpresa));
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
    
    public void mostrarTotesLesMotos(){
        int i, j;
        for (i = 0; i < llistaLocal.size(); i++){
            //Consola.escriu(llistaLocal.get(i).toString());
            for (j = 0; j < llistaLocal.get(i).getNMotos(); j++){
                Consola.escriu(llistaLocal.get(i).getMoto(j).toString());
            }
        }
    }
    
    public void generarInformeMensual(String mes){
        int i;
        for (i = 0; i < llistaUsuaris.size(); i++){
            if (llistaUsuaris.get(i).getTipus().equals("Client")){
                Client clientActual = (Client) llistaUsuaris.get(i);
                Consola.escriu("\nClient:\n");
                if (!clientActual.generarInformeClient(mes).equals("")){
                    Consola.escriu(clientActual.generarInformeClient(mes));
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
   
   public void ferReserva(){
        int num = 0;
        boolean correcte  = false;
        int idLocalInici = 0;
        int idLocalFinal = 0;
        int capacitat;
        String opcio;
        String dataIniciS = null, horaIniciS = null;
        String dataFinalS = null, horaFinalS = null;
        Local localInici;
        Local localFinal;
        Moto moto = null;
        Date dataInicial = null;
        Date dataFinal = null;
        Reserva r;
        ArrayList<Local> auxiliar = new ArrayList<>();
        
        if (comprovacionsInicials()){
            
                //------------------LOCAL DE INICI------------------
            auxiliar = crearLlistaAuxiliarLocalsInicials();
            
            imprimirLlista(auxiliar);
            
            while (!correcte){
                Consola.escriu("\nSelecciona el local de sortida: ");
                idLocalInici = Consola.llegeixInt();
                if (idLocalInici > 0 && idLocalInici <= auxiliar.size()) {
                    correcte = true;
                } else {
                    Consola.escriu("Escriu el valor un altre cop.\n");
                }
            }
            localInici = auxiliar.get(idLocalInici - 1);
            //------------------MOTO------------------
            Consola.escriu("\n\nLLISTAT DE MOTOS DISPONIBLES:\n\n");
            Consola.escriu(localInici.mostrarMotosDisponibles());
            correcte = false;
            while (!correcte) {
                Consola.escriu("\nSelecciona una moto (id): ");
                moto = buscarMoto(Consola.llegeixString());
                if (moto != null) {
                    correcte = true;
                } else {
                    Consola.escriu("Escriu el valor un altre cop.\n");
                }
            }
            //------------------LOCAL DE DESTI------------------
            auxiliar = crearLlistaAuxiliarLocalsFinals();
            
            imprimirLlista(auxiliar);
            
            correcte = false;
            while (!correcte) {
                Consola.escriu("\nSelecciona el local de desti: ");
                idLocalFinal = Consola.llegeixInt();
                if (idLocalFinal > 0 && idLocalFinal <= auxiliar.size()) {
                    correcte = true;
                } else {
                    Consola.escriu("Escriu el valor un altre cop.");
                }
            }
            localFinal = auxiliar.get(idLocalFinal - 1);
            //------------------DATES------------------
            correcte = false;
            while (!correcte) {
                Consola.escriu("Selecciona la data de sortida (dd/mm/aaaa): ");
                dataIniciS = Consola.llegeixString();
                Consola.escriu("Selecciona la hora de sortida (hh:mm:ss): ");
                horaIniciS = Consola.llegeixString();

                Consola.escriu("Selecciona la data de desti (dd/mm/aaaa): ");
                dataFinalS = Consola.llegeixString();
                Consola.escriu("Selecciona la hora d'arrivada (hh:mm:ss): ");
                horaFinalS = Consola.llegeixString();
                try {
                    dataInicial = crearData(dataIniciS, horaIniciS).dataToDate();
                    dataFinal = crearData(dataFinalS, horaFinalS).dataToDate();
                } catch (ParseException ex) {
                    Consola.escriu("El format de les dades no és el correcte.");
                }

                if (dataInicial.after(dataFinal)) {
                    Consola.escriu("Les dades introduides no son correctes: la data final es inferior o igual a la data inicial.\n");
                } else {
                    correcte = true;
                }
            }

            Data dInicial = crearData(dataIniciS, horaIniciS);
            Data dFinal = crearData(dataFinalS, horaFinalS);

            //------------------CONFIRMACIO------------------
            correcte = false;
            while (!correcte) {
                Consola.escriu("Vols confirmar la reserva amb aquestes dates?(Y/N) ");
                opcio = Consola.llegeixString();
                if (opcio.equals("N")) {
                    return;
                } else if (opcio.equals("Y")) {
                    correcte = true;
                } else {
                    Consola.escriu("Escriu la resposta un altre cop.");
                }
            }

            //------------------CREACIO DE RESERVA------------------
            moto.setEstat("Reservada");
            localInici.eliminarMoto(moto);
            localInici.afegirMoto(moto);
            Random rand = new Random();
            num = rand.nextInt(10000);
            Client clientReserva = (Client) usuariLogat;
            r = new Reserva(Integer.toString(num), 0, false, false, 0, dInicial, dFinal, localInici.getIdLocal(), localFinal.getIdLocal(), clientReserva.getIdClient(), moto.getIdMoto());

            llistaReserves.add(r);
            clientReserva.afegirReserva(r);

            Consola.escriu("Reserva creada. El codi de la reserva es: " + num+"\n");
        }else{
            return;
        }
    }
   
    private boolean comprovacionsInicials(){
       if(!usuariLogat.getTipus().equals("Client")){
            Consola.escriu("Error. El usuari no es un client. No es pot completar la reserva.");
            return false;//Tornem al menu principal.
        }
        Client clientLogat = (Client) usuariLogat;
        if(clientLogat.getEstat().equals("Amb Reserva")){
            Consola.escriu("Error. No es pot completar la reserva perque ja tens una en curs.");
            return false;
        }  
        return true;
    }
    
    private ArrayList crearLlistaAuxiliarLocalsInicials(){
        Iterator itr = llistaLocal.iterator();
        ArrayList <Local> auxiliar = new ArrayList<>();
            while (itr.hasNext()) {
                Local l = (Local) itr.next();
                if (l.getNMotosDisp() > 0) {
                    auxiliar.add(l);
                }
            }
        return auxiliar;
    }
    
    private ArrayList crearLlistaAuxiliarLocalsFinals(){
        Iterator itr2 = llistaLocal.iterator();
        ArrayList<Local> auxiliar = new ArrayList <>();
        while (itr2.hasNext()) {
            Local l = (Local) itr2.next();
            if (l.getNMotos() < l.getCapacitat()) {
                auxiliar.add(l);
                }
            }
        return auxiliar;
    }
    
    private void imprimirLlista(ArrayList<Local> llista){
        int k;
        Consola.escriu("\nLlistat de Locals Disponibles: \n");
        for (k = 0; k < llista.size(); k++) {
            Consola.escriu("\nLocal n" + (k + 1) + ": ");
            Consola.escriu(llista.get(k).mostrarDadesLocal() + "\n");
        }
    }
    
    private Data crearData(String diaComplet, String horaCompleta){
            String[] aux = diaComplet.split("/");
            String[] aux2 = horaCompleta.split(":");
            return new Data(aux[2], aux[1], aux[0], aux2[0], aux2[1], aux2[2]);
    }
    
}
