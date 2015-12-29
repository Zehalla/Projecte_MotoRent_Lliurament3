package Model;

import Excepcions.LlistaBuidaException;
import Excepcions.LlistaPlenaException;
import Vista.Consola;
import java.util.ArrayList;
import java.util.Iterator;


public class Local {
    private final String idLocal;
    private final int capacitat;
    private int ocupacio;
    private Direccio direccioLocal;
    private ArrayList<Moto> llistaMotos;
    private final Gerent gestor;

    public Gerent getGestor() {
        return gestor;
    }
    
    public Local(){
        this.idLocal = null;
        this.capacitat = 0;
        this.gestor = null;
    }
    
    public Local(String idLocal, int capacitat, String[] direccioLocal, ArrayList<Moto> llistaMotos, Gerent gestor){
        this.idLocal = idLocal;
        this.capacitat = capacitat;
        this.direccioLocal = crearDireccio(direccioLocal);
        this.llistaMotos = llistaMotos;
        this.gestor = gestor;
    }
    
    
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

    public String mostrarDadesLocal() {
        return "-----\n" + "\nID: " + idLocal + "\n"+direccioLocal.toString() +"Capacitat local: " + capacitat;
    }

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
    
    public String mostrarMotos(){
        String str = "";
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next();
            str += m.toString();
        }
        return str;
    }
    
    public Moto getMoto(int i){
        return llistaMotos.get(i);
    }
    
    public Moto getMoto(String id){ 
        for (Moto llistaMoto : llistaMotos) {
            if (llistaMoto.getIdMoto().equals(id)) {
                return llistaMoto;
            }
        }
        return null;
    }
 

    public void obtenirMotosLocal(){
        int i;
        for (i = 0; i < llistaMotos.size(); i++){
            Consola.escriu(llistaMotos.get(i).toString());
        }
    }

    public boolean eliminarMoto(Moto moto){
        return llistaMotos.remove(moto);
    }
    
    public void afegirMoto(Moto moto) throws LlistaPlenaException{
        if (llistaMotos.size() < capacitat){
            llistaMotos.add(moto);
        }else{
            throw new LlistaPlenaException();
        }
    }
    
    public void afegirMoto(String id, String matricula, String model, String color, String estat) throws LlistaPlenaException{
        if (llistaMotos.size() < capacitat){
            llistaMotos.add(new Moto(id, matricula, model, color, estat));
        }else{
            throw new LlistaPlenaException();
        }
    }
    
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
    
    public int getNMotos(){
        return llistaMotos.size();
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
    
    public boolean checkID(String motoId){
        for (int i = 0; i < llistaMotos.size(); i++){
            if (llistaMotos.get(i).getIdMoto().equals(motoId) && "Disponible".equals(llistaMotos.get(i).getEstat())){
                return true;
            }
        }
        return false;
    }
}
