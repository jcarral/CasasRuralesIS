package businessLogic;

import domain.Offer;
import domain.Persona;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import exceptions.UsuarioNoExiste;
import exceptions.UsuarioRepetido;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by joseba on 22/2/16.
 */
public interface ruralManagerLogic {

    int checkLogin(String mail, String pass, boolean tipo) throws UsuarioNoExiste;
    void storeUsuario(String mail, String password, String nombre, String apellido, String DNI, int numTel, boolean propietario) throws UsuarioRepetido;
    String[] getUserInfo();
    void updatePersona(String mail, String password, String nombre, String apellido, String DNI, int numTel) throws UsuarioNoExiste;
    Vector<RuralHouse> getAllRuralHouses();
    List<RuralHouse> getUsersRuralHouses();
    void storeRH(String nombre, String city, String direccion, int numTel, String desc) throws UsuarioNoExiste;
    Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws BadDates, OverlappingOfferExists, UsuarioNoExiste;
}
