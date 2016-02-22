package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField Correo;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCorreo.setBounds(10, 50, 123, 26);
		contentPane.add(lblCorreo);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblContrasea.setBounds(10, 99, 169, 26);
		contentPane.add(lblContrasea);
		
		Correo = new JTextField();
		Correo.setBounds(172, 50, 237, 29);
		contentPane.add(Correo);
		Correo.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(172, 99, 237, 29);
		contentPane.add(passwordField);
		
		JRadioButton rdbtnUsuario = new JRadioButton("Usuario");
		buttonGroup.add(rdbtnUsuario);
		rdbtnUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnUsuario.setBounds(110, 171, 109, 23);
		contentPane.add(rdbtnUsuario);
		
		JRadioButton rdbtnPropietario = new JRadioButton("Propietario");
		buttonGroup.add(rdbtnPropietario);
		rdbtnPropietario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnPropietario.setBounds(244, 171, 109, 23);
		contentPane.add(rdbtnPropietario);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(79, 213, 117, 38);
		contentPane.add(btnLogin);
		
		JButton btnRegistro = new JButton("Registro");
		btnRegistro.setBounds(320, 228, 89, 23);
		contentPane.add(btnRegistro);
	}
}
