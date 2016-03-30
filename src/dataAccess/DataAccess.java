package dataAccess;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import domain.*;
import exceptions.UsuarioNoExiste;

import java.util.List;
import java.util.Vector;


public class DataAccess
{
    public static final String DB4OFILENAME = "rhs.db4o";
    public static final int MAXNIVEL = 5;
    private ObjectContainer db;


    private void openDB() {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().activationDepth(MAXNIVEL);
        configuration.common().updateDepth(MAXNIVEL);
        db = Db4oEmbedded.openFile(configuration, DB4OFILENAME);
    }

    public void closeDb(){
        db.close();
    }

    public Persona validUser(Persona p) throws UsuarioNoExiste{
        Persona actualUser;
        openDB();
        List<Persona> res = db.queryByExample(p);

        if (!res.isEmpty()) {
            actualUser = res.get(0);

        } else {
            db.close();
            throw new UsuarioNoExiste();
        }

        return actualUser;
    }

    public boolean existsUser(Propietario p, Cliente c){
        List<Persona> props, users;
        openDB();

        props = db.queryByExample(p);
        users = db.queryByExample(c);

        boolean exists = !(props.isEmpty() && users.isEmpty());
        closeDb();
        return exists;
    }

    public void insertUser(Persona p){
        openDB();
        db.store(p);
        db.commit();
        closeDb();
    }

    public Vector<RuralHouse> getRuralHousesBy(RuralHouse rh){
        openDB();
        List<RuralHouse> res = db.queryByExample(rh);
        Vector<RuralHouse> v = new Vector(res);
        closeDb();
        return v;
    }

    public Persona updateUser(Persona p) throws UsuarioNoExiste{
        openDB();
        Persona perAux;
        List<Persona> res = db.queryByExample(p);
        if (res.isEmpty())
            throw new UsuarioNoExiste();
        else
            perAux = res.get(0);
        db.close();
        return perAux;
    }

    public Persona modifyUser(Persona actual, Persona p) throws UsuarioNoExiste{
        openDB();
        List<Persona> res = db.queryByExample(actual);

        if (res.isEmpty()) {
            db.close();
            throw new UsuarioNoExiste();
        } else {
            actual = res.get(0);
            actual.clone(p);
            db.store(actual);
            db.commit();
            db.close();
        }
        return actual;
    }

    public void insertOffer(RuralHouse rh, Offer of){
        openDB();
        RuralHouse rhRef = (RuralHouse) db.queryByExample(rh).get(0);
        rhRef.addOffer(of);
        db.store(rhRef);
        db.commit();
        closeDb();
    }

    public Persona storeNewRuralHouse(RuralHouse rh, Persona p) throws UsuarioNoExiste{
        openDB();
        List<Propietario> res = db.queryByExample(p);
        if (res.isEmpty()) throw new UsuarioNoExiste();
        else
            p = res.get(0);
        ((Propietario) p).addRuralHouse(rh);
        db.store(p);
        db.commit();
        closeDb();
        return p;
    }
}
