package Model;

public abstract class Usuari {
    protected String id;
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

    /**
     * Mètode que retorna l'id de l'Usuari en qüestió
     * @return 
     */
    public String getId(){
        return this.id;
    }
    
    public void setLocalAGestionar(Local local){
        
    }
    
    /**
     * UC 2_1. Métode que retorna true si newUserName es igual a userName.
     * @param newUserName
     * @return 
     */
    public boolean checkUserName(String newUserName) {
        return newUserName.equals(this.userName);
    }
    
    /**
     * UC 2_2. Métode que retorna true si newPassword es igual a password.
     * @param newPassword
     * @return 
     */
    public boolean checkPassword(String newPassword) {
        return newPassword.equals(this.password);
    }

    
    /*----------------------------------------------------------------
    -------------METODES QUE IMPLEMENTARAN LES SUBCLASSES-------------
    -----------------------------------------------------------------*/
    public String gestionarLocal() {
        return null;    
    }

    public int demanarNombreMotosAImportar() {
        return -1;    
    }

    public int demanarNombreMotosAExportar() {
        return -1;
    }

    public int getNMotosDisp() {
        return -1;
    }

    public String getEstat() {
        return null;
    }

    public void exportarMotos(int motosAExportar, Local localPerExportar) {
    }

    public void importarMotos(int motosAImportar, Local localPerImportar) {
    }


}
