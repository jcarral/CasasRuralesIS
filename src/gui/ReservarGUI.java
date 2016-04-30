package gui;

import businessLogic.ruralManagerLogic;
import domain.Offer;
import domain.RuralHouse;
import exceptions.OfertaNoExiste;
import exceptions.UsuarioNoExiste;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.ResourceBundle;

public class ReservarGUI extends JPanel implements PanelCard{

    //UI components
    private JPanel panelContent, panelBtns, panelHeaderOffers;
    private JButton btnConfirmar;
    private DefaultComboBoxModel comboModel;
    private JComboBox comboList;
    private JEditorPane editorPane;

    private ruralManagerLogic logica;
    private List<RuralHouse> casas;
    private Offer ofertaReserva;
    private ResourceBundle strings;

    ReservarGUI(ruralManagerLogic logica) {

        this.logica = logica;
        strings = MainFrame.strings;
        this.casas = logica.getAllRuralHouses();
        this.add(setMainPane());

        this.addComponentListener(new ComponentListener() {
            ActionListener listener;

            public void componentResized(ComponentEvent e) {
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {

                printList(casas);
                comboModel.removeAllElements();
                searchOffers(casas);

                listener = ev -> { //El listener da problemas para actualizar los modelos de los cardlayouts
                    System.out.println("Seleccionada");
                    ofertaReserva = (Offer) comboModel.getElementAt(comboList.getSelectedIndex());
                    editorPane.setText(textoResumenOferta(ofertaReserva));
                    btnConfirmar.setEnabled(true);


                };
                comboList.addActionListener(listener);
                btnConfirmar.setEnabled(false);
            }

            public void componentHidden(ComponentEvent e) {
                comboList.removeActionListener(listener);
            }
        });

    }

    public JPanel getPanel() {
        return this;
    }

    @Override
    public void reloadFields() {
        strings = MainFrame.strings;
        btnConfirmar.setText(strings.getString("reservar.btnConfirmar"));
    }

    public void setList(List<RuralHouse> lista) {
        casas = lista;
    }

    private JPanel setMainPane() {
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        mainPane.add(setPanelContent());
        mainPane.add(setBtnPanel());

        return mainPane;
    }

    private JPanel setBtnPanel() {
        if (panelBtns == null) {
            panelBtns = new JPanel();
            btnConfirmar = new JButton(strings.getString("reservar.btnConfirmar"));
            btnConfirmar.setEnabled(false);
            confirmHandler(btnConfirmar);
            panelBtns.add(btnConfirmar);
        }
        return panelBtns;
    }

    private void confirmHandler(JButton btn) {
        btn.addActionListener(e -> {
            try {
                logica.confirmarReserva(ofertaReserva);
            } catch (UsuarioNoExiste usuarioNoExiste) {
                System.out.println(usuarioNoExiste.getMessage());
            } catch (OfertaNoExiste ofertaNoExiste) {
                System.out.println(ofertaNoExiste.getMessage());
            }
            JOptionPane.showMessageDialog(null, strings.getString("reservar.reservada"));
            this.setVisible(false);

        });
    }


    private JPanel setPanelContent() {
        if (panelContent == null) {
            panelContent = new JPanel();
            panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.PAGE_AXIS));
            comboModel = new DefaultComboBoxModel<>();
            comboList = new JComboBox(comboModel);

            if (!casas.isEmpty()) {
                searchOffers(casas);
            }

            panelContent.add(comboList);

            editorPane = new JEditorPane();
            HTMLEditorKit kitHTML = new HTMLEditorKit();
            editorPane.setEditorKit(kitHTML);

            StyleSheet css = kitHTML.getStyleSheet();

            //CSS RULES
            css.addRule("body{margin: 10px; padding: 10px; background-color: #bdc3c7; border: 2px solid #2c3e50}");
            css.addRule("h2{text-align: center;}");
            css.addRule("strong{display: inline-block; margin: 0; padding: 0; font-size: 16px;");
            css.addRule("p{display:inline-block; margin: 0; padding: 0;");
            css.addRule("div{margin-top: 15px;}");

            editorPane.setEditable(false);
            editorPane.setPreferredSize(new Dimension(520, 390));
            editorPane.setContentType("text/html");
            panelContent.add(editorPane);


        }
        return panelContent;
    }

    private String textoResumenOferta(Offer of) {
        RuralHouse rh = of.getRuralHouse();

        String nombre = strings.getString("reservar.Nombre"),
                localidad = strings.getString("reservar.Localidad"),
                inicioOferta = strings.getString("reservar.Inicio"),
                finOferta = strings.getString("reservar.Fin"),
                precio = strings.getString("reservar.Precio");

        String res = "<h2>Resumen oferta</h2>\n" +
                "  <div><strong>" + nombre + "</strong> <p> " + rh.getNombre() + "</p></div>\n" +
                "  <div><strong>" + localidad + "</strong> <p>" + rh.getCity() + "</p></div>\n" +
                "  <div><strong> "+ inicioOferta +" </strong> <p>" + of.getFirstDay() + "</p></div>\n" +
                "  <div><strong>"+ finOferta +"</strong> <p>" + of.getLastDay() + "</p></div>\n" +
                "  <div><strong>" + precio + "</strong> <p> " + of.getPrice() + "€ </p></div>\n";

        return res;
    }

    //Función para recargar el combomodel
    private void searchOffers(List<RuralHouse> list) {
        for (RuralHouse rh : list) {
            List<Offer> res = rh.getOffers();
            for (Offer of : res)
                if (of.getReserva() == null)
                    comboModel.addElement(of);
        }
    }

    //Función auxiliar para imprimir por consola todas las ofertas de las casas
    private void printList(List<RuralHouse> res) {
        for (RuralHouse rh : res) {
            List<Offer> resOf = rh.getOffers();
            System.out.println("RuralHouse: " + rh.getNombre());
            for (Offer of : resOf)
                System.out.println("---------> " + of.getOfferID() + ", esta llena: " + (of.getReserva() != null));
        }
    }

}
