package businessLogic;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;

//import domain.Booking;
import domain.Offer;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

//Service Implementation
@WebService(endpointInterface = "businessLogic.ApplicationFacadeInterfaceWS")
public class FacadeImplementationWS  implements ApplicationFacadeInterfaceWS {

	/**
	 * 
	 */

	//Vector<Owner> owners;
	//Vector<RuralHouse> ruralHouses;
	//DataAccessInterface dB4oManager;
 

	public FacadeImplementationWS()  {
		
		System.out.println("Executing FacadeImplementationWS");
		ConfigXML c=ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize") && (c.isDatabaseLocal())) {
			System.out.println("File deleted");
			new File(c.getDb4oFilename()).delete();
		}
		DataAccess dB4oManager=new DataAccess();
		if (c.getDataBaseOpenMode().equals("initialize")) {
			dB4oManager.initializeDB();
			}
		else {// check if it is opened
			 //Vector<Owner> owns=dataAccess.getOwners();
		}
		dB4oManager.close();
		

	}
	

	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return the created offer, or null, or an exception
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay,
			float price) throws OverlappingOfferExists, BadDates {
		System.out.println("Executing createOffer");
		System.out.println(firstDay);
		System.out.println(lastDay);

		
		DataAccess dB4oManager=new DataAccess();
		Offer o=null;
		if (firstDay.compareTo(lastDay)>=0) throw new BadDates();
		
		boolean b = dB4oManager.existsOverlappingOffer(ruralHouse,firstDay,lastDay); // The ruralHouse object in the client may not be updated
		if (!b) o=dB4oManager.createOffer(ruralHouse,firstDay,lastDay,price);		

		dB4oManager.close();
		
		return o;
	}
 

	
		
	public Vector<RuralHouse> getAllRuralHouses()  {
		System.out.println("Start: getAllRuralHouses");

		DataAccess dB4oManager=new DataAccess();

		Vector<RuralHouse>  ruralHouses=dB4oManager.getAllRuralHouses();
		dB4oManager.close();
		System.out.println("End: getAllRuralHouses");

		return ruralHouses;

	}
	
	/**
	 * This method obtains the  offers of a ruralHouse in the provided dates 
	 * 
	 * @param ruralHouse, the ruralHouse to inspect 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is no overlapping offer
	 */

	@WebMethod public Vector<Offer> getOffers( RuralHouse rh, Date firstDay,  Date lastDay) {
		
		DataAccess dB4oManager=new DataAccess();
		Vector<Offer>  offers=new Vector<Offer>();
		  offers=dB4oManager.getOffers(rh,firstDay,lastDay);
		dB4oManager.close();

		return offers;
	}	
		
		
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}


	 public void initializeBD(){
		
			DataAccess dB4oManager=new DataAccess();
		dB4oManager.initializeDB();
		dB4oManager.close();

	}



	 

	}

