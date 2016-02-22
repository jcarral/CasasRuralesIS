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
import javax.swing.JComboBox;

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
		setResizable(false);
		setTitle("Propietario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 354);
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
		InfPropietario.setBounds(10, 83, 439, 115);
		contentPane.add(InfPropietario);
		
		JLabel lblListaDeCasas = new JLabel("Lista de casas:");
		lblListaDeCasas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblListaDeCasas.setBounds(10, 190, 200, 50);
		contentPane.add(lblListaDeCasas);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 236, -11, 22);
		contentPane.add(textArea);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 251, 439, 41);
		contentPane.add(comboBox);
	}
}
