/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controlador.MotoRent;
import Vista.Consola;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author atorraag7.alumnes
 */
public class Local {
    private final String idLocal;
    private final int capacitat;
    private Direccio direccioLocal;
    private ArrayList<Moto> llistaMotos;
    private final String idGestor;

    public String getIdGestor() {
        return idGestor;
    }
    
    public Local(){
        this.idLocal = null;
        this.capacitat = 0;
        this.idGestor = null;
    }
    
    public Local(String idLocal, int capacitat, Direccio direccioLocal, ArrayList<Moto> llistaMotos, ArrayList<Reserva> llistaReserves, String idGestor){
        this.idLocal = idLocal;
        this.capacitat = capacitat;
        this.direccioLocal = direccioLocal;
        this.llistaMotos = llistaMotos;
        this.idGestor = idGestor;
    }
    
    
    public int getNMotosDisp(){
        int NMotosDisp = 0;
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next(); 
            if(m.getEstat().equalsIgnoreCase("Disponible")){
                NMotosDisp ++;
            }
        }
        return NMotosDisp;
    }

    public String mostrarDadesLocal() {
        return "\n"+direccioLocal.toString() +"\nCapacitat local: " + capacitat;
    }

    public String mostrarMotosDisponibles() {
        String tipus, str = "";
        int i = 0;
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next();
            tipus = m.getEstat();
            if("Disponible".equalsIgnoreCase(tipus)){
                str += i+": ";
                str += m.toString();
                i ++;
            } else {
            }
        }return str;
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
 

    public Moto getMoto(int index){
       return llistaMotos.get(index);
    }

    public boolean eliminarMoto(Moto moto){
        return llistaMotos.remove(moto);
    }
    
    public void eliminarMoto2(int index){
        int i = 0;
        String tipus;
        
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next();
            tipus = m.getEstat();
            if("Disponible".equalsIgnoreCase(tipus)){
                i++;
            }
            if(index == i){
                llistaMotos.remove(i);
            }
        }  
    }
    
    public void afegirMoto(Moto moto){
        if (llistaMotos.size() < capacitat){
            llistaMotos.add(moto);
        }else{
            //throw new LlistaPlenaException();
        }
    }
    
    public String getIdLocal(){
        return idLocal;
    }
    
    public int getCapacitat() {
        return capacitat;
    }

    public int getNumeroMotosActual(){
        return llistaMotos.size();
    }
    
    public Direccio getDireccioLocal() {
        return direccioLocal;
    }

    public void setDireccioLocal(Direccio direccioLocal) {
        this.direccioLocal = direccioLocal;
    }

    public ArrayList<Moto> getLlistaMotos() {
        return llistaMotos;
    }

    public void setLlistaMotos(ArrayList<Moto> llistaMotos) {
        this.llistaMotos = llistaMotos;
    }
    
    @Override
    public String toString(){
        String str;
        str = "\nlocal amb ID: " + idLocal + "\n";
	str += "--------------------------------------------------\n";
	str += "Capacitat: " + capacitat + "\n";
	str += "Gestor ID: " + idGestor + "\n";
	str += direccioLocal.toString() + "\n";
        return str;
    }
    
    public int getNMotos(){
        return llistaMotos.size();
    }  

    public void gestionarLocal(MotoRent controlador) {
        double ocupacio;
        int nMotos, nM;
        boolean confirmacio;
        
        nMotos = llistaMotos.size();
        
        Consola.escriu(toString());
        Consola.escriu(mostrarMotos());
        
        ocupacio = nMotos*100./capacitat;
        if ((nMotos >= 5) && (ocupacio < 75)){
            Consola.escriu("El local te ");
            Consola.escriu(nMotos);
            Consola.escriu(" motos i es troba al ");
            Consola.escriu((int)ocupacio);
            Consola.escriu("% d'ocupacio.\n");
        }else if(nMotos < 5){
            Consola.escriu("El local te menys de 5 motos. Has de importar motos.\n");
            nM = 5 - nMotos;
            controlador.importarMotos(this.llistaMotos, nM);
        }else if(ocupacio >= 75){
            Consola.escriu("El local te massa motos. Hauries d'exportar-ne algunes.\n");
            Consola.escriu("Vols exportar ara les motos?\n");
            confirmacio = confirmarImportacio();
            if (confirmacio){
                nM = (int) Math.round(nMotos - capacitat*0.25);
                if (5 < nMotos - nM){
                    nM --;
                }
                if (nM > 0){
                    Consola.escriu("Es procedira a l'exportacio.\n");
                    controlador.exportarMotos(this.llistaMotos, nM);
                }else{
                    Consola.escriu("Ups, si exportam motos el local es quedaria amb menys de 5 motos.\n");
                }
            }
            
        }
    }
    private boolean confirmarImportacio(){
        String control;
        do {
            control = Consola.llegeixString();
            if (control.equals("") || control.equalsIgnoreCase("Y") || control.equalsIgnoreCase("YES") || control.equalsIgnoreCase("S") || control.equalsIgnoreCase("SI")) {
                return true;
            } else if (control.equalsIgnoreCase("NO") || control.equalsIgnoreCase("N") || control.equalsIgnoreCase("NOP") || control.equalsIgnoreCase("NOPE")) {
                return false;
            }else{
                Consola.escriu("Introdueixi 'No' per cancelar, premi Intro per confirmar.\n" );
            }
        } while (!control.equals("") && !control.equalsIgnoreCase("NO"));
        return false;
    }
}
