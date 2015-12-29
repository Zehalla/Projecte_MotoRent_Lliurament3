package Model;

public class Administrador extends Usuari{

    /**
     * Constructor de la classe Administrador.
     * @param nom
     * @param cognom1
     * @param cognom2
     * @param userName
     * @param password
     * @param idEmpresa 
     */
    public Administrador(String nom, String cognom1, String cognom2, String userName, String password, String idEmpresa){
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.userName = userName;
        this.password = password;
        this.id = idEmpresa;
    }
    
    @Override
    /**
     * Mètode que retorna el tipus d'Administrador.
     */
    public String getTipus() {
        return "Administrador";
    }
    
    
    @Override
    /**
     * Mètode per a imprimir la informació relativa a un Administrador.
     */
      public String toString(){
        String str;
        str = "\nAdministrador ID: " + this.getId() + "\n";
	str += "-----------------\n";
	str +="Nom: " + nom + "\n";
        str += "Cognom: "+cognom1+"\n";
	str += "Usuari: " + userName + "\n";
	str += "Password: " + password + "\n";
        return str;
    }
}
