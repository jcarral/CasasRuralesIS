package gui;

import businessLogic.ruralManagerLogic;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import domain.Offer;
import domain.Reserva;
import exceptions.OfertaNoExiste;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MisReservas extends JFrame{

    private ruralManagerLogic logica;
    private ResourceBundle strings;

    private JPanel mainPanel;
    private DefaultListModel model;

    public MisReservas(ruralManagerLogic logica){
        super();
        this.setSize(new Dimension(520, 500));
        this.logica = logica;
        strings = MainFrame.strings;
        this.setTitle(strings.getString("misReservas.titulo"));
        this.add(setMainPanel());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel setMainPanel(){
        if(mainPanel == null){
            mainPanel = new JPanel();
            model = new DefaultListModel();
            JList lista = new JList(model);
            JButton btnBorrar = new JButton(strings.getString("misReservas.btnBorrar"));
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

            });

            btnBorrar.addActionListener(e->{
                System.out.println(model.getElementAt(lista.getSelectedIndex()));
                Offer of = ((Reserva) model.getElementAt(lista.getSelectedIndex())).getOferta();
                try {
                    logica.cancelarReserva((Reserva) model.getElementAt(lista.getSelectedIndex()));
                    JOptionPane.showMessageDialog(null,
                            strings.getString("misReservas.alerta"),
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
