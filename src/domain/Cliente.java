package domain;

/**
 * Created by joseba on 22/2/16.
 */
public class Cliente extends Persona {
    public Cliente(String mail, String password, String nombre, String apellido, String DNI, int numTel) {
        super(mail, password, nombre, apellido, DNI, numTel);
    }
}
