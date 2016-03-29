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
import javax.swing.border.MatteBorder;

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
    private JButton btnReservas;


    /**
     * COnstructor
     * @param logica
     */
    UsuarioGUI(ruralManagerLogic logica) {
        super("Zona usuarios");
        this.logica = logica;

        setSize(648, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().add(setMainPanel());
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
            Icon iLogout = new ImageIcon("images/logout.png");
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
            btnPane.add(setQueryBtn());
            btnPane.add(setEditableBtn());
            btnPane.setBackground(estilosGUI.bckColorDark);
            btnPane.setBorder(new MatteBorder(4, 0, 0, 0, (Color) new Color(0, 0, 0)));
            btnPane.add(setBtnReservas());
        }
        return btnPane;
    }

    //Boton de busqueda
    private JButton setQueryBtn() {
        if (btnQuery == null) {
            Icon edit = new ImageIcon("images/search.png");
            btnQuery = new JButton("Buscar ", edit);
            btnQuery.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new BusquedaAvanzadaGUI(logica);
                }
            });
        }
        return btnQuery;
    }

    //Boton para editar el perfil
    private JButton setEditableBtn() {
        if (btnEdit == null) {
            Icon edit = new ImageIcon("images/edit.png");
            btnEdit = new JButton("Configurar perfil ", edit);
            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new UsuarioEditGUI(logica);
                }
            });
        }
        return btnEdit;
    }
    //Boton para ver reservas
	private JButton setBtnReservas() {
		if (btnReservas == null) {
			Icon edit = new ImageIcon("images/reserve.png");
			btnReservas = new JButton("Reservas realizadas", edit);
			btnReservas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReservasUsuarioGUI(logica);
                }
            });
		}
		return btnReservas;
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
