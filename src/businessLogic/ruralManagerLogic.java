package businessLogic;

import domain.Offer;
import domain.Persona;
import domain.RuralHouse;
import exceptions.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by joseba on 22/2/16.
 */
public interface ruralManagerLogic {

    int checkLogin(String mail, String pass) throws UsuarioNoExiste;
    void storeUsuario(String mail, String password, String nombre, String apellido, String DNI, int numTel, boolean propietario) throws UsuarioRepetido;
    String[] getUserInfo();
    void updatePersona(String mail, String password, String nombre, String apellido, String DNI, int numTel) throws UsuarioNoExiste;
    Vector<RuralHouse> getAllRuralHouses();
    List<RuralHouse> getUsersRuralHouses();
    void storeRH(String nombre, String city, String direccion, int numTel, String desc, int habitaciones, int banios) throws UsuarioNoExiste;
    Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws BadDates, OverlappingOfferExists, UsuarioNoExiste, OfertaRepetida;
    List<RuralHouse> searchUsingFilter(String nombre, String ciudad, String direccion, int min, int max, int habitaciones, int banios);
    void confirmarReserva(Offer of) throws UsuarioNoExiste, OfertaNoExiste;
    Vector<Vector<String>> reservedRHInfo();
}
