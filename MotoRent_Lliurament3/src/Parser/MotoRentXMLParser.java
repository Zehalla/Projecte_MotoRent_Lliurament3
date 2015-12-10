package Parser;

import java.io.File;
import java.lang.String;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Parser per a obtenir dades d'un fitxer XML d'MotoRent
 * 
 * @author Professors disseny de software
 */
public class MotoRentXMLParser {

	/**
	 * Data manager
	 */
	MotoRentDataManager dataManager;

	/**
	 * Constructor
	 */
	public MotoRentXMLParser(MotoRentDataManager dataManager) {
		this.dataManager = dataManager;
	}
	
	/**
	 * Parseja un fitxer XML de MotoRent i guarda les dades al sistema
	 * 
	 * @param nomFitxer
	 */
	public void parse(String nomFitxer) {
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File(nomFitxer));
			doc.getDocumentElement().normalize();

			// Obtenim dades
			this.obtenirLocals(doc);
			this.obtenirReserves(doc);
			this.obtenirAdministradors(doc);
			this.obtenirGestors(doc);
			this.obtenirClients(doc);
		}
		catch (SAXParseException err) {
			System.out.println ("** Error parsejant" +", linia " + err.getLineNumber () + ", uri " + err.getSystemId ());
			System.out.println(" " + err.getMessage ());
		}
		catch (SAXException e) {
			Exception x = e.getException ();
			((x == null) ? e : x).printStackTrace (); 
		}
		catch (Throwable t) {
			t.printStackTrace ();
		}
	}

	/**
	 * Obte els locals de MotoRent
	 * 
	 * @param doc document XML del que obtenir les dades
	 */
	private void obtenirLocals(Document doc) {
		NodeList locals = doc.getElementsByTagName("local");
		String id, mida, nomLocal, adreca;
		int numLocals = locals.getLength();

		// Parsejo tots els elements locals
		for(int i=0; i<numLocals; i++) {
			Node local = locals.item(i);

			if(local.getNodeType() == Node.ELEMENT_NODE){
				Element eLocal = (Element)local;
				id = local.getAttributes().getNamedItem("id").getTextContent();
				mida = local.getAttributes().getNamedItem("capacitat").getTextContent();
				
				NodeList nNomLocal = eLocal.getElementsByTagName("gestorID");
				Element eNomLocal = (Element)nNomLocal.item(0);
				nomLocal = eNomLocal.getTextContent();
				
				NodeList nAdreca = eLocal.getElementsByTagName("adreca");
				Element eAdreca = (Element)nAdreca.item(0);
				adreca = eAdreca.getTextContent();

                                
				// Creem una nova local amb la informacio obtinguda
				dataManager.crearLocal(id, mida, nomLocal, this.obtenirDireccio(adreca));
				
				// Obtenim informacio de les bicicletes
				this.obtenirMotos(eLocal);
			}
		}
	}
        
	/**
	 * Obte informacio sobre les bicicletes d'una estacio
	 * 
	 * @param eLocal estacio del que buscar les biciletes
	 */
	private void obtenirMotos(Element eLocal) {
		NodeList motos = eLocal.getElementsByTagName("moto");
		String id, matricula, marca, model, color, estat;
		int numMotos = motos.getLength();

		// Parsejo tots els elements bici
		for(int i=0; i<numMotos; i++) {
			Node moto = motos.item(i);

			if(moto.getNodeType() == Node.ELEMENT_NODE){
				id = moto.getAttributes().getNamedItem("id").getTextContent();
				matricula = moto.getAttributes().getNamedItem("matricula").getTextContent();
                                marca = moto.getAttributes().getNamedItem("marca").getTextContent();
                                model = moto.getAttributes().getNamedItem("model").getTextContent();
                                color = moto.getAttributes().getNamedItem("color").getTextContent();
				estat = moto.getAttributes().getNamedItem("estat").getTextContent();
				
				dataManager.crearMoto(id, matricula, marca, model, color, estat);
			}			
		}
	}
	
	/**
	 * Obte informacio sobre els reserves
	 * 
	 * @param doc fitxer XML del que obtenir les dades
	 */
	
	private void obtenirReserves(Document doc) {
		NodeList reserves = doc.getElementsByTagName("reserva");
		String id, client, moto, cost, falta, local_inici, local_fi, hora_inici, hora_fi, fecha_inici, fecha_fi;
		int numTrajectes = reserves.getLength();

		// Parsejo tots els elements trajecte
		for(int i=0; i<numTrajectes; i++) {
			Node reserva = reserves.item(i);

			if(reserva.getNodeType() == Node.ELEMENT_NODE){
				Element eReserva = (Element)reserva;
				id = reserva.getAttributes().getNamedItem("id").getTextContent();
				client = reserva.getAttributes().getNamedItem("client").getTextContent();
				moto = reserva.getAttributes().getNamedItem("moto").getTextContent();
                                cost = reserva.getAttributes().getNamedItem("cost").getTextContent(); 
                                falta = reserva.getAttributes().getNamedItem("falta").getTextContent(); 
				
				NodeList nInici = eReserva.getElementsByTagName("inici");
				hora_inici = nInici.item(0).getAttributes().getNamedItem("hora").getTextContent();
				local_inici = nInici.item(0).getAttributes().getNamedItem("local").getTextContent();
				fecha_inici = nInici.item(0).getAttributes().getNamedItem("data").getTextContent();
				
				NodeList nFi = eReserva.getElementsByTagName("fi");
				hora_fi = nFi.item(0).getAttributes().getNamedItem("hora").getTextContent();
				local_fi = nFi.item(0).getAttributes().getNamedItem("local").getTextContent();
				fecha_fi = nFi.item(0).getAttributes().getNamedItem("data").getTextContent();
				
				dataManager.crearReserva(id,client,moto,cost, falta, local_inici,hora_inici,fecha_inici, local_fi,hora_fi, fecha_fi);
			}			
		}
	}
	
	/**
	 * Obte informacio sobre els administradors 
	 * 
	 * @param doc fitxer XML del que obtenir les dades
	 */
	private void obtenirAdministradors(Document doc) {
		NodeList admins = doc.getElementsByTagName("admin");
		String id, nom, usuari, password;
		int numAdmins = admins.getLength();

		// Parsejo tots els elements admin
		for(int i=0; i<numAdmins; i++) {
			Node admin = admins.item(i);

			if(admin.getNodeType() == Node.ELEMENT_NODE){
				id = admin.getAttributes().getNamedItem("id").getTextContent();
				Element eAdmin = (Element)admin;

				NodeList nNom = eAdmin.getElementsByTagName("nom");
				Element eNom = (Element)nNom.item(0);
				nom = eNom.getTextContent();

				NodeList nUsuari = eAdmin.getElementsByTagName("usuari");
				Element eUsuari = (Element)nUsuari.item(0);
				usuari = eUsuari.getTextContent();

				NodeList nPassword = eAdmin.getElementsByTagName("password");
				Element ePassword = (Element)nPassword.item(0);
				password = ePassword.getTextContent();

				// Creem l'admin
				dataManager.crearAdmin(id, this.obtenirNom(nom), usuari, password);
			}
		}
	}
	
        
	/**
	 * Obte informacio sobre els gestors dels locals 
	 * 
	 * @param doc fitxer XML del que obtenir les dades
	 */
	private void obtenirGestors(Document doc) {
		NodeList gestors = doc.getElementsByTagName("gestor");
		String id, nom, usuari, password;
		int numGestors = gestors.getLength();

		// Parsejo tots els elements admin
		for(int i=0; i<numGestors; i++) {
			Node gestor = gestors.item(i);

			if(gestor.getNodeType() == Node.ELEMENT_NODE){
				id = gestor.getAttributes().getNamedItem("id").getTextContent();
				Element eGestor = (Element)gestor;

				NodeList nNom = eGestor.getElementsByTagName("nom");
				Element eNom = (Element)nNom.item(0);
				nom = eNom.getTextContent();

				NodeList nUsuari = eGestor.getElementsByTagName("usuari");
				Element eUsuari = (Element)nUsuari.item(0);
				usuari = eUsuari.getTextContent();

				NodeList nPassword = eGestor.getElementsByTagName("password");
				Element ePassword = (Element)nPassword.item(0);
				password = ePassword.getTextContent();

				// Creem l'admin
				dataManager.crearGestor(id, this.obtenirNom(nom) , usuari, password);
			}
		}
	}

	/**
	 * Obte informacio sobre els clients
	 * 
	 * @param doc fitxer XML del que obtenir les dades
	 */
	private void obtenirClients(Document doc) {
		NodeList clients = doc.getElementsByTagName("client");
		String id, nom, usuari, password, vip, renovacio, faltes, dni, adreca;
		int numAdmins = clients.getLength();

		// Parsejo tots els elements client
		for(int i=0; i<numAdmins; i++) {
			Node client = clients.item(i);

			if(client.getNodeType() == Node.ELEMENT_NODE){
				id = client.getAttributes().getNamedItem("id").getTextContent();
				vip = client.getAttributes().getNamedItem("vip").getTextContent();
				renovacio = client.getAttributes().getNamedItem("renovacio").getTextContent();
				faltes = client.getAttributes().getNamedItem("faltes").getTextContent();
				Element eClient = (Element)client;

				NodeList nNom = eClient.getElementsByTagName("nom");
				Element eNom = (Element)nNom.item(0);
				nom = eNom.getTextContent();

				NodeList nDni = eClient.getElementsByTagName("dni");
				Element eDni = (Element)nDni.item(0);
				dni = eDni.getTextContent();
				
				NodeList nAdreca = eClient.getElementsByTagName("adreca");
				Element eAdreca = (Element)nAdreca.item(0);
				adreca = eAdreca.getTextContent();
				
				NodeList nUsuari = eClient.getElementsByTagName("usuari");
				Element eUsuari = (Element)nUsuari.item(0);
				usuari = eUsuari.getTextContent();

				NodeList nPassword = eClient.getElementsByTagName("password");
				Element ePassword = (Element)nPassword.item(0);
				password = ePassword.getTextContent();
                               
				// Creem el client
				dataManager.crearClient(id, this.obtenirNom(nom), dni, this.obtenirDireccio(adreca), usuari, password, vip, renovacio, faltes);
			}
		}
	}
        
        private String[] obtenirNom(String nomUsuari){
            return nomUsuari.split(" ");
        }
        
        private String[] obtenirDireccio(String direccio){
            String[] retorn = new String[4];
            String[] adreca = direccio.split(",");
            String[] num = adreca[0].split(" ");
            retorn[1] = num[num.length-1].trim();
            retorn[0] = adreca[0].substring(0,adreca[0].length()-retorn[1].length()).trim();
            retorn[2] = adreca[1].trim();
            retorn[3] = adreca[2].trim();
            return retorn;
        }
}
