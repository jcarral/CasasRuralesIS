package businessLogic;

import domain.Offer;
import domain.Persona;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by joseba on 22/2/16.
 */
public interface ruralManagerLogic {

    int checkLogin(String mail, String pass, boolean tipo);
    boolean storeUsuario(String mail, String password, String nombre, String apellido, String DNI, int numTel, boolean propietario);
    void closeDB();
    String[] getUserInfo();
    boolean updatePersona(Persona p);
    Vector<RuralHouse> getAllRuralHouses();
    List<RuralHouse> getUsersRuralHouses();
    boolean storeRH(String nombre, String city, String direccion, int numTel, String desc);
    Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws BadDates, OverlappingOfferExists;
}
