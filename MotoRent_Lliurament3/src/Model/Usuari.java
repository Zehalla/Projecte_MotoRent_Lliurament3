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
public abstract class Usuari {
    protected String DNI;
    protected String userName;
    protected String password;
    protected String nom;
    protected String cognom1;
    protected String cognom2;

    /**
     * Métode que retorna el tipus d'Usuari.
     * @return 
     */
    public abstract String getTipus();

    /**
     * Métode que retorna el nom d'Usuari.
     * @return userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Mètode que retorna la contrasenya.
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    public abstract String getId();
    
    public void setLocalAGestionar(Local local){
        
    }
    
    /**
     * Métode que retorna true si newUserName es igual a userName.
     * @param newUserName
     * @return 
     */
    public boolean checkUserName(String newUserName) {
        return newUserName.equals(this.userName);
    }
    
    /**
     * Métode que retorna true si newPassword es igual a password.
     * @param newPassword
     * @return 
     */
    public boolean checkPassword(String newPassword) {
        return newPassword.equals(this.password);
    }

    public String getEstat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
