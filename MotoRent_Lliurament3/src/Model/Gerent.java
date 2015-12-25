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
    private Local localAGestionar;

    public Gerent(String nom, String cognom1, String cognom2, String userName, String password, String idEmpresa){
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.userName = userName;
        this.password = password;
        this.id = idEmpresa;
        this.localAGestionar = null;
    }
    
    @Override
    public String getTipus() {
        return "Gerent";
    }
    
    @Override
    public String getId(){
        return id;
    }
    
    @Override
    public void setLocalAGestionar(Local local){
        this.localAGestionar = local;
    }
    

    
    @Override
    public String toString(){
        String str;
        str = "\nGestor ID: " + this.id + "\n";
	str += "-----------------\n";
	str +="Nom: " + nom + "\n";
        str += "Cognom: "+cognom1+"\n";
	str += "Usuari: " + userName + "\n";
	str += "Password: " + password + "\n";
        str += "Local a gestionar: \n"+ localAGestionar.toString() +"\n";
        return str;
    }
    /**
     * Metode que determina si el local gestionat necesita importar o exportar motos
     * @return accio a empendre
     */
    @Override
    public String gestionarLocal(){
            String accio;
            Consola.escriu(localAGestionar.toString());
            accio = localAGestionar.gestionarLocal();
            return accio;
    }
    /**
     * Metode que demana el nombre de motos que es volen importar
     * @return nombre de motos a importar
     */
    @Override
    public int demanarNombreMotosAImportar(){
        return localAGestionar.demanarNombreMotosAImportar();
    }
    /**
     * Metode que demana el nombre de motos que es volen exportar
     * @return nombre de motos a exportar
     */
    @Override
    public int demanarNombreMotosAExportar(){
        return localAGestionar.demanarNombreMotosAExportar();
    }
    /**
     * Metode que calcula el nombre de motos disponibles del local
     * @return nombre de motos disponibles en el local
     */    
    @Override
    public int getNMotosDisp(){
        return localAGestionar.getNMotosDisp();
    }
    /**
     * Metode que exporta el nombre de motos indicat al local donat
     * @param motosAExportar
     * @param localPerExportar 
     */
    @Override
    public void exportarMotos(int motosAExportar, Local localPerExportar){
        boolean control = true;
        int nombreMotos, i = 0;
        while (i < motosAExportar && control){
                nombreMotos = getNMotosDisp();
                if (nombreMotos > 0){
                    localAGestionar.exportarMoto(localPerExportar);
                    i += 1;
                }else{
                    control = false;
                }
            }
            if (i < motosAExportar){
                Consola.escriu("Error. Nomes s'han pogut exportar ");
                Consola.escriu(i+1);
                Consola.escriu(" de ");
                Consola.escriu(motosAExportar);
                Consola.escriu(" motos.\n");
            }else{
                Consola.escriu("La exportacio s'ha completat amb exit.\n");
            }
    }
    /**
     * Metode que importa el nombre de motos indicat del local donat
     * @param motosAImportar
     * @param localPerImportar 
     */
    @Override
    public void importarMotos(int motosAImportar, Local localPerImportar){
        boolean control = true;
        int nombreMotos, i = 0;
        while (i < motosAImportar && control){
                nombreMotos = localPerImportar.getNMotosDisp();
                if (nombreMotos > 0){
                    localAGestionar.importarMoto(localPerImportar);
                    i += 1;
                }else{
                    control = false;
                }
            }
            if (i < motosAImportar){
                Consola.escriu("Error. Nomes s'han pogut importar ");
                Consola.escriu(i+1);
                Consola.escriu(" de ");
                Consola.escriu(motosAImportar);
                Consola.escriu(" motos.\n");
            }else{
                Consola.escriu("La importacio s'ha completat amb exit.\n");
            }
    }
}
