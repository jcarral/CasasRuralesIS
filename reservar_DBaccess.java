package dataAccess;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import businessLogic.OfferManager;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import domain.Offer;
import domain.RuralHouse;

public class reservar_DBaccess implements OfferManager {

	
	public static final String DB4OFILENAME = "RuralHouse.db4o"; 
	public static final int MAXNIVEL = 5;
	public static boolean inicializar = false;
	private static ObjectContainer db;
	
	public reservar_DBaccess(){
		EmbeddedConfiguration configuracion = Db4oEmbedded.newConfiguration();
		configuracion.common().activationDepth(MAXNIVEL);
		configuracion.common().updateDepth(MAXNIVEL);
		db = Db4oEmbedded.openFile(configuracion, DB4OFILENAME);
		
	}
	
	public static void close(){
		db.close();
	}
	
	public static void Book(Offer O){
		
		List<RuralHouse> R = db.queryByExample(O.getRuralHouse());
		RuralHouse RH = R.get(0);
		db.delete(RH);
		Collection<Offer> Offer = RH.getOffers(null, null);
		for (Offer offer2: Offer){
			if (offer2==O){
				offer2.reservar();
			}
		}
		RH.setOffers(Offer);
		db.store(RH);
		db.commit();
		System.out.println("Booked " + O);		
		return;
	}
	


	@Override
	public Collection getConcreteOffers(String city, Date date) {
		// TODO Auto-generated method stub
		return null;
	}
}