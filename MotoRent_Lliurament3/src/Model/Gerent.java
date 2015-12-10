/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author atorraag7.alumnes
 */
public class Gerent extends Usuari{
    private final Local localAGestionar;
    private final String idEmpresa;

    public Gerent(String nom, String cognom1, String cognom2, String userName, String password, Local localAGestionar, String idEmpresa){
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.userName = userName;
        this.password = password;
        this.localAGestionar = localAGestionar;
        this.idEmpresa = idEmpresa;
    }
    
    
    @Override
    public String getTipus() {
        return "Gerent";
    }
    
    public String getIdEmpresa(){
        return idEmpresa;
}
    
    
    public String toString(){
        String str;
        str = "\nGestor ID: " + idEmpresa + "\n";
	str += "-----------------\n";
	str +="Nom: " + nom + "\n";
        str += "Cognom: "+cognom1+"\n";
	str += "Usuari: " + userName + "\n";
	str += "Password: " + password + "\n";
        return str;
    }
}
