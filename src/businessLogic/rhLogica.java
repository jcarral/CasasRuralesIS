package businessLogic;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import domain.Persona;
import domain.Propietario;
import domain.Usuario;

/**
 * Created by joseba on 22/2/16.
 */
public class rhLogica implements ruralManagerLogic {

    public static final String DB4OFILENAME = "rhs.db4o";
    public static final int MAXNIVEL = 5;
    private ObjectContainer db;

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
    public int checkLogin(String mail, String pass, boolean tipo) {
        return 0;
    }

    @Override
    public boolean storeUsuario(String mail, String password, String nombre, String apellido, String DNI, int numTel, boolean propietario) {
        Persona p;

        if (propietario)
            p = new Propietario(mail, password, nombre, apellido, DNI, numTel);
        else
            p = new Usuario(mail, password, nombre, apellido, DNI, numTel);

        try {
            db.store(p);
            db.commit();
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    @Override
    public void closeDB() {
        db.close();
    }
}
