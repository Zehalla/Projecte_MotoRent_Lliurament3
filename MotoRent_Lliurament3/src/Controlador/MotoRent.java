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


public class MotoRent {
    private final ArrayList<Local> llistaLocals;
    private final ArrayList<Usuari> llistaUsuaris;
    private final ArrayList<Reserva> llistaReserves;
    private Usuari usuariLogat;
    private int lastIDreserva;

    /**
     * Constructor de MotoRent que inicialitza les tres llistes.
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
    /**
     * Mètode cridat quan es vol guardar un Local al Sistema.
     * Usat únicament al carregar dades al Sistema.
     * @param idLocal
     * @param capacitat
     * @param direccioLocal
     * @param idGestor 
     */
    public void guardarLocal(String idLocal, int capacitat, String[] direccioLocal, String idGestor){
        Gerent gestor = (Gerent) buscarUsuari(idGestor);
        llistaLocals.add(new Local(idLocal, capacitat, direccioLocal, new ArrayList<>(), gestor));
    }
    
    /**
     * Mètode cridat quan es vol guardar una Moto al Sistema.
     * Usat únicament al carregar dades al Sistema.
     * @param idMoto
     * @param matricula
     * @param model
     * @param color
     * @param estatMoto 
     */
    public void guardarMoto(String idMoto, String matricula, String model, String color, String estatMoto){
        try{
            llistaLocals.get(llistaLocals.size()-1).afegirMoto(idMoto, matricula, model, color, estatMoto);
        }catch(LlistaPlenaException ex){
            Consola.escriu(ex.getMessage());
        }
    }
    
    /**
     * Mètode cridat quan es vol guardar una Reserva al Sistema.
     * Usat únicament al carregar dades al Sistema.
     * @param id
     * @param idClient
     * @param idMoto
     * @param cost
     * @param falta
     * @param local_inici
     * @param local_fi
     * @param dataInicial
     * @param horaInicial
     * @param dataFinal
     * @param horaFinal 
     */
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
    
    /**
     * Mètode cridat quan es vol guardar un Client al Sistema.
     * Usat únicament al carregar dades al Sistema.
     * @param id
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
    public void guardarClient(String id, String nom, String cognom1, String cognom2, String DNI, String userName, String password, boolean vip, int faltes, String[] direccio){
        llistaUsuaris.add(new Client(id, nom, cognom1, cognom2, DNI, userName, password, vip, faltes, direccio));
    }
    
   /**
    * Mètode cridat quan es vol guardar un Gerent al Sistema.
    * Usat únicament al carregar dades al Sistema.
    * @param nom
    * @param cognom1
    * @param cognom2
    * @param userName
    * @param password
    * @param idEmpresa 
    */
    public void guardarGerent(String nom, String cognom1, String cognom2, String userName, String password,  String idEmpresa){
        llistaUsuaris.add(new Gerent(nom, cognom1, cognom2, userName, password, idEmpresa));
    }
    
    /**
     * Mètode cridat quan es vol guardar un Administrador al Sistema.
     * Usat únicament al carregar dades al Sistema.
     * @param nom
     * @param cognom1
     * @param cognom2
     * @param userName
     * @param password
     * @param idEmpresa 
     */
    public void guardarAdministrador(String nom, String cognom1, String cognom2, String userName, String password, String idEmpresa){
        llistaUsuaris.add(new Administrador(nom, cognom1, cognom2, userName, password, idEmpresa));
    }
    
    /**
     * Mètode cridat un cop s'han guardat totes les dades des de l'arxiu XML.
     * Realitza una serie de comprovacions per a mantenir la coherència de dades.
     * Les comprovacions són:
     * 1. Relaciona cada Reserva amb el Client que l'ha efectuat.
     * 2. Comprova i adequa les Motos al seu estat adient.
     * 3. Posa cada Client al seu estat adient.
     * 4. Relaciona cada Local amb el Gerent que el gestiona i viceversa.
     * 5. Finalment, mostra les dades per a comprovar que són correctes.
     */
    public void comprovacionsPrevies(){
        comprovarReservesClients();
        comprovarEstatsMotos();
        comprovarEstatsClients();
        comprovarLocalsAGestionar();
        mostrarDades(); //Metodes per a testeig.
    }
    
    /**
     * Mètode que recorre la llista de Reserves i relaciona cada Reserva amb el seu Client.
     * A part el Client que realitza la reserva, ara rep l'estat de Amb Reserva.
     */
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
    
    /**
     * Mètode que recorre la llista de Reserves i posa a estat Reservada cada Moto que hi està involucrada.
     */
    private void comprovarEstatsMotos(){
        int i;
        for (i = 0; i < llistaReserves.size(); i++){
            llistaReserves.get(i).getMotoReserva().setEstat("reservada");
        }
    }
    
    /**
     * Mètode que recorre la llista d'Usuaris i comprova si algun Client té més de 3 faltes.
     * En cas afirmatiu, assigna a aquell Client l'estat de Desactivat.
     */
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
    
    /**
     * Mètode que relaciona cada Gerent del Sistema amb el local que gestiona.
     */
    private void comprovarLocalsAGestionar(){
        int i;
        for (i = 0; i < llistaLocals.size(); i++){
            llistaLocals.get(i).getGestor().setLocalAGestionar(llistaLocals.get(i));
        }
    }
    
    /**
     * Mètode que donat un id realitza una cerca al llistat d'Usuaris del Sistema i retorna l'Usuari al qual li correspon aquell id.
     * Només és usat al carregar les dades al Sistema.
     * @param idUsuari
     * @return 
     */
    private Usuari buscarUsuari(String idUsuari){
        int i;
        for (i = 0; i<llistaUsuaris.size();i++){
            if (llistaUsuaris.get(i).getId().equals(idUsuari)){
                return llistaUsuaris.get(i);
            }
        }
        return null;
    }
    
    /**
     * Mètode que donat un id, realitza una cerca al llistat de Locals del Sistema i retorna el Local al qual li correspon aquell id.
     * Només és usat al carregar les dades al Sistema.
     * @param idLocal
     * @return 
     */
    private Local buscarLocal(String idLocal){
        int i;
        for (i = 0; i < llistaLocals.size(); i++){
            if (llistaLocals.get(i).getIdLocal().equals(idLocal)){
                return llistaLocals.get(i);
            }
        }
        return null;
    }
  
    /**
     * Mètode que imprimeix totes les dades del Sistema.
     * Ordre:
     * 1. Usuaris (Clients, Gerents, Administradors).
     * 2. Locals + Motos del Local.
     * 3. Reserves.
     * 4. Per a cada Client, imprimeix l'historic de Reserves.
     */
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
    /*----------------------------------------------------------------
    ------------------------------------------------------------------
    -----------------------------------------------------------------*/
    
    /**
     * UC 1. Métode que permet registrar un Client
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
    * UC 1_1. Métode que comprova si el newUserName ja esta en ús.
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
     * UC 2. Mètode que permet a un Usuari logar-se.
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
   
   /*----------------------------------------------------------------
    ------------------------FER RESERVA------------------------------
    -----------------------------------------------------------------*/
   
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
        String dataInicial, horaInicial;
        String dataFinal, horaFinal;
        Local localInici = null;
        Local localFinal = null;
        Reserva r = null;
        ArrayList<Local> auxiliar;
        Client clientReserva = (Client) usuariLogat;
        Local l;
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
        try{
            Consola.escriu(localInici.mostrarMotosDisponibles());
        }catch(LlistaBuidaException ex){
            Consola.escriu(ex.getMessage());
            return;
        }

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
        while (!correcte) {
            Consola.escriu("Selecciona la data de sortida (dd/mm/aaaa): ");
            dataInicial = Consola.llegeixString();
            Consola.escriu("Selecciona la hora de sortida (hh:mm:ss): ");
            horaInicial = Consola.llegeixString();
            
            Consola.escriu("Selecciona la data de finalització (dd/mm/aaaa): ");
            dataFinal = Consola.llegeixString();
            Consola.escriu("Selecciona la hora de finalització (hh:mm:ss)");
            horaFinal = Consola.llegeixString();
            //CREACIO DE RESERVA 
            r = new Reserva("r"+Integer.toString(lastIDreserva), 0, false, false, 0, dataInicial, horaInicial, dataFinal, horaFinal, localInici, localFinal, clientReserva, idMoto);            

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
            if (l.getOcupacio() > 0) {
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
            if (l.getOcupacio() < l.getCapacitat()) {
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
     * Mètode que donat un llista de Locals i un id retorna la primera ocurrència del Local amb l'id especificat.
     * @param auxiliar
     * @param idLocal
     * @return 
     */
    private Local getLocal(ArrayList<Local> auxiliar, String idLocal) {
        for (int i = 0; i < auxiliar.size(); i++){
            if (auxiliar.get(i).getIdLocal().equals(idLocal)){
                return auxiliar.get(i);
            }
        }
        return null;
    }
    
    /*----------------------------------------------------------------
    ------------------------------------------------------------------
    -----------------------------------------------------------------*/
    
   
    
    /**
     * UC Metode de gerent que engloba totes les accions que es fan quan un client retorna una moto.
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
        String horaActual, diaActual;
        
        while(!correcte && !stop){
            Consola.escriu("Introdueix la data 'actual' (dd/mm/aaaa):");
            diaActual = Consola.llegeixString();
            Consola.escriu("Introdueix la hora 'actual' (hh:mm:ss): ");
            horaActual = Consola.llegeixString();
            Consola.escriu("Introdueix l'identificador del client: ");
            idClient = Consola.llegeixString();
            
            Consola.escriu("Introdueix l'identificador de la reserva: ");
            idReserva = Consola.llegeixString();
        
            Iterator itr = llistaReserves.iterator();
            while(itr.hasNext() && !trobat){
                r = (Reserva) itr.next();
                trobat = r.getId().equals(idReserva) && r.getClientReserva().getId().equals(idClient) && r.isActiva(diaActual, horaActual);      
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
    
    /*----------------------------------------------------------------
    ----------------OPERACIONS DE L'ADMINISTRADOR---------------------
    -----------------------------------------------------------------*/
    /**
     * UC 6. Mostrar totes les Motos que té el Sistema.
     */
    public void mostrarTotesLesMotos(){
        int i;
        Consola.escriu("\nLLISTAT DE TOTES LES MOTOS:\n");
        for (i = 0; i < llistaLocals.size(); i++){
            llistaLocals.get(i).obtenirMotosLocal();
        }
    }
    
    /**
     * Mètode que permet veure els Locals que tenen menys de 5 Motos.
     */
    public void veureLocalsPocaPoblacioMotos(){
        int i;
        Consola.escriu("\nLLISTAT DELS LOCALS QUE TENEN MENYS DE 5 MOTOS:\n");
        for (i = 0; i <llistaLocals.size(); i++){
            if (llistaLocals.get(i).getOcupacio() < 5){
                Consola.escriu(llistaLocals.get(i).toString());
            }
        }
    }
    
    /**
     * Mètode que permet veure els Locals que tenen més d'un 75% d'ocupació.
     */
    public void veureLocalsMoltaPoblacioMotos(){
        int i;
        Consola.escriu("\nLLISTAT DELS LOCALS QUE TENEN MES DEL 75% DE LA CAPACITAT OCUPADA:\n");
        for (i = 0; i < llistaLocals.size(); i++){
            if (llistaLocals.get(i).getOcupacio()/llistaLocals.get(i).getCapacitat() > 0.75){
                Consola.escriu(llistaLocals.get(i).toString());
            }
        }
    }
    
 
   /**
    * UC 8. Generar l'informe d'un mes concret.
    * @param mes 
    */ 
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
    /**
     * UC 8_1. Comprovar data correcte.
     * @param mes
     * @return 
     */
    private boolean comprovarDataCorrecte(String mes) {
        return (Integer.parseInt(mes)) <= (Integer.parseInt(Consola.llegeixDataSistema().getMes())+1); // el +1 es perque
                                                                                                    // el Date té representa els mesos de l'any de 0 a 11.
    }
    
    /*----------------------------------------------------------------
    ------------------------------------------------------------------
    -----------------------------------------------------------------*/


}


