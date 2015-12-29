package Model;

import Model.Estats.*;


public class Moto {
    private final String idMoto;
    private final String matricula;
    private final String model;
    private final String color;
    private EstatMoto estatMoto;


    /**
     * Constructor de la classe Moto.
     * @param idMoto
     * @param matricula
     * @param model
     * @param color
     * @param estat 
     */
    public Moto(String idMoto, String matricula, String model, String color, String estat){
        this.idMoto = idMoto;
        this.matricula = matricula;
        this.model = model;
        this.color = color;
        this.setEstat(estat);
    }

    /**
     * Mètode cridat per a saber l'estat d'una Moto.
     * Retorna una entre les tres possibilitats:
     * Disponible.
     * Reparant.
     * Reservada.
     * @return 
     */
    public String getEstat(){
        if(this.estatMoto instanceof EstatMotoDisponible){
            return "Disponible";
        }else if(this.estatMoto instanceof EstatMotoReparant){
            return "Reparant";
        }else{
            return "Reservada";   
        }
    }
    
    /**
     * Mètode cridat per a saber el id d'una Moto.
     * @return 
     */
    public String getIdMoto(){
        return idMoto;
    }
    
    /**
     * Mètode cridat per a modifcar l'estat d'una Moto.
     * L'Estat final serà un d'entre els tres possibles:
     * Reservada, Disponible o Reparant.
     * @param estat 
     */
    public void setEstat(String estat){
        estat = estat.toUpperCase();
        switch (estat) {
            case "RESERVADA":
                this.estatMoto = Estats.getEstatMotoReservada();
                break;
            case "DISPONIBLE":
                this.estatMoto = Estats.getEstatMotoDisponible();
                break;
            case "AVARIADA":
                this.estatMoto = Estats.getEstatMotoReparant();
                break;
        }
    }
    
    @Override
    /**
     * Mètode per a imprimir les dades referents a una Moto.
     */
    public String toString(){
        String str;
        str = "\nmoto amb ID: " + idMoto + "\n";
	str += "--------------------------------------\n";
	str += "Matrícula: " + matricula + "\n";
        str += "Model: " + model + "\n";
        str += "Color: " + color + "\n";
        str += "Estat: " + getEstat() + "\n";
        return str;
    }
    
}
