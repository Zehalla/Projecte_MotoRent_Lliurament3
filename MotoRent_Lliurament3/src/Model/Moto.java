package Model;

import Model.Estats.*;

/**
 *
 * @author atorraag7.alumnes
 */
public class Moto {
    private String idMoto;
    private String matricula;
    private String model;
    private String color;
    private EstatMoto estatMoto;

    public Moto(){
    }
    
    public Moto(String idMoto, String matricula, String model, String color, String estat){
        this.idMoto = idMoto;
        this.matricula = matricula;
        this.model = model;
        this.color = color;
        this.setEstat(estat);
    }

    public String getEstat(){
        if(this.estatMoto instanceof EstatMotoDisponible){
            return "Disponible";
        }else if(this.estatMoto instanceof EstatMotoReparant){
            return "Reparant";
        }else{
            return "Reservada";   
        }
    }
    
    
    public String getIdMoto(){
        return idMoto;
    }
    
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
