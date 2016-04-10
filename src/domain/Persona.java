package domain;

/**
 * Created by joseba on 22/2/16.
 */
public abstract class Persona {
    private String apellido;
    private String mail;
    private String password;
    private String nombre;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public void clone(Persona p) {
        this.mail = p.getMail();
        this.password = p.getPassword();
        this.nombre = p.getNombre();
        this.apellido = p.getApellido();
        this.DNI = p.getDNI();
        this.numTel = p.getNumTel();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;

        Persona persona = (Persona) o;

        if (numTel != persona.numTel) return false;
        if (apellido != null ? !apellido.equals(persona.apellido) : persona.apellido != null) return false;
        if (mail != null ? !mail.equals(persona.mail) : persona.mail != null) return false;
        if (password != null ? !password.equals(persona.password) : persona.password != null) return false;
        if (nombre != null ? !nombre.equals(persona.nombre) : persona.nombre != null) return false;
        return !(DNI != null ? !DNI.equals(persona.DNI) : persona.DNI != null);

    }

    @Override
    public int hashCode() {
        int result = apellido != null ? apellido.hashCode() : 0;
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (DNI != null ? DNI.hashCode() : 0);
        result = 31 * result + numTel;
        return result;
    }
}
