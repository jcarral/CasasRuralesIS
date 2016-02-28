package gui;

import domain.RuralHouse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FichaGUI extends JFrame {

    //Elementos de la interfaz
    private JPanel mainPane, dataPane;
    private JTextField tfNombre, tfNumero, tfDireccion, tfCiudad, tfTel;
    private TextArea taDescripcion;
    private JButton btnCerrar;

    //Casa Rural actual
    private RuralHouse rh;

    /**
     * Create the frame.
     */
    public FichaGUI(RuralHouse rh) {
        super("Ficha técnica");
        this.rh = rh;
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        add(setMainPane());

        setVisible(true);
    }

    //JPanel principal
    private JPanel setMainPane() {

        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

            mainPane.add(estilosGUI.setHeaderPane("Ficha casa"));
            mainPane.add(setDataPane());

            mainPane.add(new JLabel("Descripción: "));
            taDescripcion = new TextArea(rh.getDescription());
            taDescripcion.setEditable(false);
            mainPane.add(taDescripcion);
            mainPane.add(setBtn());

        }
        return mainPane;
    }

    //JPanel con los datos de la casa rural
    private JPanel setDataPane() {

        if (dataPane == null) {
            dataPane = new JPanel(new GridLayout(5, 2));
            dataPane.add(new JLabel("Numero casa: "));
            tfNumero = new JTextField(Integer.toString(rh.getHouseNumber()));
            tfNumero.setEditable(false);
            dataPane.add(tfNumero);

            dataPane.add(new JLabel("Nombre de la casa: "));
            tfNombre = new JTextField(rh.getNombre());
            tfNombre.setEditable(false);
            dataPane.add(tfNombre);

            dataPane.add(new JLabel("Ciudad: "));
            tfCiudad = new JTextField(rh.getCity());
            tfCiudad.setEditable(false);
            dataPane.add(tfCiudad);

            dataPane.add(new JLabel("Dirección: "));
            tfDireccion = new JTextField(rh.getDir());
            tfDireccion.setEditable(false);
            dataPane.add(tfDireccion);

            dataPane.add(new JLabel("Numero de telefono: "));
            tfTel = new JTextField(Integer.toString(rh.getNumTel()));
            tfTel.setEditable(false);
            dataPane.add(tfTel);
        }
        return dataPane;
    }

    //Boton para cerrar la ventana
    private JButton setBtn(){
        if(btnCerrar == null){
            btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return btnCerrar;
    }
}
