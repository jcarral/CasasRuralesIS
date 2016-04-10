package domain;

import java.util.Date;
import java.util.UUID;


public class Reserva {

    private Offer oferta;
    private Date fechaReserva;
    private String reservaID;
    private Persona usuario;


    public Reserva(Persona usuario) {


        this.usuario = usuario;
        this.reservaID = UUID.randomUUID().toString();
        this.fechaReserva = new Date(System.currentTimeMillis());

        System.out.println("Nueva reserva realizada el " + fechaReserva + "por el usuario " + this.usuario.getNombre());
    }

    public void setOffer(Offer oferta) {
        this.oferta = oferta;
        oferta.setReserva(this);

    }

    public Persona getUsuario() {
        return usuario;
    }

    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

    public Offer getOferta() {
        return oferta;
    }

    public void setOferta(Offer oferta) {
        this.oferta = oferta;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "oferta=" + oferta +
                ", fechaReserva=" + fechaReserva +
                ", reservaID='" + reservaID + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
