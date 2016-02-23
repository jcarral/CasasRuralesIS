package businessLogic;

import domain.Persona;
import domain.RuralHouse;

import java.util.List;
import java.util.Vector;

/**
 * Created by joseba on 22/2/16.
 */
public interface ruralManagerLogic {

    public int checkLogin(String mail, String pass, boolean tipo);

    public boolean storeUsuario(String mail, String password, String nombre, String apellido, String DNI, int numTel, boolean propietario);

    public void closeDB();

    String[] getUserInfo();

    boolean updatePersona(Persona p);

    Vector<RuralHouse> getAllRuralHouses();
}
