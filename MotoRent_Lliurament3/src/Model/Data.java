package Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe que conté la nostra especificació de Data segons el problema.
 */
public class Data{
    private String any, mes, dia, hora, minut, segon;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

    
    /**
     * Constructor de la classe Data.
     * Aquest constructor inicialitza una data al dia i hora actuals.
     */
    public Data(){
        GregorianCalendar day = new GregorianCalendar();
        this.any = Integer.toString(day.get(Calendar.YEAR));
        this.mes = Integer.toString(day.get(Calendar.MONTH));
        this.dia = Integer.toString(day.get(Calendar.DAY_OF_MONTH));
        this.hora = Integer.toString(day.get(Calendar.HOUR_OF_DAY));
        this.minut = Integer.toString(day.get(Calendar.MINUTE));
        this.segon = Integer.toString(day.get(Calendar.SECOND));
        
    }
   
    /**
     * Constructor de la classe Data.
     * Aquest constructor inicialitza una data al dia i hora especificats.
     * @param any
     * @param mes
     * @param dia
     * @param hora
     * @param minut
     * @param segon 
     */
    public Data(String any, String mes, String dia, String hora, String minut, String segon){
        this.any = any;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minut = minut;
        this.segon = segon;
    }

     @Override
     /**
      * Mètode per a imprimir un objecte de tipus Data.
      */
    public String toString(){
        return dia+"/"+mes+"/"+any+" - "+hora+":"+minut+":"+segon;
        
    }

    /**
     * Mètode que retorna l'any d'una Data donada.
     * @return 
     */
    public String getAny() {
        return any;
    }

    /**
     * Mètode que retorna el mes d'una Data donada.
     * @return 
     */
    public String getMes() {
        return mes;
    }

    /**
     * Mètode que retorna el dia d'una Data donada.
     * @return 
     */
    public String getDia() {
        return dia;
    }

    /**
     * Mètode que retorna la hora d'una Data donada.
     * @return 
     */
    public String getHora() {
        return hora;
    }

    /**
     * Mètode que retorna el minut d'una Data donada.
     * @return 
     */
    public String getMinut() {
        return minut;
    }

    /**
     * Mètode que retorna el segon d'una Data donada.
     * @return 
     */
    public String getSegon() {
        return segon;
    }

    /**
     * Mètode que compara dues dates donades.
     * Retorna 1 si la data passada per paràmetre és més petita que la cridada.
     * Retorna -1 si la data passada per paràmetre és més gran que la cridada.
     * Retorna 0 si són iguals.
     * Retorna -2 en cas d'error inesperat.
     * @param cmp
     * @return 
     */
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
        
    /**
     * Mètode que calcula la diferencia, en dies, de dues Dates donades.
     * @param dat
     * @return 
     */
    public int calcularDiferencia(Data dat) {    
        int dif;
        dif = (Integer.parseInt(this.any)-Integer.parseInt(dat.getAny()))*12;
        dif = (dif + Integer.parseInt(this.mes) - Integer.parseInt(dat.getMes()))*30;
        dif = (dif + Integer.parseInt(this.dia) - Integer.parseInt(dat.getDia()))*24;
        dif = (dif + Integer.parseInt(this.hora) - Integer.parseInt(dat.getHora()));
        return dif;
    }  
    
    /**
     * Mètode Static que retorna un objecte Data corresponent al dia i hora 
     * passats com a paràmetre.
     * @param diaComplet
     * @param horaCompleta
     * @return 
     */
    public static Data crearData(String diaComplet, String horaCompleta){
            String[] aux = diaComplet.split("/");
            String[] aux2 = horaCompleta.split(":");
            return new Data(aux[2], aux[1], aux[0], aux2[0], aux2[1], aux2[2]);
    }
    

}
