package businessLogic;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import domain.*;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

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
        List<RuralHouse> res = db.queryByExample(new RuralHouse(null, null, null, null, 0, null));
        System.out.println("Borrar" + res);
        return new Vector<RuralHouse>(res);
    }

    @Override
    public List<RuralHouse> getUsersRuralHouses() {
        return ((Propietario) actualUser).getListaCasas();
    }

    @Override
    public boolean storeRH(String nombre, String city, String direccion, int numTel, String desc) {

        RuralHouse rh = new RuralHouse(setRHNumber(), desc, city, nombre, numTel, direccion);
        ((Propietario) actualUser).addRuralHouse(rh);

        return insertPersona(actualUser);

    }

    @Override
    public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws BadDates, OverlappingOfferExists{
        Offer of;

        if(firstDay.after(lastDay)) throw new BadDates("Fechas incorrectas");
        of = ruralHouse.createOffer(1, firstDay, lastDay, price);
        if(db.queryByExample(of).size() > 0) throw new OverlappingOfferExists("La oferta ya existe");
        insertPersona(actualUser);
        return of;
    }

    private int setRHNumber() {
        List<RuralHouse> res = getUsersRuralHouses();
        String numero = Integer.toString(res.size());
        numero += (int) Math.floor((Math.random() * 1000) + 1);
        return Integer.parseInt(numero);
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
