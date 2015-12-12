package Parser;

import Controlador.MotoRent;
import Model.Data;
import Model.Direccio;
import Model.Estats.*;

/**
 * Data manager per MotoRent
 * . Crea les estructures de dades necessàries
 * per a manegar l'aplicació de gestió d'MotoRent
 * .
 * @author Professors disseny de software
 */
public class MotoRentDataManager {
        MotoRent controlador;
	/* -------------------------------------------------------------------
	 * Metodes a implementar per vosaltres. En aquests metodes creareu els
	 * vostres objectes a partir de la informacio obtinguda del fitxer XML
	 * 
	 * Podeu esborrar els prints si voleu. Tambe podeu canviar el tipus de
	 * dades que retorna el metode, es a dir que sou lliures de
	 * modificar-ho al gust, excepte les crides inicials que es fan.
	 * -------------------------------------------------------------------
	 */
	
        public MotoRentDataManager(MotoRent controlador){
            this.controlador = controlador;
        }
	/**
	 * Obté les dades del fitxer XML passat per paràmetre
	 * 
	 * @param nomFitxer ruta del fitxer XML del que obtenir les dades
	 */
	public void obtenirDades(String nomFitxer) {
		MotoRentXMLParser parser = new MotoRentXMLParser(this);
		parser.parse(nomFitxer);
	}

	/**
	 * Crea a partir de la informacio obtinguda del fitxer XML
	 * 
	 * @param id id de l'local. El podeu utilitzar o no
	 * @param capacitat quantita màxima de motos
	 * @param gestorID gestorID del local
	 * @param adreca adreça del local
	 */
	
	public void crearLocal(String id, String capacitat, String gestorID, String[] adreca) {
            controlador.guardarLocal(id, Integer.parseInt(capacitat), crearDireccio(adreca), gestorID);
	}

	/**
	 * Crea una nova moto a partir de la informacio obtinguda del fitxer XML
	 * 
	 * @param id id de la moto. El podeu utilitzar o no
         * @param matricula matrícula de la moto
         * @param marca marca de la moto
         * @param model model de la moto
	 * @param color color de la moto
	 * @param estat estat de la moto: "true" representa moto activa i "false" per reparar
	 */

	public void crearMoto(String id, String matricula, String marca, String model, String color, String estat) {
            EstatMoto estatMoto;
            switch (estat) {
                case "disponible":
                    estatMoto = Estats.getEstatMotoDisponible();
                    break;
                case "avariada":
                    estatMoto = Estats.getEstatMotoReparant();
                    break;
                default:
                    estatMoto = Estats.getEstatMotoReservada();
                    break;
            }
            controlador.guardarMoto(id, matricula, model, color, estatMoto);
	

		
	}

	/**
	 * Crea un trajecte a partir de la informacio obtinguda del fitxer XML
	 * 
	 * @param id id del trajecte. El podeu utilitzar o no
	 * @param client identificador del client. El podeu utilitzar o no
	 * @param moto identificador de la moto. El podeu utilitzar o no
         * @param cost
         * @param falta
	 * @param local_inici identificador del local d'inici
	 * @param hora_inici hora d'inici de trajecte
         * @param fecha_inici
	 * @param local_fi identificador del local final de trajecte
	 * @param hora_fi hora de finalització de trajecte
         * @param fecha_fi
	 */
	
	public void crearReserva(String id,String client,String moto, String cost, String falta, String local_inici,String hora_inici,String fecha_inici, String local_fi,String hora_fi,String fecha_fi) {
            controlador.guardarReserva(id, client, moto, cost, falta, local_inici, local_fi, crearData(fecha_inici, hora_inici), crearData(fecha_fi, hora_fi)); 
	}

	/**
	 * Crea un nou admin a partir de la informacio obtinguda del fitxer XML
	 * 
	 * @param id id del administrador
	 * @param nom nom del administrador
	 * @param usuari usuari del administrador
	 * @param password password del administrador
	 */
	public void crearAdmin(String id, String[] nom, String usuari, String password) {
            controlador.guardarAdministrador(nom[0], nom[1], "", usuari, password, id);
	}
	
	/**
	 * Crea un nou gestor a partir de la informacio obtinguda del fitxer XML
	 * 
	 * @param id id del gestor
	 * @param nom nom del gestor
	 * @param usuari usuari del gestor
	 * @param password password del gestor
	 */
	public void crearGestor(String id, String[] nom, String usuari, String password) {
            controlador.guardarGerent(nom[0], nom[1], "", usuari, password, id);
	}

	/**
	 * Crea un nou client a partir de la informacio obtinguda del fitxer XML
	 * 
	 * @param id id del client
	 * @param nom nom del client
	 * @param dni dni del client
	 * @param adreca adreça del client
	 * @param usuari usuari al sistema del client
	 * @param password password del client
	 * @param vip true si el client es vip. false si no
	 * @param renovacio true si el client renova automaticament. false si no
	 * @param faltes nombre de faltes
	 */

	public void crearClient(String id, String[] nom, String dni, String[] adreca, String usuari, String password, String vip, String renovacio, String faltes) {

            controlador.guardarClient(id, nom[0], nom[1], "", dni, usuari, password, esVip(vip), Integer.parseInt(faltes), crearDireccio(adreca), new Data(), Estats.getEstatClientSenseReserva());
	}
        
        
        // METODES AUXILIARS
        private Direccio crearDireccio(String[] adreca){
            return new Direccio(adreca[0], adreca[1], adreca[2], adreca[3]);
        }
        
        private boolean esVip(String vip){
            return "true".equals(vip);
        }
        
        private Data crearData(String diaComplet, String horaCompleta){
            String[] aux = diaComplet.split("/");
            String[] aux2 = horaCompleta.split(":");
            return new Data(aux[2], aux[1], aux[0], aux2[0], aux2[1], aux2[2]);
        }
}
