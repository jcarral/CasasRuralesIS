package businessLogic;

import dataAccess.reservar_DBaccess;
import domain.Offer;

public class MyReservaManager implements ReservaManagerLogic {

	public MyReservaManager(){
		
	}

	@Override
	public void storeReserva(Offer O) {
		reservar_DBaccess.Book(O);
		
	}
}
