package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JList;

public class PropietarioGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropietarioGUI frame = new PropietarioGUI();
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
	public PropietarioGUI() {
		setTitle("Propietario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCuenta = new JLabel("Cuenta:");
		lblCuenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCuenta.setBounds(10, 58, 115, 14);
		contentPane.add(lblCuenta);
		
		JButton btnActualizarCuentaProp = new JButton("Actualizar Cuenta");
		btnActualizarCuentaProp.setBounds(266, 49, 158, 23);
		contentPane.add(btnActualizarCuentaProp);
		
		JButton btnAadirCasas = new JButton("A\u00D1ADIR CASAS");
		btnAadirCasas.setBounds(10, 11, 158, 41);
		contentPane.add(btnAadirCasas);
		
		JList InfPropietario = new JList();
		InfPropietario.setBounds(10, 83, 414, 89);
		contentPane.add(InfPropietario);
	}
}
