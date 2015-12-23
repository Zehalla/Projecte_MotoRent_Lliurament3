/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Oriol
 */
public class Data{
    private String any, mes, dia, hora, minut, segon;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

    
    // Constructor que inicialitza un Objecte Data al dia actual.
    public Data(){
        GregorianCalendar day = new GregorianCalendar();
        this.any = Integer.toString(day.get(Calendar.YEAR));
        this.mes = Integer.toString(day.get(Calendar.MONTH));
        this.dia = Integer.toString(day.get(Calendar.DAY_OF_MONTH));
        this.hora = Integer.toString(day.get(Calendar.HOUR_OF_DAY));
        this.minut = Integer.toString(day.get(Calendar.MINUTE));
        this.segon = Integer.toString(day.get(Calendar.SECOND));
        
    }
   
    public Data(String any, String mes, String dia, String hora, String minut, String segon){
        this.any = any;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minut = minut;
        this.segon = segon;
    } 
    
    public Data(String dataS){
        String[] sliced = dataS.split("/");
        this.any = sliced[3];
        this.mes = sliced[2];
        this.dia = sliced[1];
        this.hora = sliced[0];
    } 
    
    
    
    private Data dateToData(Date dt){
        String format = df.format(dt);
        String[] sliced = format.split("-");
        Data data = new Data();
        data.setAny(sliced[0]);
        data.setMes(sliced[1]);
        data.setDia(sliced[2]);
        data.setHora(sliced[3]);
        data.setMinut(sliced[4]);
        data.setSegon(sliced[5]);
        return data;
    }
    
    public Date dataToDate() throws ParseException{
        String format = this.any+"-"+this.mes+"-"+this.dia+"-"+this.hora+"-"+this.minut+"-"+this.segon;
        Date date;
        date = df.parse(format);
        return date;
    }

     @Override
    public String toString(){
        return dia+"/"+mes+"/"+any+" - "+hora+":"+minut+":"+segon;
        
    }
    
    private void setAny(String any) {
        this.any = any;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }     

    public void setMinut(String minut) {
        this.minut = minut;
    }

    public void setSegon(String segon) {
        this.segon = segon;
    }

    public String getAny() {
        return any;
    }

    public String getMes() {
        return mes;
    }

    public String getDia() {
        return dia;
    }

    public String getHora() {
        return hora;
    }

    public String getMinut() {
        return minut;
    }

    public String getSegon() {
        return segon;
    }

    public int compara(Data cmp) {

        int anyComp = Integer.parseInt(cmp.getAny());
        int mesComp = Integer.parseInt(cmp.getMes());
        int diaComp = Integer.parseInt(cmp.getDia());
        int horaComp = Integer.parseInt(cmp.getHora());

        int anyInt = Integer.parseInt(any);
        int mesInt = Integer.parseInt(mes);
        int diaInt = Integer.parseInt(dia);
        int horaInt = Integer.parseInt(hora);
        
        
        int dataInt = horaInt + 100*(diaInt + 100*(mesInt   + 100*anyInt));  
        int dataComp = horaComp + 100*(diaComp + 100*(mesComp   + 100*anyComp));
        
        if (dataInt > dataComp){ //La data que passem per parametre es mes petita, la nostra es mes gran
            return 1;
        }else if (dataInt < dataComp){ //La data que passem per parametre es mes gran, la nostra es mes petita
            return -1;
        }else if(dataInt == dataComp){ //iguals
            return 0;
        }else{
            return -2; //Error inesperat
        }
    }
        
    public int calcularDiferencia(Data dat) {    
        int dif;
        dif = (Integer.parseInt(this.any)-Integer.parseInt(dat.getAny()))*12;
        dif = (dif + Integer.parseInt(this.mes) - Integer.parseInt(dat.getMes()))*30;
        dif = (dif + Integer.parseInt(this.dia) - Integer.parseInt(dat.getDia()))*24;
        dif = (dif + Integer.parseInt(this.hora) - Integer.parseInt(dat.getHora()));
        return dif;
    }   
    

}
