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

    //Lógica de negocio de la aplicación
    private ruralManagerLogic logica;

    //Componentesb de la interfaz de usuario
    private JPanel mainPane, infoPane, btnPane;
    private JLabel lblNombre, lblMail, lblApellido;
    private JButton btnEdit, btnQuery;


    /**
     * COnstructor
     * @param logica
     */
    UsuarioGUI(ruralManagerLogic logica) {
        super("Zona usuarios");
        this.logica = logica;

        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(setMainPanel());
        frameEvents();

        setVisible(true);
    }

    //JPanel principal
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

    //JPanel con la información del usuario
    private JPanel setInfoPanel() {
        if (infoPane == null) {
            updateInfoPane();
            Icon iLogout = new ImageIcon("/images/logout.png");
            JButton btnLogout = new JButton(iLogout);
            btnLogout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new LoginGUI(logica);
                    dispose();
                }
            });
            infoPane.add(btnLogout);
        }

        return infoPane;
    }

    //Funcion para actualizar la información del usuario
    private void updateInfoPane() {
        infoPane = new JPanel(new FlowLayout());

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

    //JPanel con los botones de la interfaz
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

    //Boton de busqueda
    private JButton setQueryBtn() {
        if (btnQuery == null) {
            Icon edit = new ImageIcon("/images/search.png");
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

    //Boton para editar el perfil
    private JButton setEditableBtn() {
        if (btnEdit == null) {
            Icon edit = new ImageIcon("/images/edit.png");
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

    //Función para gestionar los eventos del frame
    //Gestiona cuando la ventana obtiene el foco
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
