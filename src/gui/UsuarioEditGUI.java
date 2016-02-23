package gui;

import businessLogic.ruralManagerLogic;
import domain.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * Created by joseba on 22/2/16.
 */
public class UsuarioEditGUI extends JFrame {

    private ruralManagerLogic logica;

    private JPanel mainPane, infoPane;
    private JTextField tfNombre, tfMail, tfApellido, tfDNI, tfTel;
    private JPasswordField pfPass, pfVerificarPass;
    private JButton btnConfirm;

    private final int MAIL = 0, NOMBRE = 1, APELLIDO = 2, DNI = 3, TEL = 4;

    UsuarioEditGUI(ruralManagerLogic logica) {
        super("Configurar perfil de usuario");
        this.logica = logica;
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        add(mainPanel());
        setVisible(true);

    }

    private JPanel mainPanel() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
            mainPane.add(estilosGUI.setHeaderPane("Editar perfil"));
            mainPane.add(setInfoPanel());
            frameEvents();
            mainPane.add(setBtnPane());

        }
        return mainPane;
    }

    private JPanel setInfoPanel() {
        if (infoPane == null) {
            infoPane = new JPanel();
            infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.PAGE_AXIS));
            infoPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
            infoPane.setBackground(estilosGUI.bckGray);
            String[] info = logica.getUserInfo();

            JPanel pNombre = new JPanel(new FlowLayout());
            pNombre.add(new JLabel("Nombre: "));
            tfNombre = new JTextField(info[NOMBRE], 10);
            pNombre.add(tfNombre);
            pNombre.setBackground(estilosGUI.bckGray);
            infoPane.add(pNombre);


            JPanel pApellido = new JPanel(new FlowLayout());
            pApellido.add(new JLabel("Apellido: "));
            tfApellido = new JTextField(info[APELLIDO], 10);
            pApellido.add(tfApellido);
            pApellido.setBackground(estilosGUI.bckGray);
            infoPane.add(pApellido);


            JPanel pMail = new JPanel(new FlowLayout());
            pMail.add(new JLabel("Correo: "));
            tfMail = new JTextField(info[MAIL], 20);
            pMail.add(tfMail);
            pMail.setBackground(estilosGUI.bckGray);
            infoPane.add(pMail);


            JPanel pDNI = new JPanel(new FlowLayout());
            pDNI.add(new JLabel("DNI: "));
            tfDNI = new JTextField(info[DNI], 10);
            pDNI.add(tfDNI);
            pDNI.setBackground(estilosGUI.bckGray);
            infoPane.add(pDNI);

            JPanel pTel = new JPanel(new FlowLayout());
            pTel.add(new JLabel("Telefono: "));
            tfTel = new JTextField(info[TEL], 10);
            tfTel.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                }

                @Override
                public void focusLost(FocusEvent e) {
                    try {
                        Integer.parseInt(tfTel.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Eso no es un número de telefono correcto");
                        tfTel.setText("");
                    }
                }
            });
            pTel.add(tfTel);
            pTel.setBackground(estilosGUI.bckGray);
            infoPane.add(pTel);


            JPanel pPass1 = new JPanel(new FlowLayout());
            pPass1.add(new JLabel("Nueva password: "));
            pfPass = new JPasswordField(10);
            pPass1.add(pfPass);
            pPass1.setBackground(estilosGUI.bckGray);
            infoPane.add(pPass1);

            JPanel pPass2 = new JPanel(new FlowLayout());
            pPass2.add(new JLabel("Repite password: "));
            pfVerificarPass = new JPasswordField(10);
            pfVerificarPass.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (!Arrays.equals(pfVerificarPass.getPassword(), pfPass.getPassword())) {
                        JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                        pfVerificarPass.setText("");
                    }
                }
            });
            pPass2.add(pfVerificarPass);
            pPass2.setBackground(estilosGUI.bckGray);
            infoPane.add(pPass2);


        }
        return infoPane;
    }

    private JPanel setBtnPane() {
        JPanel btnPane = new JPanel();
        btnPane.setBackground(estilosGUI.bckColorDark);
        btnPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        if (btnConfirm == null) {
            btnConfirm = new JButton("Confirmar cambios");
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (camposLlenos()) {
                        //Actualizar propietario
                        if (!logica.updatePersona(new Usuario(tfMail.getText(), Arrays.toString(pfPass.getPassword()), tfNombre.getText(), tfApellido.getText(),
                                tfDNI.getText(), Integer.parseInt(tfTel.getText()))))
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos, intentalo más tarde");
                        else
                            dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hay campos vacios, rellenalos todos");
                    }
                }
            });
        }
        btnPane.add(btnConfirm);
        return btnPane;

    }

    private boolean camposLlenos() {
        return !tfApellido.getText().isEmpty() && !tfTel.getText().isEmpty() && estilosGUI.validarCorreo(tfMail.getText())
                && !tfNombre.getText().isEmpty() && !tfDNI.getText().isEmpty() && pfPass.getPassword().length > 0
                && pfVerificarPass.getPassword().length > 0;
    }

    private void frameEvents() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (!camposLlenos()) {

                    int res = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro que quieres descartar los cambios?", null, JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION)
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
