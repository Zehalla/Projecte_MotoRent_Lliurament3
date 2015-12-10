/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Oriol
 */
public class Data{
    private String any, mes, dia, hora, minut, segon;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

    
    //Constructor a partir de un string del estil hh-dd-mm-aaaa. (Fer Reserva).
    public Data(){
        
    }
    public Data(String sData){
        String[] sliced = sData.split("-");

        setAny(sliced[0]);
        setMes(sliced[1]);
        setDia(sliced[2]);
        setHora(sliced[3]);
    }
    
    public Data(int any,int mes,int dia,int hora){
        this.any = Integer.toString(any);
        if(mes>=0 && mes<=12){
            this.mes = Integer.toString(mes);   
        }
        if(dia>=0 && dia<=31){
            this.dia = Integer.toString(dia);
        }
        if(hora>=0 && hora<=24){
            this.hora = Integer.toString(hora);
        }
    }
    
    
    public int compara(Data dataFinal) {
        int anyComp;
        int mesComp;
        int diaComp;
        int horaComp;
        
        anyComp = Integer.parseInt(dataFinal.getAny());
        mesComp = Integer.parseInt(dataFinal.getMes());
        diaComp = Integer.parseInt(dataFinal.getDia());
        horaComp = Integer.parseInt(dataFinal.getHora());
        
        if((anyComp<Integer.parseInt(any)) || (anyComp==Integer.parseInt(any) && mesComp<Integer.parseInt(mes)) || (anyComp==Integer.parseInt(any) && mesComp==Integer.parseInt(mes)) && diaComp<Integer.parseInt(dia) || (anyComp==Integer.parseInt(any) && mesComp==Integer.parseInt(mes) && diaComp==Integer.parseInt(dia) && horaComp<Integer.parseInt(hora))){
            return 1;
        }else if((anyComp>Integer.parseInt(any)) || (anyComp==Integer.parseInt(any) && mesComp>Integer.parseInt(mes)) || (anyComp==Integer.parseInt(any) && mesComp==Integer.parseInt(mes) && diaComp>Integer.parseInt(dia)) || (anyComp==Integer.parseInt(any) && mesComp==Integer.parseInt(mes) && diaComp==Integer.parseInt(dia) && horaComp>Integer.parseInt(hora))){
            return -1;
        }
        return 0;    //Si no es cap dels dos o es igual o es un error.    
    }
    

    public Data(String any, String mes, String dia, String hora, String minut, String segon){
        this.any = any;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minut = minut;
        this.segon = segon;
    }  
    public void dateToData(Date dt){
        String format = df.format(dt);
        String[] sliced = format.split("-");
        
        setAny(sliced[0]);
        setMes(sliced[1]);
        setDia(sliced[2]);
        setHora(sliced[3]);
        setMinut(sliced[4]);
        setSegon(sliced[5]);
    }
    public Date dataToDate() throws ParseException{
        String format = this.any+"-"+this.mes+"-"+this.dia+"-"+this.hora+"-"+this.minut+"-"+this.segon;
        Date date;
        date = df.parse(format);
        return date;
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

    public SimpleDateFormat getDf() {
        return df;
    }
     @Override
    public String toString(){
        return dia+"/"+mes+"/"+any+" - "+hora+":"+minut+":"+segon;
        
    }
}
