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

    /**
     * Función para cerrar la conexión con la base de datos
     */
    public void closeDb(){
        db.close();
    }

    /**
     * Función para validar si una persona existe en la base de datos
     * @param p
     * @return
     * @throws UsuarioNoExiste
     */
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

    /**
     * Función para validar si un usuario existe como persona o cliente
     * @param p
     * @param c
     * @return
     */
    public boolean existsUser(Propietario p, Cliente c){
        List<Persona> props, users;
        openDB();

        props = db.queryByExample(p);
        users = db.queryByExample(c);

        boolean exists = !(props.isEmpty() && users.isEmpty());
        closeDb();
        return exists;
    }

    /**
     * Función para almacenar un usuario nuevo en la base de datos
     * @param p
     */
    public void insertUser(Persona p){
        openDB();
        db.store(p);
        db.commit();
        closeDb();
    }

    /**
     * Función para obtener todas las casas rurales que coincidan con la que se pasa por parametro
     * @param rh
     * @return
     */
    public Vector<RuralHouse> getRuralHousesBy(RuralHouse rh){
        openDB();
        System.out.println(rh);
        List<RuralHouse> res = db.queryByExample(rh);
        Vector<RuralHouse> v = new Vector(res);
        closeDb();
        return v;
    }

    /**
     * Función para actualizar el usuario
     * @param p
     * @return
     * @throws UsuarioNoExiste
     */
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

    /**
     * Función para modificar un usuario
     * @param actual
     * @param p
     * @return
     * @throws UsuarioNoExiste
     */
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

    /**
     * Función para insertar una oferta nueva en la base de datos
     * @param rh
     * @param of
     */
    public void insertOffer(RuralHouse rh, Offer of){
        openDB();
        RuralHouse rhRef = (RuralHouse) db.queryByExample(rh).get(0);
        rhRef.addOffer(of);
        db.store(rhRef);
        db.commit();
        closeDb();
    }

    /**
     * Función para almacenar una casa nueva en la base de datos
     * @param rh
     * @param p
     * @return
     * @throws UsuarioNoExiste
     */
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
