package Model.Estats;

/**
 * Factoria d'objectes Estat
 * @author aleix
 */
public class Estats {
    /**
     * Retorna la instància de l'objecte EstatClientAmbReserva
     * @return EstatClientAmbReserva
     */
    public static EstatClientAmbReserva getEstatClientAmbReserva() {
        return EstatClientAmbReserva.getInstance();
    }
    /**
     * Retorna la instància de l'objecte EstatClientDesactivat
     * @return EstatClientDesactivat
     */
    public static EstatClientDesactivat getEstatClientDesactivat() {
        return EstatClientDesactivat.getInstance();
    }
    /**
     * Retorna la instància de l'objecte EstatClientSenseReserva
     * @return EstatClientSenseReserva
     */
    public static EstatClientSenseReserva getEstatClientSenseReserva() {
        return EstatClientSenseReserva.getInstance();
    }
    /**
     * Retorna la instància de l'objecte EstatMotoDisponible
     * @return EstatMotoDisponible
     */
    public static EstatMotoDisponible getEstatMotoDisponible() {
        return EstatMotoDisponible.getInstance();
    }
    /**
     * Retorna la instància de l'objecte EstatMotoReparant
     * @return EstatMotoReparant
     */
    public static EstatMotoReparant getEstatMotoReparant() {
        return EstatMotoReparant.getInstance();
    }
    /**
     * Retorna la instància de l'objecte EstatMotoReservada
     * @return EstatMotoReservada
     */
    public static EstatMotoReservada getEstatMotoReservada() {
        return EstatMotoReservada.getInstance();
    }

    
}
