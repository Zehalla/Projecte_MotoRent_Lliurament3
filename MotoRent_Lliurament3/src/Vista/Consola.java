package Vista;

import Controlador.MotoRent;
import Parser.MotoRentDataManager;
import java.util.Date;
import java.util.Scanner;
import org.xml.sax.helpers.DefaultHandler;
/**
 *
 * @author Adry
 */
 
public class Consola extends DefaultHandler{
    /*
    Atributs de la classe App.
    Contenten 4 llistes d'String, representant les opcions dels diferents menús.
    Conté un atribut Scanner per a l'entrada de dades per part de l'Usuari.
    Conté un atribut Menu que representarà els diferents menús.
    */
    private final String[] menuUsuari = {"Menú de l'Usuari:", "1.- Registrar-se.", "2.- Log-in.", "3.- Sortir"};
    private final String[] menuClient = {"Menú del Client:", "1.- Fer reserva d'una moto.", "2.- Modificar reserva.", "3.- Modificar dades personals", "4.- Donar-se de baixa.", "5.- Log-out."};
    private final String[] menuGerent = {"Menú del Gerent:", "1.- Gestionar Local", "2.- Comprovar reserva", "3.- Actualitzar estat moto", "4.- Log-out"};
    private static final Scanner scanner = new Scanner(System.in);
    private final MotoRent controlador;
    private final MotoRentDataManager dataManager;
    
    
    

    /**
     * Constructor de la Classe App.
     * Inicialitza un objecte de tipus Scanner.
     * Inicialitza un objecte de tipus Menu.
     */
    public Consola(){
        controlador = new MotoRent();
        dataManager = new MotoRentDataManager(controlador);
        dataManager.obtenirDades("data/MotoRent.xml");
        controlador.comprovarReservesClients();
        controlador.comprovarEstatsMotos();
        controlador.mostrarDades();
        controlador.mostrarTotesLesMotos();
    }
    
    /**
     * Métode que inicia l'aplicació.
     * Crea un nou objecte de tipus App i crida a la funció que inicia el programa.
     * @param args 
     */
    public static void main(String[] args) {
        Consola aplicacio = new Consola();
        aplicacio.iniciarPrograma();
    }
    
    
    public String mostrarMenu(String[] menu){
        int i;
        String str = "\n";
        for (i = 0; i<menu.length; i++){
            str += menu[i]+"\n";
        }
        return str;
    }
    
    /**
     * Métode auxiliar que demana a l'Usuari si vol tornar a un menú anterior o reintroduir les Dades
     * Retornar true si es prem intro i false si s'escriu no.
     * @return 
     */
    public static boolean reintroduirDades(){
        String control;
        escriu("Premi intro per tornar a introduir les dades o escrigui 'no' per tornar enrere.\n");
        do {
            control = llegeixString();
            if (control.equals("")) {
                return true;
            } else if (control.equalsIgnoreCase("NO")) {
                return false;
            }
        } while (!control.equals("") && !control.equalsIgnoreCase("NO"));
        return false;
    }
    
    /**
     * Métode cridat quan s'inicia l'aplicació.
     * Comença un bucle on es mostra el menú d'Usuari i es demana una opció.
     */
    private void iniciarPrograma(){
        int opcio;
        do{
            escriu(mostrarMenu(menuUsuari));
            escriu("Tria una opció: ");
            opcio = llegeixInt();
            switch (opcio) {
                case 1:
                    opcioRegistrarse();
                    break;
                case 2:
                    opcioLogin();
                    break;
                case 3:
                    opcioSortir();
                    break;
            }
        } while (opcio != 3);
    }
    
    
    /**
     * Métode cridat quan s'escull l'opció de fer Log-in.
     * Aquest és un métode auxiliar que permet escollir entre logar-se com a
     * Client o com a Gerent. (en próxims lliurament no caldrà, ja que 
     * el sistema ho detectarà automàticament).
     */
    private void opcioLogin(){
        boolean tornarMenuAnterior;
        String usuari;
        do{
            usuari = controlador.logIn();
            switch(usuari){
                case "Client":
                    opcioLoginClient();
                    tornarMenuAnterior = true;
                    break;
                case "Gerent":
                    opcioLoginGerent();
                    tornarMenuAnterior = true;
                    break;
                case "Administrador":
                    opcioLoginAdministrador();
                    tornarMenuAnterior = true;
                    break;
                default:
                    tornarMenuAnterior = !reintroduirDades();    
            }
        }while (!tornarMenuAnterior);
    }
    
    
    
    //==============================================================
    //========== METODES MENU USUARI ===============================
    //==============================================================
    private void opcioRegistrarse(){
        boolean registrat;
        registrat = !controlador.registrarUsuari();
        if (registrat){
            opcioLoginClient();
        }
    }
    
    /**
     * Métode cridat quan un Usuari es loga a l'aplicació i és un Client.
     * Mostra el menú corresponent a les accions que pot dur a terme un Client.
     */
    private void opcioLoginClient(){
        int opcio;
        do{
            escriu(mostrarMenu(menuClient));
            escriu("Tria una opció: ");
            opcio = llegeixInt();
            switch (opcio) {
                case 1:
                    opcioFerReserva();
                    break;
                case 2:
                    opcioModificarReserva();
                    break;
                case 3:
                    opcioModificarDadesPersonals();
                    break;
                case 4:
                    opcioDonarBaixa();
                    opcio = 5;
                    break;
            }
        } while (opcio != 5);
    }
    
    /**
     * Métode cridat quan un Usuari es loga com a Gerent.
     * Mostra les opcions que pot dur a terme un Gerent.
     */
    private void opcioLoginGerent(){
        int opcio;
        escriu("T'has logat com a Gerent");
        do {
            escriu(mostrarMenu(menuGerent));
            opcio = llegeixInt();
            switch (opcio) {
                case 1:
                    opcioGestionarLocal();
                    break;
                case 2:
                    opcioComprovarReserva();
                    break;
                case 3:
                    opcioActualitzarEstatMoto();
                    break;
            }
        } while (opcio != 4);
    }     
   
    /**
     * Mètode que finalitza la aplicació.
     */
    private void opcioSortir(){
        escriu("Fins aviat!");
    }
    //==============================================================
    //==============================================================
    //==============================================================
    

    //==============================================================
    //================ METODES MENU CLIENT =========================
    //==============================================================
    
    /**
     * Mètode cridat quan un Client vol fer una reserva.
     */
    private void opcioFerReserva(){
        controlador.ferReserva();
    }
    
    /**
     * Mètode cridat quan un Client vol modificar una reserva.
     */
    private void opcioModificarReserva(){
        escriu("Has modificat la teva reserva");
    }
    
    /**
     * Mètode cridat quan un Client vol modificar les seves dades personals.
     */
    private void opcioModificarDadesPersonals(){
        escriu("Has modificat les teves dades personals");
    }
    
    /**
     * Mètode cridat quan un Client vol donar-se de baixa de l'aplicació.
     */
    private void opcioDonarBaixa(){
        escriu("T'has donat de baixa correctament.");
    }
    
    
    //==============================================================
    //==============================================================
    //==============================================================
 
    
    //==============================================================
    //=========== METODES MENU GERENT ==============================
    //==============================================================
    
    /**
     * Mètode cridat quan un Gerent vol gestionar un Local.
     */
    private void opcioGestionarLocal(){
        escriu("Has gestionat el local");
    }
    
    /**
     * Mètode cridat quan un Gerent vol comprovar una reserva d'un Client.
     */
    private void opcioComprovarReserva(){
        escriu("Has comprovat la reserva");
    }
    
    /**
     * Mètode cridat quan un Gerent vol actualitzar l'estat d'una Moto.
     */
    private void opcioActualitzarEstatMoto(){
        escriu("Has actualitzat l'estat d'una moto");
    }
    
    //==============================================================
    //==============================================================
    //==============================================================
        
   
    //==============================================================
    //=======METODES DE SORTIDA ====================================
    //==============================================================

     
    /**
     * Mostra un text per pantalla (sense salt de línia al final).
     *
     * @param s text a mostrar.
     */
    public static void escriu(String s) {
        System.out.print(s);
    }

    /**
     * Mostra un enter per pantalla.
     *
     * @param i enter a mostrar.
     */
    public static void escriu(int i) {
        System.out.print(i);
    }

    /**
     * Mostra un nombre flotant per pantalla.
     *
     * @param f nombre flotant a mostrar.
     */
    public static void escriu(float f) {
        System.out.print(f);
    }

    /**
     * Llegeix un enter per teclat.
     * <p>
     * Aquesta funció no retorna fins que l'usuari ha introduït un enter. En
     * cas que l'usuari introdueixi una cadena, la funció torna a demanar un
     * enter fins que la dada introduïda sigui vàlida.
     * </p>
     *
     * @return enter introduit per l'usuari
     */
    public static int llegeixInt() {
        while (true) {
            // Llegim una cadena, per així consumir salts de línia també
            String buffer = llegeixString();
            try {
                return Integer.valueOf(buffer);
            }
            // Rebem aquesta excepció si les dades no són correctes
            catch (NumberFormatException ex) {
                escriu("Entrada incorrecta. Sisplau poseu un enter: ");
            }
        }
    }

    /**
     * Llegeix una cadena per teclat.
     * <p>
     * Es considera una cadena qualsevol entrada fins a un salt de línia. El
     * salt de línia no s'inclou a la cadena retornada.
     * </p>
     *
     * @return cadena introduida per l'usuari
     */
    public static String llegeixString() {
        return scanner.nextLine();
    }

    /**
     * Obtenir la data actual segons el sistema.
     *
     * @return data actual
     */
    public static Date llegeixDataSistema() {
        return new Date();
    }
    
    //==============================================================
    //==============================================================
    //==============================================================

    private void opcioLoginAdministrador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}


