package domain;

import java.util.List;

/**
 * Created by joseba on 22/2/16.
 */
public class Propietario extends Persona {
    private List<RuralHouse> listaCasas;

    Propietario(String mail, String password, String nombre, String apellido, String DNI, int numTel, List<RuralHouse> listaCasas) {
        super(mail, password, nombre, apellido, DNI, numTel);
        this.listaCasas = listaCasas;
    }

    public List<RuralHouse> getListaCasas() {
        return listaCasas;
    }

    public void setListaCasas(List<RuralHouse> listaCasas) {
        this.listaCasas = listaCasas;
    }

    public void addRuralHouse(RuralHouse rh) {
        listaCasas.add(rh);
    }
}
