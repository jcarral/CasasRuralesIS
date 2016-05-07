package dataAccess;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import domain.*;
import exceptions.OfertaNoExiste;
import exceptions.OfertaRepetida;
import exceptions.UsuarioNoExiste;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public void insertarReserva(Reserva reserva, Offer oferta) throws OfertaNoExiste {

        List<Offer> res = db.queryByExample(oferta);
        if(res.isEmpty()) throw new OfertaNoExiste("No se puede hacer una reserva porque no existe oferta");
        else{
            Offer actualOffer = res.get(0);
            reserva.setOffer(actualOffer);
            actualOffer.setReserva(reserva);
            db.store(reserva);

            db.commit();
            db.close();
            System.out.println("Reserva almacenada: " + actualOffer.getReserva().toString());
        }
    }

    /**
     * Función para obtener la referencia al objeto Personar
     * @param p Persona que se quiere recuperar
     * @return la referencia del objeto
     * @throws UsuarioNoExiste si la persona que se busca no se encuentra en la base de datos
     */
    public Persona getUserRef(Persona p) throws UsuarioNoExiste{
        openDB();
        List<Persona> res = db.queryByExample(p);
        if(res.isEmpty()) throw new UsuarioNoExiste("No se puede obtener la referencia del usuario");
        else return res.get(0);
    }
    /**
     * Función para validar si una persona existe en la base de datos
     * @param p Persona que se busca
     * @return Objeto con la referencia de la persona
     * @throws UsuarioNoExiste si la persona no existe como cliente o propietario
     */
    public Persona validUser(Cliente c, Propietario p) throws UsuarioNoExiste{
        Persona actualUser;
        openDB();
        List<Persona> resP = db.queryByExample(p);
        List<Persona> resC = db.queryByExample(c);
        List<Persona> res = new LinkedList<>(resP);
        res.addAll(resC);
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
    public void insertOffer(RuralHouse rh, Offer of) throws OfertaRepetida {
        openDB();
        RuralHouse rhRef = (RuralHouse) db.queryByExample(rh).get(0);
        List<Offer> offers = db.queryByExample(of);
        if(!offers.isEmpty()) throw new OfertaRepetida("La oferta que intentas meter ya está en la base de datos");
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

    /**
     * Obtiene todas las reservas que han hecho para la casa de un usuario
     * @param p
     * @return
     */
    public List<Reserva> getUserReservedOffers(Propietario p){
        openDB();

        List<Reserva> res = db.query(new Predicate<Reserva>() {

            @Override
            public boolean match(Reserva reserva) {

                return reserva.getUsuario() != null && reserva.getOferta() != null && p.equals(reserva.getOferta().getRuralHouse().getPropietario());
            }
        });


        return res;
    }

    public List<Reserva> obtainOffersReservedBy(Persona p){
        openDB();
        Reserva reserva = new Reserva(p);
        reserva.setFechaReserva(null);
        reserva.setReservaID(null);
        List<Reserva> res = db.queryByExample(reserva);
        List<Reserva> resultado = new ArrayList<>(res);
        closeDb();
        return resultado;
    }

    public void borrarOferta(Reserva r) throws OfertaNoExiste{
        openDB();
        //List<Offer> resOf = db.queryByExample(of);
        //List<Persona> resPer = db.queryByExample(p);
        Reserva resAux = new Reserva(null);
        resAux.createBlankReserve(r.getReservaID());
        List<Reserva> res = db.queryByExample(resAux);
        if(res.isEmpty() ){
            closeDb();
            throw new OfertaNoExiste("La oferta que se quiere borrar no existe");
        }


        Offer oferta = res.get(0).getOferta();
        db.delete(res.get(0));
        oferta.setReserva(null);
        db.store(oferta);
        db.commit();
        closeDb();
    }
}
