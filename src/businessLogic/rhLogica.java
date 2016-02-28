package businessLogic;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import domain.*;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import exceptions.UsuarioNoExiste;
import exceptions.UsuarioRepetido;

import java.util.Arrays;
import java.util.Date;
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

    }

    private void openDB() {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().activationDepth(MAXNIVEL);
        configuration.common().updateDepth(MAXNIVEL);
        db = Db4oEmbedded.openFile(configuration, DB4OFILENAME);
    }

    /**
     * Comprueba si existe un usuario con esos atributos en la base de datos
     * @param mail correo del usuario
     * @param pass contraseña del usuario
     * @param usuario true si es usuario, false si es propietario
     * @return 0 si es usuario, 1 si es propietario
     * @throws UsuarioNoExiste si el usuario no existe en la base de datos
     */
    @Override
    public int checkLogin(String mail, String pass, boolean usuario) throws UsuarioNoExiste {

        Persona p;
        int val;

        if (usuario) {
            p = new Usuario(mail, pass, null, null, null, 0);
            val = 0;
        } else {
            p = new Propietario(mail, pass, null, null, null, 0);
            val = 1;
        }

        openDB();
        List<Persona> res = db.queryByExample(p);

        if (!res.isEmpty()) {
            actualUser = res.get(0);

        } else {
            throw new UsuarioNoExiste();
        }
        db.close();
        return val;
    }

    /**
     * Guarda un nuevo usuario en la base de datos
     * @param mail correo del usuario
     * @param password contraseña del usuario
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     * @param DNI dni del usuario
     * @param numTel numero de telefono del usuario
     * @param propietario true si es propietario, false si es usuario
     * @throws UsuarioRepetido si existe en la base de datos una persona con el mismo correo
     */
    @Override
    public void storeUsuario(String mail, String password, String nombre, String apellido, String DNI, int numTel, boolean propietario) throws UsuarioRepetido {
        Persona p;
        List<Persona> props, users;
        openDB();
        if (propietario) {
            p = new Propietario(mail, password, nombre, apellido, DNI, numTel);

        } else {
            p = new Usuario(mail, password, nombre, apellido, DNI, numTel);

        }
        props = db.queryByExample(new Propietario(mail, null, null, null, null, 0));
        users = db.queryByExample(new Usuario(mail, null, null, null, null, 0));

        if (props.isEmpty() && users.isEmpty()) {
            db.store(p);
            db.commit();
            db.close();
        } else {
            db.close();
            throw new UsuarioRepetido();
        }
    }

    /**
     * Obtiene la información del usuario actual
     * @return Array con toda la información del usuario
     */
    @Override
    public String[] getUserInfo() {
        String[] info = new String[5];
        try {
            updatePersona();

            info[0] = actualUser.getMail();
            info[1] = actualUser.getNombre();
            info[2] = actualUser.getApellido();
            info[3] = actualUser.getDNI();
            info[4] = Integer.toString(actualUser.getNumTel());

        } catch (UsuarioNoExiste usuarioNoExiste) {
            usuarioNoExiste.printStackTrace();
        }
        return info;
    }

    /**
     * Actualiza los datos de la persona actual
     * @param mail
     * @param password
     * @param nombre
     * @param apellido
     * @param DNI
     * @param numTel
     * @throws UsuarioNoExiste si actualmente no hay ningún usuario
     */
    @Override
    public void updatePersona(String mail, String password, String nombre, String apellido, String DNI, int numTel) throws UsuarioNoExiste {
        Persona p;
        if (actualUser.getClass().equals(Usuario.class))
            p = new Usuario(mail, password, nombre, apellido, DNI, numTel);
        else
            p = new Propietario(mail, password, nombre, apellido, DNI, numTel);
        insertPersona(p);
    }

    /**
     * Devuelve todas las casas rurales de la base de datos
     * @return Vector con todas las casas rurales
     */
    @Override
    public Vector<RuralHouse> getAllRuralHouses() {
        openDB();
        List<RuralHouse> res = db.queryByExample(new RuralHouse(null, null, null, null, 0, null));
        Vector<RuralHouse> v = new Vector<>(res);
        db.close();
        return v;
    }

    /**
     * Devuelve todas las casas rurales del actual usuario
     * @return Lista con todas las casas rurales
     */
    @Override
    public List<RuralHouse> getUsersRuralHouses() {
        return ((Propietario) actualUser).getListaCasas();
    }

    /**
     * Almacena una nueva casa rural en la base de datos
     * @param nombre
     * @param city
     * @param direccion
     * @param numTel
     * @param desc
     * @throws UsuarioNoExiste Si actualmente no hay ningún usuario
     */
    @Override
    public void storeRH(String nombre, String city, String direccion, int numTel, String desc) throws UsuarioNoExiste {

        openDB();
        List<Propietario> res = db.queryByExample(actualUser);
        if (res.isEmpty()) throw new UsuarioNoExiste();
        else
            actualUser = res.get(0);
        RuralHouse rh = new RuralHouse(createRHNumber(), desc, city, nombre, numTel, direccion);
        ((Propietario) actualUser).addRuralHouse(rh);
        db.store(actualUser);
        db.commit();
        db.close();

    }

    /**
     * Almacena una nueva oferta en la base de datos
     * @param ruralHouse
     * @param firstDay
     * @param lastDay
     * @param price
     * @return Devuelve la nueva oferta
     * @throws BadDates Si el primer día es posterior al último
     * @throws OverlappingOfferExists Si ya existe esa oferta en la bd
     * @throws UsuarioNoExiste Si actualmente no hay ningun usuario
     */
    @Override
    public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws BadDates, OverlappingOfferExists, UsuarioNoExiste {
        Offer of;
        openDB();


        if (firstDay.after(lastDay)) throw new BadDates("Fechas incorrectas");
        RuralHouse rh = (RuralHouse) db.queryByExample(ruralHouse).get(0);
        of = rh.createOffer(1, firstDay, lastDay, price);

        if (db.queryByExample(of).size() > 0) throw new OverlappingOfferExists("La oferta ya existe");

        db.store(rh);
        db.commit();
        List<Propietario> res = db.queryByExample(new Propietario(actualUser.getMail(), actualUser.getPassword(),
                actualUser.getNombre(), actualUser.getApellido(), actualUser.getDNI(), actualUser.getNumTel()));
        if (res.isEmpty()) throw new UsuarioNoExiste();
        else
            actualUser = res.get(0);
        db.close();
        return of;
    }

    //Genera un número para cada casa
    private int createRHNumber() {
        List<RuralHouse> res = getUsersRuralHouses();
        String numero = Integer.toString(res.size()) + 1;
        numero += (int) Math.floor((Math.random() * 1000) + 1);
        return Integer.parseInt(numero);
    }

    //Inserta una persona p en la base de datos
    private void insertPersona(Persona p) throws UsuarioNoExiste {

        openDB();
        List<Persona> res = db.queryByExample(actualUser);
        if (res.isEmpty()) {
            db.close();
            throw new UsuarioNoExiste();
        } else {
            actualUser = res.get(0);
            actualUser.clone(p);
            db.store(actualUser);
            db.commit();
            db.close();
        }
    }

    //Actualiza los valores de la persona actual
    private void updatePersona() throws UsuarioNoExiste {
        openDB();
        List<Persona> res = db.queryByExample(actualUser);
        if (res.isEmpty())
            throw new UsuarioNoExiste();
        else
            actualUser = res.get(0);
        db.close();
    }
}
