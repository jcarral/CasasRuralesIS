package gui;

import businessLogic.rhLogica;
import businessLogic.ruralManagerLogic;
import exceptions.UsuarioNoExiste;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by joseba on 22/2/16.
 */
public class LoginGUI extends JFrame {


    private JPanel mainPane, headerPane, contentPane;
    private JButton btnSignIn, btnSignUp;
    private JTextField tfMail;
    private JPasswordField pfPass;
    private JRadioButton rdbtnPropietario, rdbtnUsuario;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private ruralManagerLogic logica;

    //Constantes
    private final int USUARIO = 0, PROPIETARIO = 1;

    LoginGUI(ruralManagerLogic logica) {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(setMainPane());
        this.logica = logica;
        setVisible(true);

    }

    private JPanel setMainPane() {
        if (mainPane == null) {
            mainPane = new JPanel(new BorderLayout());
            mainPane.setBackground(estilosGUI.bckColor);

            mainPane.add(estilosGUI.setHeaderPane("Iniciar sesión"), BorderLayout.PAGE_START);
            mainPane.add(setBtnLayout(), BorderLayout.PAGE_END);
            mainPane.add(setContentPane(), BorderLayout.CENTER);
        }
        return mainPane;
    }


    private JPanel setContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(setUserPanel());
            contentPane.add(setPassPanel());
            contentPane.add(setRadioPanel());
        }
        return contentPane;
    }

    private JPanel setPassPanel() {
        JPanel passPane = new JPanel(new FlowLayout());
        passPane.setBackground(estilosGUI.bckColor);
        passPane.add(new JLabel("Contraseña"));
        pfPass = new JPasswordField(15);
        passPane.add(pfPass);
        return passPane;
    }

    private JPanel setRadioPanel() {
        JPanel radioPane = new JPanel(new FlowLayout());
        radioPane.setBackground(estilosGUI.bckColor);

        rdbtnUsuario = new JRadioButton("Usuario");
        rdbtnUsuario.setSelected(true);
        buttonGroup.add(rdbtnUsuario);
        rdbtnUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
        radioPane.add(rdbtnUsuario);

        rdbtnPropietario = new JRadioButton("Propietario");
        buttonGroup.add(rdbtnPropietario);
        rdbtnPropietario.setFont(new Font("Tahoma", Font.PLAIN, 14));
        radioPane.add(rdbtnPropietario);

        return radioPane;
    }

    private JPanel setUserPanel() {
        JPanel userPane = new JPanel(new FlowLayout());
        userPane.add(new JLabel("Correo electrónico: "));
        userPane.setBackground(estilosGUI.bckColor);
        tfMail = new JTextField(15);
        userPane.add(tfMail);
        return userPane;
    }

    private JPanel setBtnLayout() {
        JPanel btnLayout = new JPanel();
        btnLayout.setLayout(new BoxLayout(btnLayout, BoxLayout.PAGE_AXIS));
        btnLayout.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        btnLayout.add(setSignInBtn());
        btnLayout.add(setSignUpBtn());
        btnLayout.setBackground(new Color(106, 110, 124));

        return btnLayout;
    }



    private JButton setSignInBtn() {
        if (btnSignIn == null) {
            btnSignIn = new JButton("Iniciar sesión");
            btnSignIn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnSignIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int acceso = 0;
                    try {
                        acceso = logica.checkLogin(tfMail.getText(), Arrays.toString(pfPass.getPassword()), rdbtnUsuario.isSelected());

                    if (acceso == USUARIO) {
                        new UsuarioGUI(logica);
                        setVisible(false);
                    } else {
                        new PropietarioGUI(logica);
                        setVisible(false);
                    }
                    } catch (UsuarioNoExiste usuarioNoExiste) {
                        usuarioNoExiste.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Datos incorrectos");
                    }
                }
            });
        }
        return btnSignIn;
    }

    private JButton setSignUpBtn() {
        if (btnSignUp == null) {
            btnSignUp = new JButton("Registrarse");
            btnSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);

            btnSignUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new RegistroGUI(logica);

                }
            });
        }
        return btnSignUp;
    }


    public static void main(String args[]) {
        new LoginGUI(new rhLogica());
    }
}
