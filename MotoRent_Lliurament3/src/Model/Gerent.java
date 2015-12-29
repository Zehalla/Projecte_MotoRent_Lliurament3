package Model;

import Vista.Consola;

public class Gerent extends Usuari{
    private Local localAGestionar;

    /**
     * Constructor de la classe Gerent.
     * @param nom
     * @param cognom1
     * @param cognom2
     * @param userName
     * @param password
     * @param idEmpresa 
     */
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
    /**
     * Mètode que retorna el tipus de Gerent.
     */
    public String getTipus() {
        return "Gerent";
    }
    
    @Override
    /**
     * Mètode per a enllaçar un Gerent amb el Local que gestiona.
     */
    public void setLocalAGestionar(Local local){
        this.localAGestionar = local;
    }
    

    
    @Override
    /**
     * Mètode per imprimir la informació relativa a un Gerent.
     */
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
    public int getNMotosDisp(){
        return localAGestionar.getNMotosDisp();
    }
    
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
