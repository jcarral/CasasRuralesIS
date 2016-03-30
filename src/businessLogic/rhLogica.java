package businessLogic;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import dataAccess.DataAccess;
import domain.*;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import exceptions.UsuarioNoExiste;
import exceptions.UsuarioRepetido;

import java.util.Date;
import java.util.List;
import java.util.Vector;


public class rhLogica implements ruralManagerLogic {

    private Persona actualUser;

    public rhLogica() {

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
        DataAccess dataManager = new DataAccess();
        int val;

        if (usuario) {
            p = new Cliente(mail, pass, null, null, null, 0);
            val = 0;
        } else {
            p = new Propietario(mail, pass, null, null, null, 0);
            val = 1;
        }

        actualUser = dataManager.validUser(p);
        dataManager.closeDb();

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

        DataAccess dataManager = new DataAccess();
        Persona p;

        p = (propietario)?new Propietario(mail, password, nombre, apellido, DNI, numTel):
        new Cliente(mail, password, nombre, apellido, DNI, numTel);


        Propietario prop = new Propietario(mail, null, null, null, null, 0);
        Cliente cli = new Cliente(mail, null, null, null, null, 0);
        if(!dataManager.existsUser(prop, cli)){
            dataManager.insertUser(p);
        }else{
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
        p = (actualUser.getClass().equals(Cliente.class))?
            new Cliente(mail, password, nombre, apellido, DNI, numTel):
            new Propietario(mail, password, nombre, apellido, DNI, numTel);
        insertPersona(p);
    }

    /**
     * Devuelve todas las casas rurales de la base de datos
     * @return Vector con todas las casas rurales
     */
    @Override
    public Vector<RuralHouse> getAllRuralHouses() {

        DataAccess dataManager = new DataAccess();
        return dataManager.getRuralHousesBy(new RuralHouse(null, null, null, null, 0, null));
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
        DataAccess dataManager = new DataAccess();
        RuralHouse rh = new RuralHouse(createRHNumber(), desc, city, nombre, numTel, direccion);
        actualUser = dataManager.storeNewRuralHouse(rh, actualUser);
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

        DataAccess dataManager = new DataAccess();
        if (firstDay.after(lastDay)) throw new BadDates("Fechas incorrectas");
        Offer oferta = new Offer(1, firstDay, lastDay, price, null);
        dataManager.insertOffer(ruralHouse, oferta);
        Propietario p = new Propietario(actualUser.getMail(), actualUser.getPassword(), actualUser.getNombre(), actualUser.getApellido(), actualUser.getDNI(), actualUser.getNumTel());
        actualUser = dataManager.updateUser(p);
        return oferta;
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

        DataAccess dataManager = new DataAccess();
        actualUser = dataManager.modifyUser(actualUser, p);
    }

    //Actualiza los valores de la persona actual
    private void updatePersona() throws UsuarioNoExiste {

        DataAccess dataManager = new DataAccess();
        actualUser = dataManager.updateUser(actualUser);
    }
}
