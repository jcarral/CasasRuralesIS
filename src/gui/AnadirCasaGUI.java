package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AñadirCasaGUI extends JFrame {

	private JPanel contentPane;
	private JTextField NombreCasa;
	private JTextField CiudadCasa;
	private JTextField TelefonoCasa;
	private JTextField DireccionCasa;
	private JTextField DescripcionCasa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AñadirCasaGUI frame = new AñadirCasaGUI();
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
	public AñadirCasaGUI() {
		setTitle("A\u00F1adir Casa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(10, 39, 78, 14);
		contentPane.add(lblNombre);
		
		JLabel lblCiudad = new JLabel("Ciudad:");
		lblCiudad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiudad.setBounds(10, 89, 55, 19);
		contentPane.add(lblCiudad);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDireccin.setBounds(10, 136, 112, 14);
		contentPane.add(lblDireccin);
		
		JLabel lblNmeroTelefono = new JLabel("N\u00FAmero telefono:");
		lblNmeroTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNmeroTelefono.setBounds(10, 190, 136, 14);
		contentPane.add(lblNmeroTelefono);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcin.setBounds(10, 241, 91, 14);
		contentPane.add(lblDescripcin);
		
		NombreCasa = new JTextField();
		NombreCasa.setBounds(98, 38, 326, 20);
		contentPane.add(NombreCasa);
		NombreCasa.setColumns(10);
		
		CiudadCasa = new JTextField();
		CiudadCasa.setBounds(98, 88, 326, 20);
		contentPane.add(CiudadCasa);
		CiudadCasa.setColumns(10);
		
		TelefonoCasa = new JTextField();
		TelefonoCasa.setBounds(153, 189, 271, 20);
		contentPane.add(TelefonoCasa);
		TelefonoCasa.setColumns(10);
		
		DireccionCasa = new JTextField();
		DireccionCasa.setBounds(98, 135, 326, 20);
		contentPane.add(DireccionCasa);
		DireccionCasa.setColumns(10);
		
		DescripcionCasa = new JTextField();
		DescripcionCasa.setBounds(125, 241, 299, 116);
		contentPane.add(DescripcionCasa);
		DescripcionCasa.setColumns(10);
		
		JButton btnAadirCasa = new JButton("A\u00D1ADIR CASA");
		btnAadirCasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAadirCasa.setBounds(167, 418, 112, 41);
		contentPane.add(btnAadirCasa);
		
		JButton btnAadirFotos = new JButton("A\u00F1adir fotos");
		btnAadirFotos.setBounds(123, 364, 100, 23);
		contentPane.add(btnAadirFotos);
	}

}
