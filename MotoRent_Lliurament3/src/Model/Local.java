/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
        Iterator itr = llistaMotos.iterator();
        while(itr.hasNext()){
            Moto m = (Moto) itr.next();
            tipus = m.getEstat();
            if("Disponible".equalsIgnoreCase(tipus)){
                str += m.toString();
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
}
