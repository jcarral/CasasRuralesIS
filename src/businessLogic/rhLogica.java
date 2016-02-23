package businessLogic;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import domain.Persona;
import domain.Propietario;
import domain.RuralHouse;
import domain.Usuario;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by joseba on 22/2/16.
 */
public class rhLogica implements ruralManagerLogic {

    public static final String DB4OFILENAME = "rhs.db4o";
    public static final int MAXNIVEL = 5;
    private ObjectContainer db;

    private Persona actualUser;

    private final boolean USUARIO = true, PROPIETARIO = false;

    public rhLogica() {
        openDB();
    }

    private void openDB() {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().activationDepth(MAXNIVEL);
        configuration.common().updateDepth(MAXNIVEL);
        db = Db4oEmbedded.openFile(configuration, DB4OFILENAME);
    }


    @Override
    public int checkLogin(String mail, String pass, boolean usuario) {

        Persona p;
        int val;

        if (usuario) {
            p = new Usuario(mail, pass, null, null, null, 0);
            val = 0;
        } else {
            p = new Propietario(mail, pass, null, null, null, 0);
            val = 1;
        }

        List<Persona> res = db.queryByExample(p);

        if (!res.isEmpty()) {
            actualUser = res.get(0);

        } else {
            val = -1;
        }
        return val;
    }

    @Override
    public boolean storeUsuario(String mail, String password, String nombre, String apellido, String DNI, int numTel, boolean propietario) {
        Persona p;

        if (propietario)
            p = new Propietario(mail, password, nombre, apellido, DNI, numTel);
        else
            p = new Usuario(mail, password, nombre, apellido, DNI, numTel);

        return insertPersona(p);


    }

    @Override
    public void closeDB() {
        db.close();
    }

    @Override
    public String[] getUserInfo() {
        String[] info = new String[5];

        info[0] = actualUser.getMail();
        info[1] = actualUser.getNombre();
        info[2] = actualUser.getApellido();
        info[3] = actualUser.getDNI();
        info[4] = Integer.toString(actualUser.getNumTel());


        return info;
    }

    @Override
    public boolean updatePersona(Persona p) {
        actualUser.clone(p);
        return insertPersona(actualUser);

    }

    @Override
    public Vector<RuralHouse> getAllRuralHouses() {
        List<RuralHouse> res = db.queryByExample(new RuralHouse(0, null, null));
        return new Vector<RuralHouse>(res);
    }

    private boolean insertPersona(Persona p) {

        try {
            db.store(p);
            db.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
