package gui;

import businessLogic.ruralManagerLogic;
import exceptions.UsuarioNoExiste;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * Created by joseba on 22/2/16.
 */
public class AjustesGUI extends JPanel {

    //Constantes
    private final int NUMERO_CAMPOS = 4;

    //Lógica de negocio de la aplicación
    private ruralManagerLogic logica;

    //Componentes de la interfaz de usuario
    private JPanel mainPane, infoPane;
    private JTextField tfNombre, tfMail, tfApellido, tfDNI, tfTel;
    private JPasswordField pfPass, pfVerificarPass;
    private JButton btnConfirm;

    /**
     * Constructor
     * @param logica
     */
    AjustesGUI(ruralManagerLogic logica) {

        this.logica = logica;
        setPreferredSize(new Dimension(520, 400));
        setBackground(new Color(237, 237, 237));
        add(mainPanel());
        setVisible(true);

    }

    public JPanel getPanel(){
        return this;
    }

    //JPanel principal
    private JPanel mainPanel() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
            mainPane.add(setInfoPanel());
            mainPane.add(setBtnPane());
            mainPane.setBackground(new Color(237, 237, 237));

        }
        return mainPane;
    }

    //JPanel con los campos que tienen la información
    private JPanel setInfoPanel() {
        if (infoPane == null) {
            infoPane = new JPanel();
            infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.PAGE_AXIS));
            infoPane.setPreferredSize(new Dimension(520, 400));

            String[] info = logica.getUserInfo();

            JPanel pNombre = new JPanel(new FlowLayout());
            pNombre.add(new JLabel("Nombre: "));
            tfNombre = new JTextField(info[estilosGUI.NOMBRE], 10);
            pNombre.add(tfNombre);
            pNombre.setBackground(estilosGUI.bckGray);
            infoPane.add(pNombre);


            JPanel pApellido = new JPanel(new FlowLayout());
            pApellido.add(new JLabel("Apellido: "));
            tfApellido = new JTextField(info[estilosGUI.APELLIDO], 10);
            pApellido.add(tfApellido);
            pApellido.setBackground(estilosGUI.bckGray);
            infoPane.add(pApellido);


            JPanel pMail = new JPanel(new FlowLayout());
            pMail.add(new JLabel("Correo: "));
            tfMail = new JTextField(info[estilosGUI.MAIL], 20);
            tfMail.setEditable(false);
            pMail.add(tfMail);
            pMail.setBackground(estilosGUI.bckGray);
            infoPane.add(pMail);


            JPanel pDNI = new JPanel(new FlowLayout());
            pDNI.add(new JLabel("DNI: "));
            tfDNI = new JTextField(info[estilosGUI.DNI], 10);
            pDNI.add(tfDNI);
            pDNI.setBackground(estilosGUI.bckGray);
            infoPane.add(pDNI);

            JPanel pTel = new JPanel(new FlowLayout());
            pTel.add(new JLabel("Telefono: "));
            tfTel = new JTextField(info[estilosGUI.TEL], 10);
            tfTel.addFocusListener(new SoloNumeros());
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

    //JPanel con el boton para confirmar los cambios
    private JPanel setBtnPane() {
        JPanel btnPane = new JPanel();
        btnPane.setBackground(new Color(237, 237, 237));
        if (btnConfirm == null) {
            btnConfirm = new JButton("Confirmar cambios");
            btnConfirm.addActionListener(e-> {

                                                 if (numeroCamposLibres() == 0) {
                                                     //Actualizar propietario
                                                     try {
                                                         logica.updatePersona(tfMail.getText(), Arrays.toString(pfPass.getPassword()), tfNombre.getText(), tfApellido.getText(),
                                                                 tfDNI.getText(), Integer.parseInt(tfTel.getText()));
                                                         JOptionPane.showMessageDialog(null, "Se ha modificado el perfil correctamente");

                                                     } catch (UsuarioNoExiste ex) {
                                                         JOptionPane.showMessageDialog(null, "Error al actualizar los datos, el usuario no existe");
                                                     } catch (Exception ex1) {
                                                         ex1.printStackTrace();
                                                         JOptionPane.showMessageDialog(null, "Error al actualizar los datos, intentalo más tarde");
                                                     }

                                                 } else {
                                                     JOptionPane.showMessageDialog(null, "Por favor, rellena todos los campos");
                                                 }
                                             }


            );
        }
        btnConfirm.setBackground(new Color(88, 147, 145));

        btnPane.add(btnConfirm);
        return btnPane;

    }

    //Función para gestionar los eventos del frame
    //Se gestiona el cierre de la ventana


    private int numeroCamposLibres() {
        int num = 0;
        if (tfDNI.getText().isEmpty())
            num++;
        if (tfTel.getText().isEmpty())
            num++;
        if (tfNombre.getText().isEmpty())
            num++;
        if (tfApellido.getText().isEmpty())
            num++;
        if(pfPass.getPassword().length == 0)
            num++;
        if(pfVerificarPass.getPassword().length == 0)
            num++;

        return num;
    }
}
