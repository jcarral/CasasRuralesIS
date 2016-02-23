package gui;

import businessLogic.rhLogica;
import businessLogic.ruralManagerLogic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PropietarioGUI extends JFrame {

    private ruralManagerLogic logica;


    private JPanel mainPane, infoPane, btnPane;
    private JLabel lblMail, lblNombre, lblApellido;
    private JButton btnAddRh, btnAddOff, btnEdit;

    PropietarioGUI(ruralManagerLogic logica) {
        super("Zona propietarios");
        this.logica = logica;
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(setMainPanel());
        frameEvents();

        setVisible(true);

    }

    private JPanel setMainPanel() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
            mainPane.add(estilosGUI.setHeaderPane("Zona Usuarios"));
            mainPane.add(setInfoPane());
            mainPane.add(setBtnPanel());
        }
        return mainPane;
    }

    private JPanel setBtnPanel() {
        if (btnPane == null) {
            btnPane = new JPanel(new FlowLayout());
            btnPane.add(setBtnAddRH());
            btnPane.add(setBtnAddOff());
            btnPane.add(setBtnEdit());
            btnPane.setBackground(estilosGUI.bckColorDark);
            btnPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        }
        return btnPane;
    }

    private JButton setBtnAddRH() {
        if (btnAddRh == null) {
            Icon iCasa = new ImageIcon("images/rh.png");
            btnAddRh = new JButton("Añadir casa ", iCasa);
            btnAddRh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AddCasaGUI();
                }
            });
        }
        return btnAddRh;
    }

    private JButton setBtnEdit() {
        if (btnEdit == null) {
            Icon iEdit = new ImageIcon("images/edit.png");
            btnEdit = new JButton("Editar info ", iEdit);
            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new UsuarioEditGUI(logica);
                }
            });
        }
        return btnEdit;
    }

    private JButton setBtnAddOff() {
        if (btnAddOff == null) {
            Icon iOff = new ImageIcon("images/offer.png");
            btnAddOff = new JButton("Añadir oferta ", iOff);
            btnAddOff.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new SetAvailabilityGUI();
                }
            });
        }
        return btnAddOff;
    }

    private JPanel setInfoPane() {
        if (infoPane == null) {
            updateInfoPane();

        }

        return infoPane;
    }


    private void updateInfoPane() {
        infoPane = new JPanel();

        String[] info = logica.getUserInfo();

        if (lblMail == null) {
            lblMail = new JLabel(info[estilosGUI.MAIL]);
            infoPane.add(lblMail);
        } else
            lblMail.setText(info[estilosGUI.MAIL]);

        if (lblNombre == null) {
            lblNombre = new JLabel(info[estilosGUI.NOMBRE]);
            infoPane.add(lblNombre);
        } else
            lblNombre.setText(info[estilosGUI.NOMBRE]);

        if (lblApellido == null) {
            lblApellido = new JLabel(info[estilosGUI.APELLIDO]);
            infoPane.add(lblApellido);
        } else
            lblApellido.setText(info[estilosGUI.APELLIDO]);
        infoPane.setBackground(estilosGUI.bckColor);
    }

    private void frameEvents() {
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                updateInfoPane();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}
