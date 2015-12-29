package Model;

import Vista.Consola;

public class Direccio {
    private String carrer;
    private String numero;
    private String codiPostal;
    private String poblacio;
    
    /**
     * Constructor de la classe Direccio.
     * S'usa com a pas previ a demanar les dades. 
     * S'ha implementat aquest constructor per a mantenir el disseny del
     * diagrama de classes.
     */
    public Direccio(){    
    }
    
    /**
     * Constructor de la classe Direccio.
     * @param carrer
     * @param numero
     * @param codiPostal
     * @param poblacio 
     */
    public Direccio(String carrer, String numero, String codiPostal, String poblacio){
        this.carrer = carrer;
        this.numero = numero;
        this.codiPostal = codiPostal;
        this.poblacio = poblacio;
    }
    
    /**
     * Métode que permet introduir les dades d'una Direcció.
     */
    public void introduirDades(){
        Consola.escriu("Introdueixi el carrer: ");
        this.carrer = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el numero de portal: ");
        this.numero = Consola.llegeixString();
        
        Consola.escriu("Introdueixi el codi postal: ");
        this.codiPostal = Consola.llegeixString();
        
        Consola.escriu("Introdueixi la poblacio: ");
        this.poblacio = Consola.llegeixString();    
    }
    
    @Override
    /**
     * Mètode que imprimeix per pantalla la informació referent a la Direccio.
     */
    public String toString(){
        String str;
        str = "Carrer: "+carrer+"\n";
        str += "Número: "+numero+"\n";
        str += "Codi Postal: "+codiPostal+"\n";
        str += "Població: "+poblacio+"\n";
        return str;
    }
}
