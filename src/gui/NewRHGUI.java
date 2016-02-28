package gui;

import businessLogic.ruralManagerLogic;
import exceptions.UsuarioNoExiste;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by joseba on 25/2/16.
 */
public class NewRHGUI extends JFrame {

    private ruralManagerLogic logica;

    private JPanel mainPane, infoPane, btnPane;
    private JTextField tfNombre, tfCiudad, tfDir, tfNumTel;
    private JTextArea taDesc;
    private JButton btnAceptar;

    NewRHGUI(ruralManagerLogic logica) {
        super("Añadir nueva casa");

        this.logica = logica;
        setSize(500, 600);
        setLocationRelativeTo(null);
        add(setMainPane());
        frameEvents();
        setVisible(true);
    }

    private JPanel setMainPane() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
            mainPane.add(estilosGUI.setHeaderPane("Añadir casa nueva"));
            mainPane.add(setInfoPane());
            mainPane.add(setBtnPane());
        }
        return mainPane;
    }

    private JPanel setInfoPane() {

        if (infoPane == null) {
            infoPane = new JPanel();
            infoPane.add(new JLabel("Nombre de la casa: "));
            tfNombre = new JTextField(10);
            infoPane.add(tfNombre);

            infoPane.add(new JLabel("Ciudad: "));
            tfCiudad = new JTextField(10);
            infoPane.add(tfCiudad);

            infoPane.add(new JLabel("Numero de telefono: "));
            tfNumTel = new JTextField(10);
            tfNumTel.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                }

                @Override
                public void focusLost(FocusEvent e) {
                    try {
                        Integer.parseInt(tfNumTel.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Mete un número de teléfono bien");
                        tfNumTel.setBackground(Color.red);
                        tfNumTel.setText("");
                    }
                }
            });
            infoPane.add(tfNumTel);

            infoPane.add(new JLabel("Dirección: "));
            tfDir = new JTextField(10);
            infoPane.add(tfDir);

            infoPane.add(new JLabel("Descripción sobre la casa rural"));
            taDesc = new JTextArea(10, 20);
            infoPane.add(taDesc);

        }
        return infoPane;
    }

    private JPanel setBtnPane() {

        if (btnPane == null) {
            btnPane = new JPanel();
            btnAceptar = new JButton("Aceptar");
            btnAceptar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (camposLlenos()) {
                        try {
                            logica.storeRH(tfNombre.getText(), tfCiudad.getText(), tfDir.getText(), Integer.parseInt(tfNumTel.getText()), taDesc.getText());
                            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                            dispose();
                        } catch (UsuarioNoExiste ex) {
                            JOptionPane.showMessageDialog(null, "No se han podido guardar los datos, intentalos más tarde");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No has rellenado todos los campos");
                    }
                }
            });

            btnPane.add(btnAceptar);
        }
        return btnPane;
    }

    private boolean camposLlenos() {
        return !tfDir.getText().isEmpty() && !tfNumTel.getText().isEmpty() && !tfCiudad.getText().isEmpty()
                && !tfNombre.getText().isEmpty() && !taDesc.getText().isEmpty();
    }

    private void frameEvents() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (!camposLlenos()) {
                    int num = JOptionPane.showConfirmDialog(null,
                            "Si sales perderas los cambios, ¿estás seguro?", null, JOptionPane.YES_NO_OPTION);
                    if (num == JOptionPane.YES_OPTION)
                        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    else
                        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }


}
