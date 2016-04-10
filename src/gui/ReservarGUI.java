package gui;

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
    private JButton btnConfirmar;
    private DefaultComboBoxModel comboModel;
    private JComboBox comboList;
    private JEditorPane editorPane;

    private ruralManagerLogic logica;
    private List<RuralHouse> casas;
    private Offer ofertaReserva;

    ReservarGUI(ruralManagerLogic logica){

        this.logica = logica;
        this.casas = logica.getAllRuralHouses();
        this.add(setMainPane());

        this.addComponentListener(new ComponentListener() {
            ActionListener listener;
            public void componentResized(ComponentEvent e) {}
            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {
                System.out.println("Entro por aquí");
                printList(casas);
                comboModel.removeAllElements();
                System.out.println("comboModelSize: " + comboModel.getSize());
                searchOffers(casas);
                System.out.println("comboModelSize2: " + comboModel.getSize());

                listener = new ActionListener() {
                    /**
                     * Invoked when an action occurs.
                     *
                     * @param e
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Seleccionada");
                        ofertaReserva = (Offer) comboModel.getElementAt(comboList.getSelectedIndex());
                        editorPane.setText(textoResumenOferta(ofertaReserva));
                        btnConfirmar.setEnabled(true);
                    }

                };


                comboList.addActionListener(listener);
                System.out.println("Añadido! " + listener);

            }
            public void componentHidden(ComponentEvent e) {
                comboList.removeActionListener(listener);
            }
        });

    }

    public JPanel getPanel(){return this;}

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
            try {
                logica.confirmarReserva(ofertaReserva);
            } catch (UsuarioNoExiste usuarioNoExiste) {
                System.out.println(usuarioNoExiste.getMessage());
            } catch (OfertaNoExiste ofertaNoExiste) {
                System.out.println(ofertaNoExiste.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Oferta reservada correctamente");
            this.setVisible(false);

        });
    }



    private JPanel setPanelContent(){
        if(panelContent == null) {
            panelContent = new JPanel();
            panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.PAGE_AXIS));
            comboModel = new DefaultComboBoxModel<>();
            comboList = new JComboBox(comboModel);

            if(!casas.isEmpty()){
                searchOffers(casas);
            }

            panelContent.add(comboList);

            editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setPreferredSize(new Dimension(400, 200));
            editorPane.setContentType("text/html");
            panelContent.add(editorPane);


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

    private void searchOffers(List<RuralHouse> list){
        for(RuralHouse rh : list){
            List<Offer> res = rh.getOffers();
            for (Offer of : res)
                if(of.getReserva() == null)
                    comboModel.addElement(of);
        }
    }

    private void printList(List<RuralHouse> res){
        for(RuralHouse rh : res){
            List<Offer> resOf = rh.getOffers();
            System.out.println("RuralHouse: " + rh.getNombre());
            for(Offer of : resOf)
                System.out.println("---------> " + of.getOfferID() + ", esta llena: " + (of.getReserva() != null));
        }
    }

}
