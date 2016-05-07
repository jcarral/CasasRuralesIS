package gui;

import businessLogic.ruralManagerLogic;
import domain.Offer;
import domain.Reserva;
import exceptions.OfertaNoExiste;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MisReservas extends JFrame{

    private ruralManagerLogic logica;

    private JPanel mainPanel;
    private DefaultListModel model;

    public MisReservas(ruralManagerLogic logica){
        super("Mis reservas");
        this.setSize(new Dimension(520, 500));
        this.logica = logica;
        this.add(setMainPanel());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel setMainPanel(){
        if(mainPanel == null){
            mainPanel = new JPanel();
            model = new DefaultListModel();
            JList lista = new JList(model);
            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setEnabled(false);
            loadList();
            lista.addListSelectionListener(e->{
                Date fechaInicio = (Date) ((Reserva) model.getElementAt(lista.getSelectedIndex())).getOferta().getFirstDay();
                Date now  = new Date();
                Date limitDay = new Date(now.getTime() + (60*60*24*1000*15));
                if(limitDay.after(fechaInicio)){
                    btnBorrar.setEnabled(false);
                }else{
                    btnBorrar.setEnabled(true);
                }
                System.out.println("Fecha actual: " + now + ", fecha limite: " + limitDay + ", fecha inicio" + fechaInicio);
            });

            btnBorrar.addActionListener(e->{
                System.out.println(model.getElementAt(lista.getSelectedIndex()));
                Offer of = ((Reserva) model.getElementAt(lista.getSelectedIndex())).getOferta();
                try {
                    logica.cancelarReserva((Reserva) model.getElementAt(lista.getSelectedIndex()));
                    JOptionPane.showMessageDialog(null,
                            "La oferta se ha borrado correctamente",
                            "OK",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (OfertaNoExiste ofertaNoExiste) {
                    ofertaNoExiste.printStackTrace();
                }
                this.setVisible(false);
            });

            mainPanel.add(lista);
            mainPanel.add(btnBorrar);
        }
        return mainPanel;
    }

    private void loadList(){
        java.util.List<Reserva> rlist = logica.obtainReservedOffers();
        model.clear();
        for(Reserva r : rlist){
            model.addElement(r);
        }
    }
}
