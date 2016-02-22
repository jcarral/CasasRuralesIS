package gui;

import businessLogic.loginLogic;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField Correo;
	private JPasswordField passwordField;
    private JButton btnLogin, btnRegistro;
    private loginLogic userLogic;
    JRadioButton rdbtnPropietario, rdbtnUsuario;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    private final int PROPIETARIO = 0, USUARIO = 1;

    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
        super("Iniciar sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
        setContentPane(addContentPane());
        loginLogic = new Inicio();
        setVisible(true);
    }

    private JPanel addContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(null);

            //Label 1
            JLabel lblCorreo = new JLabel("Correo:");
            lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 15));
            lblCorreo.setBounds(10, 50, 123, 26);
            contentPane.add(lblCorreo);

            //Label 2
            JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
            lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 15));
            lblContrasea.setBounds(10, 99, 169, 26);
            contentPane.add(lblContrasea);

            contentPane.add(setCorreoField());
            contentPane.add(setPassField());
            contentPane.add(setLoginBtn());
            contentPane.add(setRegBtn());

            //RadioButton
            rdbtnUsuario = new JRadioButton("Usuario");
            buttonGroup.add(rdbtnUsuario);
            rdbtnUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
            rdbtnUsuario.setBounds(110, 171, 109, 23);
            contentPane.add(rdbtnUsuario);

            rdbtnPropietario = new JRadioButton("Propietario");
            buttonGroup.add(rdbtnPropietario);
            rdbtnPropietario.setFont(new Font("Tahoma", Font.PLAIN, 14));
            rdbtnPropietario.setBounds(244, 171, 109, 23);
            contentPane.add(rdbtnPropietario);
        }

        return contentPane;
    }

    private JButton setRegBtn() {
        if (btnRegistro == null) {
            btnRegistro = new JButton("Registro");
            btnRegistro.setBounds(320, 228, 89, 23);
            btnRegistro.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new RegistroGUI();
                    setVisible(false);
                }
            });
        }
        return btnRegistro;
    }

    private JButton setLoginBtn() {
        if (btnLogin == null) {
            btnLogin = new JButton("Login");
            btnLogin.setBounds(79, 213, 117, 38);
            btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int tipo = userLogic.checkLogin(Correo.getText(), passwordField.getText(), rdbtnPropietario.isSelected());
                    if (tipo == PROPIETARIO) {
                        new UsuarioGUI();
                        setVisible(false);
                    } else if (tipo == USUARIO) {
                        new PropietarioGUI();
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Datos incorrectos");
                    }

                }
            });

        }
        return btnLogin;
    }

    private JPasswordField setPassField() {
        if (passwordField == null) {
            passwordField = new JPasswordField();
            passwordField.setBounds(172, 99, 237, 29);
        }
        return passwordField;
    }

    private JTextField setCorreoField() {
        if (Correo == null) {
            Correo = new JTextField();
            Correo.setBounds(172, 50, 237, 29);
            Correo.setColumns(10);
        }
        return Correo;
    }
}
