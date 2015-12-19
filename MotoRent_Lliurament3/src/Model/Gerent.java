/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Vista.Consola;

/**
 *
 * @author atorraag7.alumnes
 */
public class Gerent extends Usuari{
    private final String idEmpresa;
    private Local localAGestionar;

    public Gerent(String nom, String cognom1, String cognom2, String userName, String password, String idEmpresa){
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.userName = userName;
        this.password = password;
        this.idEmpresa = idEmpresa;
        this.localAGestionar = null;
    }
    
    @Override
    public String getTipus() {
        return "Gerent";
    }
    
    @Override
    public String getId(){
        return idEmpresa;
    }
    
    @Override
    public void setLocalAGestionar(Local local){
        this.localAGestionar = local;
    }
    

    
    @Override
    public String toString(){
        String str;
        str = "\nGestor ID: " + idEmpresa + "\n";
	str += "-----------------\n";
	str +="Nom: " + nom + "\n";
        str += "Cognom: "+cognom1+"\n";
	str += "Usuari: " + userName + "\n";
	str += "Password: " + password + "\n";
        str += "Local a gestionar: \n"+ localAGestionar.toString() +"\n";
        return str;
    }
    
    @Override
    public String gestionarLocal(){
            String accio;
            Consola.escriu(localAGestionar.toString());
            accio = localAGestionar.gestionarLocal();
            return accio;
    }
    @Override
    public int demanarNombreMotosAImportar(){
        return localAGestionar.demanarNombreMotosAImportar();
    }
    @Override
    public int demanarNombreMotosAExportar(){
        return localAGestionar.demanarNombreMotosAExportar();
    }
    
    @Override
    public Moto getMotoDisponible(){
        return localAGestionar.getMotoDisponible();
    }
    
    @Override
    public void importarMoto(Moto moto){
        localAGestionar.infoMotoImportada(moto);
        localAGestionar.afegirMoto(moto);
    }
    
    @Override
    public void exportarMoto(Moto moto){
        localAGestionar.infoMotoExportada(moto);
        localAGestionar.eliminarMoto(moto);
    }
    
    @Override
    public int getNMotosDisp(){
        return localAGestionar.getNMotosDisp();
    }
}
