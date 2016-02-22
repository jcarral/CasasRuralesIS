package gui;

import businessLogic.ruralManagerLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            mainPane.add(setBtnPane());

        }
        return mainPane;
    }

    private JPanel setInfoPanel() {
        if (infoPane == null) {
            infoPane = new JPanel();
            infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.PAGE_AXIS));
            String[] info = logica.getUserInfo();

            JPanel pNombre = new JPanel(new FlowLayout());
            pNombre.add(new JLabel("Nombre: "));
            tfNombre = new JTextField(info[NOMBRE], 10);
            pNombre.add(tfNombre);
            infoPane.add(pNombre);


            JPanel pApellido = new JPanel(new FlowLayout());
            pApellido.add(new JLabel("Apellido: "));
            tfApellido = new JTextField(info[APELLIDO], 10);
            pApellido.add(tfApellido);
            infoPane.add(pApellido);


            JPanel pMail = new JPanel(new FlowLayout());
            pMail.add(new JLabel("Correo: "));

            tfMail = new JTextField(info[MAIL], 20);
            pMail.add(tfMail);
            infoPane.add(pMail);


            JPanel pDNI = new JPanel(new FlowLayout());
            pDNI.add(new JLabel("DNI: "));
            tfDNI = new JTextField(info[DNI], 10);
            pDNI.add(tfDNI);
            infoPane.add(pDNI);

            JPanel pTel = new JPanel(new FlowLayout());
            pTel.add(new JLabel("Telefono: "));
            tfTel = new JTextField(info[TEL], 10);
            pTel.add(tfTel);
            infoPane.add(pTel);


            JPanel pPass1 = new JPanel(new FlowLayout());
            pPass1.add(new JLabel("Nueva password: "));
            pfPass = new JPasswordField(10);
            pPass1.add(pfPass);
            infoPane.add(pPass1);

            JPanel pPass2 = new JPanel(new FlowLayout());
            pPass2.add(new JLabel("Repite password: "));
            pfVerificarPass = new JPasswordField(10);
            pPass2.add(pfVerificarPass);
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
                    //Gestionar
                }
            });
        }
        btnPane.add(btnConfirm);
        return btnPane;

    }
}
