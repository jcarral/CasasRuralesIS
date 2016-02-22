package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.Font;

public class UsuarioGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UsuarioGUI frame = new UsuarioGUI();
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
	public UsuarioGUI() {
		setTitle("Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 229);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBuscarCasa = new JButton("BUSCAR CASAS");
		btnBuscarCasa.setBounds(10, 11, 158, 41);
		contentPane.add(btnBuscarCasa);
		
		JLabel lblCuenta = new JLabel("Cuenta:");
		lblCuenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCuenta.setBounds(10, 63, 78, 14);
		contentPane.add(lblCuenta);
		
		JList list = new JList();
		list.setBounds(10, 88, 414, 89);
		contentPane.add(list);
		
		JButton btnActualizarCuentaUsuario = new JButton("Actualizar Cuenta");
		btnActualizarCuentaUsuario.setBounds(266, 54, 158, 23);
		contentPane.add(btnActualizarCuentaUsuario);
	}
}
