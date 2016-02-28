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

    @Override
    public void updatePersona(String mail, String password, String nombre, String apellido, String DNI, int numTel) throws UsuarioNoExiste {
        Persona p;
        if (actualUser.getClass().equals(Usuario.class))
            p = new Usuario(mail, password, nombre, apellido, DNI, numTel);
        else
            p = new Propietario(mail, password, nombre, apellido, DNI, numTel);
        insertPersona(p);
    }

    @Override
    public Vector<RuralHouse> getAllRuralHouses() {
        openDB();
        List<RuralHouse> res = db.queryByExample(new RuralHouse(null, null, null, null, 0, null));
        Vector<RuralHouse> v = new Vector<>(res);
        db.close();
        return v;
    }

    @Override
    public List<RuralHouse> getUsersRuralHouses() {
        return ((Propietario) actualUser).getListaCasas();
    }

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

    private int createRHNumber() {
        List<RuralHouse> res = getUsersRuralHouses();
        String numero = Integer.toString(res.size());
        numero += (int) Math.floor((Math.random() * 1000) + 1);
        return Integer.parseInt(numero);
    }


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
