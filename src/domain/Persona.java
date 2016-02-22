package domain;

/**
 * Created by joseba on 22/2/16.
 */
public abstract class Persona {

    private String mail;
    private String password;
    private String nombre;
    private String apellido;
    private String DNI;
    private int numTel;

    public Persona(String mail, String password, String nombre, String apellido, String DNI, int numTel) {
        this.mail = mail;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.numTel = numTel;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getNumTel() {
        return numTel;
    }

    public String getDNI() {
        return DNI;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }
}
