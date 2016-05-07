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
    }

    public void setOffer(Offer oferta) {
        this.oferta = oferta;
        oferta.setReserva(this);

    }

    public String getReservaID() {
        return reservaID;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setReservaID(String reservaID) {
        this.reservaID = reservaID;
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
        return "<html>Fecha de la reserva: " + fechaReserva +
                "<br> Oferta: " +  oferta.getRuralHouse().getNombre() +
                "<br> Dia inicio: " + oferta.getFirstDay() + "</html>";
    }


    public void createBlankReserve(String id){
        oferta=null;
        fechaReserva=null;
        reservaID=id;
    }
}
