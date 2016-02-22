package gui;

import businessLogic.ruralLogic;
import businessLogic.ruralManagerLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    private final Color bckColor = new Color(230, 230, 237);
    private final int USUARIO = 0, PROPIETARIO = 1;

    LoginGUI() {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(setMainPane());
        logica = new ruralLogic();
        setVisible(true);
    }

    private JPanel setMainPane() {
        if (mainPane == null) {
            mainPane = new JPanel(new BorderLayout());
            mainPane.setBackground(bckColor);

            mainPane.add(setHeaderLayout(), BorderLayout.PAGE_START);
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
        passPane.setBackground(bckColor);
        passPane.add(new JLabel("Contraseña"));
        pfPass = new JPasswordField(15);
        passPane.add(pfPass);
        return passPane;
    }

    private JPanel setRadioPanel() {
        JPanel radioPane = new JPanel(new FlowLayout());
        radioPane.setBackground(bckColor);

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
        userPane.setBackground(bckColor);
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

    private JPanel setHeaderLayout() {
        if (headerPane == null) {
            BufferedImage header;
            headerPane = new JPanel();
            headerPane.setBackground(bckColor);
            JLabel headerLabel;
            try {

                header = ImageIO.read(new File("src/images/header.png"));
                headerLabel = new JLabel(new ImageIcon(header));
            } catch (IOException e) {
                e.printStackTrace();
                headerLabel = new JLabel("Iniciar sesión");
                headerLabel.setFont(new Font("Segoe Print", Font.PLAIN, 35));

            }
            headerPane.add(headerLabel);
        }
        return headerPane;
    }

    private JButton setSignInBtn() {
        if (btnSignIn == null) {
            btnSignIn = new JButton("Iniciar sesión");
            btnSignIn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnSignIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int acceso = logica.checkLogin(tfMail.getText(), pfPass.getPassword().toString(), rdbtnUsuario.isSelected());
                    if (acceso == USUARIO) {
                        new UsuarioGUI();
                        setVisible(false);
                    } else if (acceso == PROPIETARIO) {
                        new PropietarioGUI();
                        setVisible(false);
                    } else {
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
                    new RegistroGUI();

                }
            });
        }
        return btnSignUp;
    }


    public static void main(String args[]) {
        new LoginGUI();
    }
}
