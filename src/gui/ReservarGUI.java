package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

import java.awt.event.*;
import java.util.List;


import domain.Offer;
import domain.RuralHouse;
import businessLogic.ruralManagerLogic;
import exceptions.OfertaNoExiste;
import exceptions.UsuarioNoExiste;



public class ReservarGUI extends JPanel {


    //UI components
    private JPanel panelContent, panelBtns, panelHeaderOffers;
    private JList listOffers;
    private DefaultListModel listModel;
    private JScrollPane scrollPaneList;
    private JButton btnConfirmar;
    private TextArea resumenOferta;

    private ruralManagerLogic logica;
    private List<RuralHouse> casas;
    private Offer ofertaReserva;

    ReservarGUI(ruralManagerLogic logica){

        this.logica = logica;
        this.casas = logica.getAllRuralHouses();
        this.add(setMainPane());

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {


            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {


                for(RuralHouse rh: casas)
                    searchOffers(rh);

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }

    ReservarGUI(List<RuralHouse> casas, ruralManagerLogic logica){
        this(logica);
        this.casas = casas;
    }



    public JPanel getPanel(){
        return this;
    }

    public void setList(List<RuralHouse> lista){
        casas = lista;
    }
    private JPanel setMainPane(){
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        mainPane.add(setPanelContent());
        mainPane.add(setBtnPanel());

        return mainPane;
    }

    private JPanel setBtnPanel(){
        if(panelBtns == null){
            panelBtns = new JPanel();



           btnConfirmar = new JButton("Confirmar");
            btnConfirmar.setEnabled(false);
            confirmHandler(btnConfirmar);
            panelBtns.add(btnConfirmar);
        }
        return panelBtns;
    }

    private void confirmHandler(JButton btn){
        btn.addActionListener(e->{
            //TODO: Añadir la oferta
            try {
                logica.confirmarReserva(ofertaReserva);
            } catch (UsuarioNoExiste usuarioNoExiste) {
                System.out.println(usuarioNoExiste.getMessage());
            } catch (OfertaNoExiste ofertaNoExiste) {
                System.out.println(ofertaNoExiste.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Oferta reservada correctamente");

            this.setVisible(false);
            listOffers.clearSelection();
        });
    }



    private JPanel setPanelContent(){
        if(panelContent == null) {
            panelContent = new JPanel();
            panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.PAGE_AXIS));

            listModel = new DefaultListModel();
            listOffers = new JList(listModel);
            scrollPaneList = new JScrollPane(listOffers);
            if(!casas.isEmpty()){
                RuralHouse rh = casas.get(0);
                searchOffers(rh);
            }

            panelContent.add(scrollPaneList);

            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setPreferredSize(new Dimension(400, 200));
            editorPane.setContentType("text/html");
            panelContent.add(editorPane);

            listOffers.addListSelectionListener(e->{
                ofertaReserva = (Offer) listModel.getElementAt(listOffers.getSelectedIndex());
                editorPane.setText(textoResumenOferta(ofertaReserva));
                btnConfirmar.setEnabled(true);
            });


        }

        return panelContent;
    }

    private String textoResumenOferta(Offer of){
        RuralHouse rh = of.getRuralHouse();
        String res = "<h2>Resumen oferta</h2>\n" +
                "  <strong>Nombre casa rural:</strong> <p> " + rh.getNombre() + "</p>\n" +
                "  <strong>Localidad:</strong> <p>" + rh.getCity() +  "</p>\n" +
                "  <strong>Inicio oferta:</strong> <p>" + of.getFirstDay() +"</p>\n" +
                "  <strong>Fin Oferta: </strong> <p>"+  of.getLastDay() + "</p>\n" +
                "  <strong>Precio:</strong> <p> "+ of.getPrice()+"€ </p>";
        return res;

    }

    private void searchOffers(RuralHouse rh){
        List<Offer> res = rh.getOffers();
        listModel.clear();
        for (Offer of : res){
            if(of.getReserva() == null)
                listModel.addElement(of);
        }

    }



}
