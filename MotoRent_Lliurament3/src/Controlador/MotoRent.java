/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Excepcions.LlistaBuidaException;
import Excepcions.LlistaPlenaException;
import Vista.Consola;

import Model.Usuari;
import Model.Administrador;
import Model.Gerent;
import Model.Client;
import Model.Local;
import Model.Reserva;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author atorraag7.alumnes dkjfla
 */
public class MotoRent {
    private final ArrayList<Local> llistaLocals;
    private final ArrayList<Usuari> llistaUsuaris;
    private final ArrayList<Reserva> llistaReserves;
    private Usuari usuariLogat;
    private int lastIDreserva;

    /**
     * Constructor de MotoRent que inicialitza les dues llistes.
     */
    public MotoRent() {
        this.lastIDreserva = 3;
        this.llistaLocals = new ArrayList<>();
        this.llistaUsuaris = new ArrayList<>();
        this.llistaReserves = new ArrayList<>();
    }
    /*----------------------------------------------------------------
    ---------------METODES QUE GUARDEN DADES--------------------------
    -----------------------------------------------------------------*/
    public void guardarLocal(String idLocal, int capacitat, String[] direccioLocal, String idGestor){
        Gerent gestor = (Gerent) buscarUsuari(idGestor);
        llistaLocals.add(new Local(idLocal, capacitat, direccioLocal, new ArrayList<>(), gestor));
    }
    
    public void guardarMoto(String idMoto, String matricula, String model, String color, String estatMoto){
        try{
            llistaLocals.get(llistaLocals.size()-1).afegirMoto(idMoto, matricula, model, color, estatMoto);
        }catch(LlistaPlenaException ex){
            Consola.escriu(ex.getMessage());
        }
    }
    
    public void guardarReserva(String id, String idClient, String idMoto, String cost, String falta, String local_inici, String local_fi, String dataInicial, String horaInicial, String dataFinal, String horaFinal){
        Client client = (Client) buscarUsuari(idClient);
        if (Integer.parseInt(falta) == 0){ // si no hi ha faltes, afegim normalment.
            llistaReserves.add(new Reserva(id, Integer.parseInt(cost), false, false, 0, dataInicial, horaInicial, dataFinal, horaFinal, buscarLocal(local_inici), buscarLocal(local_fi), client, idMoto));
        }else{ //en cas q hi hagi faltes, afegim un cost addicional a la reserva q anirà de 0 a 20.
            Random ran = new Random(); 
            int i;
            llistaReserves.add(new Reserva(id, Integer.parseInt(cost), true, true, ran.nextInt(20), dataInicial, horaInicial, dataFinal, horaFinal, buscarLocal(local_inici), buscarLocal(local_fi), client, idMoto));
            for (i = 0; i < Integer.parseInt(falta); i++){
                client.afegirFalta();
            }
        }
    }
    
    public void guardarClient(String id, String nom, String cognom1, String cognom2, String DNI, String userName, String password, boolean vip, int faltes, String[] direccio){
        llistaUsuaris.add(new Client(id, nom, cognom1, cognom2, DNI, userName, password, vip, faltes, direccio));
    }
    
    public void guardarGerent(String nom, String cognom1, String cognom2, String userName, String password,  String idEmpresa){
        llistaUsuaris.add(new Gerent(nom, cognom1, cognom2, userName, password, idEmpresa));
    }
    
    public void guardarAdministrador(String nom, String cognom1, String cognom2, String userName, String password, String idEmpresa){
        llistaUsuaris.add(new Administrador(nom, cognom1, cognom2, userName, password, idEmpresa));
    }
    
    
    public void comprovacionsPrevies(){
        comprovarReservesClients();
        comprovarEstatsMotos();
        comprovarEstatsClients();
        comprovarLocalsAGestionar();
        mostrarDades(); //Metodes per a testeig.
        mostrarTotesLesMotos(); //Metodes per a testeig.
    }
    
    private void comprovarReservesClients(){
        int i;
        Client clientActual;
        Reserva reservaActual;
        for (i=0;i<llistaReserves.size();i++){
            reservaActual = llistaReserves.get(i);
            clientActual = reservaActual.getClientReserva();
            if (clientActual != null){
                clientActual.afegirReserva(reservaActual);
                clientActual.setEstat("reserva");
            }
        }
    }
    
    private void comprovarEstatsMotos(){
        int i;
        for (i = 0; i < llistaReserves.size(); i++){
            llistaReserves.get(i).getMotoReserva().setEstat("reservada");
        }
    }
    
    private void comprovarEstatsClients(){
        int i;
        Client clientActual;
        for (i = 0; i < llistaUsuaris.size(); i++){
            if (llistaUsuaris.get(i).getTipus().equals("Client")){
                clientActual = (Client) llistaUsuaris.get(i);
                if (clientActual.getFaltes() >= 3){
                    clientActual.setEstat("Desactivat");
                }
            }
        }
    }
    
    private void comprovarLocalsAGestionar(){
        int i;
        for (i = 0; i < llistaLocals.size(); i++){
            buscarUsuari(llistaLocals.get(i).getGestor().getId()).setLocalAGestionar(llistaLocals.get(i));
        }
    }
    
    
    private Usuari buscarUsuari(String idUsuari){
        int i;
        for (i = 0; i<llistaUsuaris.size();i++){
            if (llistaUsuaris.get(i).getId().equals(idUsuari)){
                return llistaUsuaris.get(i);
            }
        }
        return null;
    }
    
    private Local buscarLocal(String idLocal){
        int i;
        for (i = 0; i < llistaLocals.size(); i++){
            if (llistaLocals.get(i).getIdLocal().equals(idLocal)){
                return llistaLocals.get(i);
            }
        }
        return null;
    }
  
    private void mostrarDades(){
        int i;
        Consola.escriu("\n\nUSUARIS\n\n");
        for (i=0;i<llistaUsuaris.size();i++){
            Consola.escriu(llistaUsuaris.get(i).toString());
        }
        Consola.escriu("\n\nLOCALS\n\n");
        for (i=0;i<llistaLocals.size();i++){
            Consola.escriu(llistaLocals.get(i).toString());
            Consola.escriu("\n\nMOTOS DEL LOCAL\n\n");
            Consola.escriu(llistaLocals.get(i).mostrarMotos());
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
        int i;
        Consola.escriu("\nLLISTAT DE TOTES LES MOTOS:\n");
        for (i = 0; i < llistaLocals.size(); i++){
            llistaLocals.get(i).obtenirMotosLocal();
        }
    }
    
    public void generarInformeMensual(String mes){
        boolean correcte;
        int i;
        correcte = comprovarDataCorrecte(mes);
        if (correcte){
            for (i = 0; i < llistaUsuaris.size(); i++) {
                if (llistaUsuaris.get(i).getTipus().equals("Client")) {
                    Client clientActual = (Client) llistaUsuaris.get(i);
                    Consola.escriu(clientActual.toString());
                    clientActual.obtenirReservesClient(Integer.parseInt(mes));
                }
            }
        }else{
            Consola.escriu("\nHi ha hagut un error al realitzar l'informe del mes seleccionat.\n");
        }
        
    }
    
    private boolean comprovarDataCorrecte(String mes) {
        return (Integer.parseInt(mes)) <= (Integer.parseInt(Consola.llegeixDataSistema().getMes())+1); // el +1 es perque
                                                                                                    // el Date té representa els mesos de l'any de 0 a 11.
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
            if (tipus.equalsIgnoreCase("CLIENT")){
                if("Desactivat".equalsIgnoreCase(usuariLogat.getEstat())){
                    Consola.escriu("Voste es troba desactivat per haver comes 3 faltes en menys d'un any.\n");
                    tipus = "Desactivat";
                }
            }
        }else{
            Consola.escriu("El nom d'usuari o la contrasenya son incorrectes\n");
        }
        return tipus;
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
   
   /**
    * UC 3 Metode de client que guia a un usuari fins a la creacio de una reserva. Es contemplent tots els casos posibles.
    * El codi es divideix en diferents parts: comprovacions inicials , seleccio del local inicial, seleccio de la moto, seleccio del
    * local de desti, seleccio de hores, confimacio i processament de la reserva.
    */
   public void ferReserva(){
        boolean correcte  = false;
        String idLocalInici;
        String idLocalFinal;
        String idMoto = "";
        String opcio;
        String dataInicialS;
        String dataFinalS;
        Local localInici = null;
        Local localFinal = null;
        Reserva r = null;
        ArrayList<Local> auxiliar;
        Client clientReserva = (Client) usuariLogat;
        Local l = null;
        Boolean trobat = false;

        if (!comprovacionsInicials()){
            return;
        }
        //------------------LOCAL DE INICI------------------
        auxiliar = crearLlistaAuxiliarLocalsInicials();
           
        imprimirLlistaLocals(auxiliar);
            
        while (!correcte){
            Consola.escriu("\nEscriu la ID del local de sortida: ");
            idLocalInici = Consola.llegeixString();

            Iterator itr = auxiliar.iterator();
            while(itr.hasNext() && !trobat){
                l = (Local) itr.next();
                trobat = l.getIdLocal().equals(idLocalInici);
            }
            if (trobat){
                correcte = true;
                localInici = getLocal(auxiliar,idLocalInici);
            }else{
                Consola.escriu("Escriu el valor un altre cop.\n");
            }
        }
        //------------------MOTO------------------
        Consola.escriu("\n\nLLISTAT DE MOTOS DISPONIBLES:\n\n");
        Consola.escriu(localInici.mostrarMotosDisponibles());

        //int num = localInici.getNMotosDisp();
        correcte = false;
        while (!correcte) {
            Consola.escriu("\nSelecciona una moto: ");
            idMoto = Consola.llegeixString();
            if (localInici.checkID(idMoto)) {
                correcte = true;
            } else {
                Consola.escriu("Escriu el valor un altre cop.\n");
            }
        }

        //------------------LOCAL DE DESTI------------------
        auxiliar = crearLlistaAuxiliarLocalsFinals();
         
        imprimirLlistaLocals(auxiliar);
            
        correcte = false;
        trobat = false;
        while (!correcte) {
            Consola.escriu("\nSelecciona el local de desti: ");
            idLocalFinal = Consola.llegeixString();
            
            Iterator itr = auxiliar.iterator();
            while(itr.hasNext() && !trobat){
                l = (Local) itr.next();
                trobat = l.getIdLocal().equals(idLocalFinal);
            }
            if (trobat){
                localFinal = getLocal(auxiliar,idLocalFinal);
                correcte = true;
            }else{
                Consola.escriu("Escriu el valor un altre cop.\n");
            }  
        }
        //------------------DATES------------------
        correcte = false;
        trobat = false;
        while (!correcte) {
            Consola.escriu("Selecciona la data de sortida (hh/dd/mm/aaaa): ");
            dataInicialS = Consola.llegeixString();

            Consola.escriu("Selecciona la data de desti (hh/dd/mm/aaaa): ");
            dataFinalS = Consola.llegeixString();
            
            //CREACIO DE RESERVA 
            r = new Reserva("r"+Integer.toString(lastIDreserva), 0, false, false, 0, dataInicialS, dataFinalS, localInici, localFinal, clientReserva, idMoto);            

            if (r.comprovarDates()){
                correcte = true;
            }else{
                Consola.escriu("Les dades introduides no son correctes: la data final es inferior o igual a la data inicial.\n");
            }

        }
        //------------------CONFIRMACIO------------------
        r.calcularPreu();
        
        correcte = false;
        while (!correcte) {
            Consola.escriu("Vols confirmar la reserva amb aquestes dates?(Y/N): ");
            opcio = Consola.llegeixString();
            switch (opcio) {
                case "N":
                    return;
                case "Y":
                    correcte = true;
                    break;
                default:
                    Consola.escriu("Escriu la resposta un altre cop.");
                    break;
            }
        }

        //------------------CREACIO DE RESERVA------------------
        llistaReserves.add(r);
        r.setEstatMoto("Reservada");
        r.afegirMotoLocalFinal();
        lastIDreserva ++;
        clientReserva.afegirReserva(r);

        Consola.escriu("Reserva creada. El codi de la reserva es: r" +Integer.toString(lastIDreserva)+"\n");
   }
   
   /**
    * UC 3_1 Metode que fa totes les comprovacions possibles per assegurar que la reserva es correcte.
    * Contempla dos casos: que el usuari no sigui un client (no possible en teoria donat que la opcio de 
    * reservar nomes la te el usuari) i si el usuari ja te una reserva en curs.
    * @return si el usuari pot fer una reserva.
    */
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
    
    /**
     * UC 3_2 Per tal de fer mes clar el codi, creem una llista ja filtrada de manera que nomes conte locals que poden ser
     * locals inicials. Per tant, es un subconjunt de la llista total de locals. Per a saber si un local pot ser local incial
     * mirem si te com a minim una moto que es pugui llogar. Es crida al UC 3_5 getNMotosDisp
     * @return tota la llista de locals inicials
     */
    private ArrayList crearLlistaAuxiliarLocalsInicials(){
        Iterator itr = llistaLocals.iterator();
        ArrayList <Local> auxiliar = new ArrayList<>();
        
        while (itr.hasNext()) {
            Local l = (Local) itr.next();
            if (l.getNMotosDisp() > 0) {
                auxiliar.add(l);
            }
        }
        return auxiliar;
    }
    
    /**
     * UC 3_7 Per tal de fer mes clar el codi, creem una llista ja filtrada de manera que nomes conte els locals que poden ser
     * locals finals. Per tant , es un subconjunt de la llista total de locals. Per a saber si un local pot ser local final
     * mirem si te una plaça lliure com a minim. Es crida a getNMotos que retorna el numero de motos totals del local. 
     * @return tota la llista de locals finals
     */
    private ArrayList crearLlistaAuxiliarLocalsFinals(){
        Iterator itr2 = llistaLocals.iterator();
        ArrayList<Local> auxiliar = new ArrayList <>();
        while (itr2.hasNext()) {
            Local l = (Local) itr2.next();
            if (l.getNMotos() < l.getCapacitat()) {
                auxiliar.add(l);
                }
            }
        return auxiliar;
    }
    
    /**
     * UC 3_3 Metode que mostra una llista de objectes tipus local. 
     * @param llista llista que es vol mostrar.
     */
    private void imprimirLlistaLocals(ArrayList<Local> llista){
        Consola.escriu("\nLlistat de Locals Disponibles: \n");
        for (int k = 0; k < llista.size(); k++) {
            Consola.escriu(llista.get(k).mostrarDadesLocal() + "\n");
        }
    }
    
    /**
     * Metode de gerent que engloba totes les accions que es fan quan un client retorna una moto.
     * Es comprova la reserva, i si es troba, es cobra la reserva, i es cobren les possibles penalitzacions.
     */
    public void tornarMoto(){
        boolean correcte = false;
        boolean trobat = false;
        String reservaID;
        Reserva r = null;
        Boolean stop = false;
        String idClient;
        
        while(!correcte && !stop){
            Consola.escriu("Introdueix l'identificador de la reserva: ");
            reservaID = Consola.llegeixString();
            
            Consola.escriu("Introdueix l'identificador del client: ");
            idClient = Consola.llegeixString();
            
            Iterator itr = llistaReserves.iterator();
            while(itr.hasNext() && !trobat){
                
            }
            
            while(itr.hasNext() && !trobat){
                r = (Reserva) itr.next();
                    trobat = r.getId().equals(reservaID) && r.getClientReserva().getId().equals(idClient);    
                }
            if(trobat){
                r.cobrarReserva();
                correcte = true;
            }else{
                Consola.escriu("No s'ha trobat la reserva. Comprovi que la ID sigui correcte.");
                stop = !Consola.reintroduirDades();
            }
        }
    }
    
    /**
     * Metode per gestinar la importacio/exportacio de motos de un local
     */
    public void gestionarLocal() {
        String accio;
        int nombreMotos;
        accio = usuariLogat.gestionarLocal();
        switch(accio){
            case("Importar"):
                nombreMotos = usuariLogat.demanarNombreMotosAImportar();
                importarMotos(nombreMotos);
                break;
            case("Exportar"):
                nombreMotos = usuariLogat.demanarNombreMotosAExportar();
                if(nombreMotos >0){
                    exportarMotos(nombreMotos);
                }
                break;
        }
    }
    /**
     * Metode que importa el nombre de motos pasat per parametre
     * @param motosAImportar 
     */
    private void importarMotos(int motosAImportar) {
        Local localPerImportar;
        localPerImportar = getLocalAmbMesMotosDisponibles();
        if (localPerImportar != null){
            localPerImportar.mostrarInfoImportacio(motosAImportar);
            usuariLogat.importarMotos(motosAImportar,localPerImportar);
        }else{
            Consola.escriu("No hi ha locals per a importar motos.\n");
        }
        
    }
    /**
     * Metode que exporta el nombre de motos pasat per parametre
     * @param motosAExportar 
     */
    private void exportarMotos(int motosAExportar) {
        Local localPerExportar;
        localPerExportar = getLocalAmbMesCapacitatDisponible();
        if (localPerExportar != null){
            localPerExportar.mostrarInfoExportacio(motosAExportar);
            usuariLogat.exportarMotos(motosAExportar,localPerExportar);   
        }else{
            Consola.escriu("No hi ha locals per exportar.\n");
        }
    }
    /**
     * Metode que cerca el local que dispon de mes motos disponibles
     * @return localPerImportar
     */
    private Local getLocalAmbMesMotosDisponibles() {
        int nombreMotosDisponibles, comparador;
        Local localPerImportar = null;
        comparador = 0;
        for(Local loci : llistaLocals){
            nombreMotosDisponibles = loci.getNMotosDisp();
            nombreMotosDisponibles = loci.calcMotosImportables(nombreMotosDisponibles);
            if (nombreMotosDisponibles > comparador){
                comparador = nombreMotosDisponibles;
                localPerImportar = loci;
            }
        }
        return localPerImportar;
    }
    /**
     * Metode que cerca el local que disposa de mes capacitat disponible
     * @return localPerExportar
     */
    private Local getLocalAmbMesCapacitatDisponible() {
        int capacitatDisponible, comparador;
        Local localPerExportar = null;
        comparador = 0;
        for(Local loci: llistaLocals){
            capacitatDisponible = loci.getCapacitatDisponible();
            if (capacitatDisponible > comparador){
                comparador = capacitatDisponible;
                localPerExportar = loci;
            }
        }
        return localPerExportar;
    }

    /**
     * Metode de gerent que engloba totes les accions que es fan quan el client ve a recollir una moto del local.
     * Es comprova la reserva, i es treu la moto del local incial.
     */
    public void lliurarMotoAClient(){
        String idReserva;
        String idClient;
        boolean trobat = false;
        Reserva r = null;
        boolean correcte = false;
        boolean stop = false;
        String horaActual;
        
        while(!correcte && !stop){
            Consola.escriu("Introdueix la data 'actual' (hh/dd/mm/aaaa):");
            horaActual = Consola.llegeixString();
            
            Consola.escriu("Introdueix l'identificador del client: ");
            idClient = Consola.llegeixString();
            
            Consola.escriu("Introdueix l'identificador de la reserva: ");
            idReserva = Consola.llegeixString();
        
            Iterator itr = llistaReserves.iterator();
            while(itr.hasNext() && !trobat){
                r = (Reserva) itr.next();
                trobat = r.getId().equals(idReserva) && r.getClientReserva().getId().equals(idClient) && r.isActiva(horaActual);      
            }
            
            if(trobat){
                r.getLocalInicial().eliminarMoto(r.getMotoReserva());
                Consola.escriu("La reserva es correcte.\n");
                correcte = true;
            }else{
                Consola.escriu("No s'ha trobat la reserva demanada.\n");
                stop = !Consola.reintroduirDades();
            }
        }
    }

    private Local getLocal(ArrayList<Local> auxiliar, String idLocal) {
        for (int i = 0; i < auxiliar.size(); i++){
            if (auxiliar.get(i).getIdLocal().equals(idLocal)){
                return auxiliar.get(i);
            }
        }
        return null;
    }
}


