package gui;

import businessLogic.rhLogica;
import businessLogic.ruralManagerLogic;
import exceptions.UsuarioNoExiste;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by joseba on 22/2/16.
 */
public class LoginGUI extends JFrame {

    //COmponentes de la interfaz de usuario
    private JPanel mainPane, headerPane, contentPane;
    private JButton btnSignIn, btnSignUp;
    private JTextField tfMail;
    private JPasswordField pfPass;


    //Lógica de negocio de la aplicación
    private ruralManagerLogic logica;



    /**
     * Constructor
     * @param logica
     */
    LoginGUI(ruralManagerLogic logica) {
        setSize(500, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(setMainPane());
        this.logica = logica;
        this.setFocusable(true);
        keyHandler(this);
        setVisible(true);

    }

    //JPanel principal
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

    //JPanel con los campos del login
    private JPanel setContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            contentPane.add(setUserPanel());
            contentPane.add(setPassPanel());

            JPanel btnPanel = new JPanel();
            btnPanel.add(setSignUpBtn());
            btnPanel.add(setSignInBtn());
            btnPanel.setBackground(estilosGUI.bckColor);
            contentPane.add(btnPanel);
        }
        return contentPane;
    }

    //JPanel con los campos para la contraseña
    private JPanel setPassPanel() {
        JPanel passPane = new JPanel(new FlowLayout());
        passPane.setBackground(estilosGUI.bckColor);
        passPane.add(new JLabel("Contraseña"));
        pfPass = new JPasswordField(15);
        keyHandler(pfPass);
        passPane.add(pfPass);
        return passPane;
    }


    //JPanel con los campos para el correo
    private JPanel setUserPanel() {
        JPanel userPane = new JPanel(new FlowLayout());
        userPane.add(new JLabel("Correo electrónico: "));
        userPane.setBackground(estilosGUI.bckColor);
        tfMail = new JTextField(15);
        keyHandler(tfMail);
        userPane.add(tfMail);
        return userPane;
    }

    //JPanel con los botones
    private JPanel setBtnLayout() {
        JPanel btnLayout = new JPanel();

        btnLayout.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
        btnLayout.add(new JLabel("Scrum Masters  ©"));
        btnLayout.setBackground(new Color(106, 110, 124));

        return btnLayout;
    }


    //Boton para iniciar sesión y sla gestión de sus eventos
    private JButton setSignInBtn() {
        if (btnSignIn == null) {
            btnSignIn = new JButton("Iniciar sesión");
            btnSignIn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnSignIn.addActionListener(e->{
                login();
            });
        }
        return btnSignIn;
    }

    //Boton para registrarse
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

    private void keyHandler(Component c){
        c.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
               if(e.getKeyCode() == 10)//Intro
                login();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void login(){
        int acceso = 0;
        try {
            acceso = logica.checkLogin(tfMail.getText(), Arrays.toString(pfPass.getPassword()));
            new MainFrame(logica, acceso);
            setVisible(false);

        } catch (UsuarioNoExiste usuarioNoExiste) {
            System.out.println("Error: El usuario no existe");
            JOptionPane.showMessageDialog(null, "Datos incorrectos");
        }
    }

    public static void main(String args[]) {
        new LoginGUI(new rhLogica());
    }
}
