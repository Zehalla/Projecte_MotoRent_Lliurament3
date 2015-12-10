/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
    
    public Local(String idLocal, int capacitat, Direccio direccioLocal, ArrayList<Moto> llistaMotos, ArrayList<Reserva> llistaReserves, String idGestor){
        this.idLocal = idLocal;
        this.capacitat = capacitat;
        this.direccioLocal = direccioLocal;
        this.llistaMotos = llistaMotos;
        this.idGestor = idGestor;
    }
    
    
    public int getNMotosDisp(){
        int NMotosDisp = 0;
        boolean check = true;
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next(); 
            //check = m.checkEstat() TODO mirar el estat de la moto per comptar-la
            if(check){
                NMotosDisp ++;
            }
        }
        return NMotosDisp;
    }

    public void mostrarDadesLocal() {
        direccioLocal.mostrarDades();
        Consola.escriu("Capacitat local: " + capacitat);
    }

    public void mostrarMotos() {
        String tipus;
        int i = 0;
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next();
            tipus = m.getTipus();
            if("Disponible".equals(tipus)){
                Consola.escriu(i+": ");
                m.mostrarDadesMoto();
            }
        }
        
    }

    //NOTA: pot retornar null si el index passat no es correcte. s'ha de assegurar.
    //En fer Reserva ja esta assegurat.
    public Moto getMoto(int index) {
        int i = 0;
        boolean trobat = false;
        String tipus;
        Moto motoReserva = null;
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext() && !trobat){
            Moto m = (Moto) itr.next();
            tipus = m.getTipus();
            if("Disponible".equals(tipus)){
                i++;
            }
            if(i == index){
                trobat = true;
                motoReserva = m;
            }
        }
        return motoReserva;
    }

    public void eliminarMoto(int index){
        int i = 0;
        String tipus;
        
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next();
            tipus = m.getTipus();
            if("Disponible".equals(tipus)){
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
}
