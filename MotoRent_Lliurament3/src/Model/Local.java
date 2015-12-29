package Model;

import Excepcions.LlistaBuidaException;
import Excepcions.LlistaPlenaException;
import Vista.Consola;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe essencial del model que engloba totes les funcions d'un local: contenir i gestionar motos.
 * @author Oriol
 */
public class Local {
    private final String idLocal;
    private final int capacitat;
    private int ocupacio;
    private Direccio direccioLocal;
    private ArrayList<Moto> llistaMotos;
    private final Gerent gestor;

    /**
     * Constructor basic de local. Només es creen locals quan carreguem les dades, o sigui que sempre ens
     * donaran la informacio de la direccio com un string amb el mateix format.
     * @param idLocal String identificaro del local
     * @param capacitat numero maxim de motos en qualsevol estat
     * @param direccioLocal String amb la informacio de la direccio
     * @param llistaMotos Conte una llista de motos que conte el local.
     * @param gestor gestor encarregat del local
     */
    public Local(String idLocal, int capacitat, String[] direccioLocal, ArrayList<Moto> llistaMotos, Gerent gestor){
        this.idLocal = idLocal;
        this.capacitat = capacitat;
        this.direccioLocal = crearDireccio(direccioLocal);
        this.llistaMotos = llistaMotos;
        this.gestor = gestor;
    }
    
    /**
     * Metode auxiliar del constructor que a partir de un array de Strings crida al constructor de direccio.
     * @param adreca
     * @return 
     */
    private Direccio crearDireccio(String[] adreca){
            return new Direccio(adreca[0], adreca[1], adreca[2], adreca[3]);
    }
    
    /**
     * UC 3_5 Metode que compta el numero de motos de tipus disponibles de un local.
     * @return numero de motos que son disponibles per a llogar.
     */
    public int getNMotosDisp(){
        int NMotosDisp = 0;
        for (Moto m : llistaMotos) { 
            if(m.getEstat().equalsIgnoreCase("Disponible")){
                NMotosDisp ++;
            }
        }
        return NMotosDisp;
    }

    /**
     * Metode que mostre la info del local en un string. No ho escriu a la consola, nomes ho retorna.
     * @return String que conte tota la informacio de local.
     */
    public String mostrarDadesLocal() {
        return "-----\n" + "\nID: " + idLocal + "\n"+direccioLocal.toString() +"Capacitat local: " + capacitat;
    }

    /**
     * Metode que mostra tota informacio de totes les motos disponibles que te el local una per una. Si no hi ha motos
     * disponibles llavors retorna la excepcio perque no hi ha res per mostrar.
     * @return un String amb el format correcte que conte tota la info de totes les motos del local.
     * @throws LlistaBuidaException Si el local no te cap moto de estat disponible
     */
    public String mostrarMotosDisponibles() throws LlistaBuidaException {
        String tipus, str = "";
        int i = 0;
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            i++;
            Moto m = (Moto) itr.next();
            tipus = m.getEstat();
            if("Disponible".equalsIgnoreCase(tipus)){
                str += Integer.toString(i) + ": ";
                str += m.toString();
            }
        }
        if (!str.equals("")){
            return str;
        }else{
            throw new LlistaBuidaException();
        }
    }
    
    /**
     * Semblant a mostrarMotosDisponibles pero mostra totes les motos.
     * @return 
     */
    public String mostrarMotos(){
        String str = "";
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next();
            str += m.toString();
        }
        return str;
    }
    
    /**
     * Donat un identificador busca la moto del local que te aquest identificador amb un for. En cas de no
     * trobar cap, retorna null.
     * @param id Identificador de la moto que busquem.
     * @return Moto amb la id desitjada.
     */
    public Moto getMoto(String id){ 
        for (Moto llistaMoto : llistaMotos) {
            if (llistaMoto.getIdMoto().equals(id)) {
                return llistaMoto;
            }
        }
        return null;
    }
 
    /**
     * 
     */
    public void obtenirMotosLocal(){
        int i;
        for (i = 0; i < llistaMotos.size(); i++){
            Consola.escriu(llistaMotos.get(i).toString());
        }
    }

    /**
     * Metode que donat una moto, l'elimina del local.
     * @param moto
     * @return 
     */
    public boolean eliminarMoto(Moto moto){
        return llistaMotos.remove(moto);
    }
    
    /**
     * Metode que afegeix un objecte moto a la llista de motos del local. Si el local esta ple, retorna la 
     * excepcio de llista plena, perque no es pot afegir.
     * @param moto moto que es vol afegir.
     * @throws LlistaPlenaException si el local esta ple.
     */
    public void afegirMoto(Moto moto) throws LlistaPlenaException{
        if (llistaMotos.size() < capacitat){
            llistaMotos.add(moto);
        }else{
            throw new LlistaPlenaException();
        }
    }
    
    /**
     * Metode semblant a afegirMoto(Moto moto) pero ara no passem el objecte construit sino que li passem
     * els parametres i la moto es crea aqui.
     * @param id
     * @param matricula
     * @param model
     * @param color
     * @param estat
     * @throws LlistaPlenaException 
     */
    public void afegirMoto(String id, String matricula, String model, String color, String estat) throws LlistaPlenaException{
        if (llistaMotos.size() < capacitat){
            llistaMotos.add(new Moto(id, matricula, model, color, estat));
        }else{
            throw new LlistaPlenaException();
        }
    }
    
    /**
     * Metode basic que retorna un string de tota la informacio del local.
     * @return 
     */
    @Override
    public String toString(){
        String str;
        ocupacio = llistaMotos.size();
        str = "\nLocal amb ID: " + idLocal + "\n";
	str += "--------------------------------------------------\n";
	str += "Capacitat: " + capacitat + "\n";
        str += "Ocupacio: " + ocupacio +"\n";
	str += "Gestor ID: " + gestor.getId() + "\n";
	str += direccioLocal.toString() + "\n";
        return str;
    }
    
    /**
     * Metode que calcula el percentatge d'ocupacio del local i determina si s'han d'importar motos o exportar-les en funcio del nombre de motos del local i del seu percentatge d'ocupacio
     * @return accio a emprendre
     */
    String gestionarLocal() {
        String accio = "Cap";
        int ocupacioPercent;
        ocupacio = llistaMotos.size();
        ocupacioPercent = (int)(100.*ocupacio/capacitat);
        if(ocupacioPercent >= 75 && ocupacio > 5){
            Consola.escriu("El local te massa motos.\n");
            accio = "Exportar";
        }
        else if(ocupacio < 5){
            Consola.escriu("El local no te prou motos.\n");
            accio = "Importar";
        }else if (ocupacio >= 5){
            Consola.escriu("El local te un nombre correcte de motos.\n");
        }
        return accio;
    }
    /**
     * Metode que demana al gerent quantes motos vol importar, es comprova que el nombre demanat sigui valid
     * @return motosAImportar
     */
    int demanarNombreMotosAImportar() {
        int motosAImportar, minim;
        ocupacio = llistaMotos.size();
        minim = 5 - ocupacio;
        Consola.escriu("Quantes motos voleu importar? (Minim: ");
        Consola.escriu(minim);
        Consola.escriu(")\n");
        motosAImportar = Consola.llegeixInt();
        while (motosAImportar < minim || ocupacio + motosAImportar > capacitat){
            if (minim > motosAImportar){
                Consola.escriu("Heu de seleccionar un valor major a ");
                Consola.escriu(minim);
                Consola.escriu("\n");
            }else if(ocupacio + motosAImportar > capacitat){
                Consola.escriu("El local no te prou capacitat per importar tantes motos.\n");
                Consola.escriu("Com a molt podeu importar ");
                Consola.escriu(capacitat - ocupacio);
                Consola.escriu(" motos.\n");
            }
            motosAImportar = Consola.llegeixInt();
        }
        return motosAImportar;
    }
    /**
     * Metode que demana al gerent quantes motos vol exportar, es comprova que el nombre demanat sigui valid
     * @return motosAExportar
     */
    int demanarNombreMotosAExportar() {
        boolean error = true;
        String tmp;
        int motosAExportar = 0;
        Consola.escriu("Voleu exportar l'excedent de motos: (Si/No)\n");
        while (error){
            tmp = Consola.llegeixString();
            if(tmp.equalsIgnoreCase("SI")){
                ocupacio = llistaMotos.size();
                error = false;
                motosAExportar = (int) (ocupacio - capacitat*0.25);
                while(ocupacio - motosAExportar < 5){
                    motosAExportar -= 1;
                }
            }else if(tmp.equalsIgnoreCase("NO")){
                error = false;
            }else{
                Consola.escriu("Escrigui si o no.\n");
            }
        }
        return motosAExportar;
    }
    /**
     * Metode que informa al gerent sobre quantes motos s'importaran al local
     * @param motosAImportar 
     */
    public void mostrarInfoImportacio(int motosAImportar) {
        Consola.escriu("S'importaran ");
        Consola.escriu(motosAImportar);
        Consola.escriu(" motos del local:\n");
        Consola.escriu(direccioLocal.toString());
    }
    /**
     * Metode que agafa la primera moto disponible que troba en el local
     * @return Moto mi
     */
    public Moto getMotoDisponible() {
        String disponible;
        for (Moto mi: llistaMotos){
            disponible = mi.getEstat();
            if (disponible.equalsIgnoreCase("DISPONIBLE")){
                return mi;
            }
        }
        return null;
    }
    /**
     * Metode que informa al gerent sobre quantes motos s'exportaran del local
     * @param motosAExportar 
     */
    public void mostrarInfoExportacio(int motosAExportar) {
        Consola.escriu("S'exportaran ");
        Consola.escriu(motosAExportar);
        Consola.escriu(" motos del local:\n");
        Consola.escriu(direccioLocal.toString());
    }
    /**
     * Metode que calcula quantes motos es poden importar del local
     * @param nombreMotosDisponibles
     * @return nombreMotosImportables
     */
    public int calcMotosImportables(int nombreMotosDisponibles) {
        int nombreMotosImportables, nombreMotosNoImportables;
        ocupacio = llistaMotos.size();
        nombreMotosNoImportables = ocupacio - nombreMotosDisponibles;
        if (nombreMotosNoImportables < 5){
            nombreMotosNoImportables = 5;
        }
        nombreMotosImportables = ocupacio - nombreMotosNoImportables;
        return nombreMotosImportables;
    }
    /**
     * Metode que calcula quanta capacitat lliure te el local
     * @return capacitatDisponible 
     */
    public int getCapacitatDisponible() {
        int capacitatDisponible = 0;
        ocupacio = llistaMotos.size();
        if (100.*ocupacio/capacitat < 75){
            capacitatDisponible = capacitat - ocupacio;
        }
        return capacitatDisponible;
    }
    /**
     * Metode que exporta una moto al local indicat
     * @param localPerExportar 
     */
    void exportarMoto(Local localPerExportar) {
        Moto motoExportar;
        motoExportar = getMotoDisponible();
        Consola.escriu("Exportant "+ motoExportar.toString() + "\n");
        eliminarMoto(motoExportar);
        try{
            localPerExportar.afegirMoto(motoExportar);
        }catch (LlistaPlenaException ex){
            Consola.escriu(ex.getMessage());
        }
    }
    /**
     * Metode que importa una moto del local indicat
     * @param localPerImportar 
     */
    void importarMoto(Local localPerImportar) {
        Moto motoImportar;
        motoImportar = localPerImportar.getMotoDisponible();
        localPerImportar.eliminarMoto(motoImportar);
        Consola.escriu("Important "+ motoImportar.toString() + "\n");
        try{
            afegirMoto(motoImportar);
        }catch (LlistaPlenaException ex){
            Consola.escriu(ex.getMessage());
        }
    }
    
    /**
     * UC 3.5 Metode que comprova si un ID de moto es troba a dins del local, o sigui si alguna moto te 
     * aquest id. Serveix unicament per saber que el client ha introduit al sistema un valor correcte.
     * @param motoId
     * @return 
     */
    public boolean checkID(String motoId){
        for (Moto llistaMoto : llistaMotos) {
            if (llistaMoto.getIdMoto().equals(motoId) && "Disponible".equals(llistaMoto.getEstat())) {
                return true;
            }
        }
        return false;
    }    
    /**
     * Mètode que retorna el Gerent que gestiona el Local.
     * @return 
     */
    public Gerent getGestor() {
        return gestor;
    }
    
    //////////////////////GETTERS I SETTERS
    
    public String getIdLocal(){
        return idLocal;
    }
    
    public int getOcupacio(){
        this.ocupacio = llistaMotos.size();
        return this.ocupacio;
    }
    
    public int getCapacitat() {
        return capacitat;
    }
    
    public Direccio getDireccioLocal() {
        return direccioLocal;
    }

    public void setDireccioLocal(Direccio direccioLocal) {
        this.direccioLocal = direccioLocal;
    }

    public void setLlistaMotos(ArrayList<Moto> llistaMotos) {
        this.llistaMotos = llistaMotos;
    }
    
    public int getNMotos(){
        return llistaMotos.size();
    }
    
}
