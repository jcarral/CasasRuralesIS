package gui;

import businessLogic.ruralManagerLogic;
import domain.RuralHouse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

import javax.swing.*;

public class PropietarioGUI extends JFrame {

    //Logica de la aplicación
    private ruralManagerLogic logica;

    //Componentes de la interfaz
    private JPanel mainPane, infoPane, btnPane, listJPanel;
    private JLabel lblMail, lblNombre, lblApellido;
    private JButton btnAddRh, btnAddOff, btnEdit, btnReservas;
    private JList listRH;
    private DefaultListModel listModel;
    private JScrollPane paneList;

    /**
     * Constructor de la ventana
     * @param logica
     */
    PropietarioGUI(ruralManagerLogic logica) {
        super("Zona propietarios");
        this.logica = logica;
        setSize(900, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().add(setMainPanel());
        frameEvents();

        setVisible(true);

    }

    //Panel principal
    private JPanel setMainPanel() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
            mainPane.add(estilosGUI.setHeaderPane("Zona Usuarios"));
            mainPane.add(setInfoPane());
            mainPane.add(setListPanel());
            mainPane.add(setBtnPanel());
        }
        return mainPane;
    }

    //JPanel donde se muestra la lista de casas del usuario
    private JPanel setListPanel() {
        if (listJPanel == null) {
            listJPanel = new JPanel();
            listJPanel.setLayout(new BoxLayout(listJPanel, BoxLayout.PAGE_AXIS));
            listModel = new DefaultListModel();
            listRH = new JList(listModel);
            paneList = new JScrollPane(listRH);
            listJPanel.setBackground(estilosGUI.bckGray);
            listJPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
            JLabel lbl = new JLabel("Lista de casas propias: ");
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            listJPanel.add(lbl);
            listJPanel.add(paneList);
            insertDataIntoPane();
        }
        return listJPanel;
    }

    //Función para introducir las casas del usuario en el modelo
    private void insertDataIntoPane() {
        List<RuralHouse> res = logica.getUsersRuralHouses();
        listModel.clear();
        for (RuralHouse rh : res)
            listModel.addElement(rh);

    }

    //JPanel con los botones de la intefaz de usuario
    private JPanel setBtnPanel() {
        if (btnPane == null) {
            btnPane = new JPanel(new FlowLayout());
            btnPane.add(setBtnAddRH());
            btnPane.add(setBtnAddOff());
            btnPane.add(setBtnEdit());
            btnPane.add(setBtnReservas());
            btnPane.setBackground(estilosGUI.bckColorDark);
            btnPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        }
        return btnPane;
    }

    //Boton para añadir una nueva casa
    private JButton setBtnAddRH() {
        if (btnAddRh == null) {
            Icon iCasa = new ImageIcon("images/rh.png");
            btnAddRh = new JButton("Añadir casa ", iCasa);
            btnAddRh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new NewRHGUI(logica);
                }
            });
        }
        return btnAddRh;
    }


    //Boton para editar el perfil del usuario
    private JButton setBtnEdit() {
        if (btnEdit == null) {
            Icon iEdit = new ImageIcon("images/edit.png");
            btnEdit = new JButton("Editar info ", iEdit);
            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new AjustesGUI(logica);
                }
            });
        }
        return btnEdit;
    }
    
  //Boton para ver reservas
  	private JButton setBtnReservas() {
		if (btnReservas == null) {
  			Icon edit = new ImageIcon("images/reserve.png");
  			btnReservas = new JButton("Ver reservas", edit);
  			btnReservas.setSize(50, 50);
  			btnReservas.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      new ReservasPropietarioGUI(logica);
                  }
              });
  		}
  		return btnReservas;
  	}

    //Boton para añadir un nuevo usuario
    private JButton setBtnAddOff() {
        if (btnAddOff == null) {
            Icon iOff = new ImageIcon("images/offer.png");
            btnAddOff = new JButton("Añadir oferta ", iOff);
            btnAddOff.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SetAvailabilityGUI(logica);
                }
            });
        }
        return btnAddOff;
    }

    //JPanel con la información del usuario
    private JPanel setInfoPane() {
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

    //Función para actualizar los datos del usuario
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

    //Función para que al ganar el focus se actualicen los datos en la ventana
    private void frameEvents() {
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

                updateInfoPane();
                insertDataIntoPane();

            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
}
