package gui;

import businessLogic.rhLogica;
import businessLogic.ruralManagerLogic;
import gui.QueryAvailabilityGUI;
import gui.estilosGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by joseba on 22/2/16.
 */
public class UsuarioGUI extends JFrame {

    private ruralManagerLogic logica;
    private JPanel mainPane, infoPane, btnPane;
    private JLabel lblNombre, lblMail, lblApellido;
    private JButton btnEdit, btnQuery;

    private final int MAIL = 0, NOMBRE = 1, APELLIDO = 2;


    UsuarioGUI(ruralManagerLogic logica) {
        super("Zona usuarios");
        this.logica = logica;

        setSize(500, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(setMainPanel());

        setVisible(true);
    }


    private JPanel setMainPanel() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
            mainPane.add(estilosGUI.setHeaderPane("Zona Usuarios"));
            mainPane.add(setInfoPanel());
            mainPane.add(setBtnPanel());
            mainPane.setBackground(estilosGUI.bckColor);
        }
        return mainPane;
    }

    private JPanel setInfoPanel() {
        if (infoPane == null) {
            infoPane = new JPanel(new FlowLayout());
            updateInfoPane();

        }

        return infoPane;
    }

    private void updateInfoPane() {
        String[] info = logica.getUserInfo();

        lblMail = new JLabel(info[MAIL]);
        infoPane.add(lblMail);

        lblNombre = new JLabel(info[NOMBRE]);
        infoPane.add(lblNombre);

        lblApellido = new JLabel(info[APELLIDO]);
        infoPane.add(lblApellido);

        infoPane.setBackground(estilosGUI.bckColor);
    }

    private JPanel setBtnPanel() {
        if (btnPane == null) {
            btnPane = new JPanel(new FlowLayout());
            btnPane.add(setEditableBtn());
            btnPane.add(setQueryBtn());
            btnPane.setBackground(estilosGUI.bckColorDark);
            btnPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        }
        return btnPane;
    }

    private JButton setQueryBtn() {
        if (btnQuery == null) {
            Icon edit = new ImageIcon("images/search.png");
            btnQuery = new JButton("Buscar ", edit);
            btnQuery.setSize(200, 50);

            btnQuery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new QueryAvailabilityGUI(logica);
                }
            });
        }
        return btnQuery;
    }


    private JButton setEditableBtn() {
        if (btnEdit == null) {
            Icon edit = new ImageIcon("images/edit.png");
            btnEdit = new JButton("Configurar perfil ", edit);
            btnEdit.setSize(200, 50);

            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new UsuarioEditGUI(logica);
                }
            });
        }
        return btnEdit;
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
