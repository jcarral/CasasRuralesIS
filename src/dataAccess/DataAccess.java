package dataAccess;


import java.util.Date;
import java.util.ListIterator;
import java.util.Vector;

import javax.jws.WebMethod;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import configuration.ConfigXML;
//import domain.Booking;
import domain.Offer;
import domain.RuralHouse;
import exceptions.OverlappingOfferExists;

public class DataAccess  {
	protected static ObjectContainer  db;

	private static DB4oManagerAux theDB4oManagerAux;
	private static EmbeddedConfiguration configuration;
	private static ClientConfiguration configurationCS;


	ConfigXML c;

	public DataAccess()  {
		
		c=ConfigXML.getInstance();
		
		if (c.isDatabaseLocal()) {
			configuration = Db4oEmbedded.newConfiguration();
			configuration.common().activationDepth(c.getActivationDepth());
			configuration.common().updateDepth(c.getUpdateDepth());
			db=Db4oEmbedded.openFile(configuration, c.getDb4oFilename());
		} else {
			configurationCS = Db4oClientServer.newClientConfiguration();
			configurationCS.common().activationDepth(c.getActivationDepth());
			configurationCS.common().updateDepth(c.getUpdateDepth());
			configurationCS.common().objectClass(RuralHouse.class).cascadeOnDelete(true);
			db = Db4oClientServer.openClient(configurationCS,c.getDatabaseNode(), 
				 c.getDatabasePort(),c.getUser(),c.getPassword());
				
		}
		System.out.println("Creating DB4oManager instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());
		}


	
	 class DB4oManagerAux {
		int offerNumber;
		DB4oManagerAux(int offerNumber){
			this.offerNumber=offerNumber;
		}
	}


	
	
	public void initializeDB(){
		
		System.out.println("Db initialized");
		RuralHouse rh1=new RuralHouse(1, "Ezkioko etxea","Ezkio");
		 RuralHouse rh2=new RuralHouse(2, "Etxetxikia","Iru�a");
		 RuralHouse rh3=new RuralHouse(3, "Udaletxea","Bilbo");
		 RuralHouse rh4=new RuralHouse(4, "Gaztetxea","Renteria");

		 db.store(rh1);
		 db.store(rh2);
		 db.store(rh3);
		 db.store(rh4);
		 
		 theDB4oManagerAux=new DB4oManagerAux(1);
		 
		 db.store(theDB4oManagerAux);

		 
		 db.commit();
	}
	
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) {

	try {
		
		RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(),null,null);
		ObjectSet<RuralHouse> result = db.queryByExample(proto);
		RuralHouse rh=result.next();
		
		ObjectSet<DB4oManagerAux> res =db.queryByExample(DB4oManagerAux.class);
		ListIterator<DB4oManagerAux> listIter = res.listIterator();
		if (listIter.hasNext()) theDB4oManagerAux =  res.next();    
		
		Offer o=rh.createOffer(theDB4oManagerAux.offerNumber++,firstDay, lastDay, price);
		//Offer o=rh.createOffer(1,firstDay, lastDay, price);

		db.store(theDB4oManagerAux); // To store the new value for offerNumber
		db.store(rh);
		db.commit(); 
		return o;
	}
	catch (com.db4o.ext.ObjectNotStorableException e){
		System.out.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer");
		return null;
	}
	}
	
	
	public Vector<RuralHouse> getAllRuralHouses() {


		 try {
			 RuralHouse proto = new RuralHouse(null,null,null);
			 ObjectSet<RuralHouse> result = db.queryByExample(proto);
			 Vector<RuralHouse> ruralHouses=new Vector<RuralHouse>();
			 while(result.hasNext()) 
				 ruralHouses.add(result.next());
			 return ruralHouses;
	     } finally {
	         //db.close();
	     }
	}
	 public Vector<Offer> getOffers( RuralHouse rh, Date firstDay,  Date lastDay) {
		 Vector<Offer> offers=new Vector<Offer>();
		 RuralHouse rhn = (RuralHouse) db.queryByExample(new RuralHouse(rh.getHouseNumber(),null,null)).next();	
		  offers=rhn.getOffers(firstDay, lastDay);
		  return offers;
	

	
	 }
	public boolean existsOverlappingOffer(RuralHouse rh,Date firstDay, Date lastDay) throws  OverlappingOfferExists{
		 try {

			RuralHouse rhn = (RuralHouse) db.queryByExample(new RuralHouse(rh.getHouseNumber(),null,null)).next();		
			if (rhn.overlapsWith(firstDay, lastDay)!=null) return true;
			else return false; 
	     } finally {
	         //db.close();
	     }
	}


	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
}
