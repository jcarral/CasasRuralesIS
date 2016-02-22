package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.SwingConstants;

public class FichaGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FichaGUI frame = new FichaGUI();
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
	public FichaGUI() {
		setResizable(false);
		setTitle("Ficha Casa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcin.setBounds(10, 25, 200, 50);
		contentPane.add(lblDescripcin);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 61, 414, 196);
		contentPane.add(textArea);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDireccin.setBounds(10, 437, 76, 30);
		contentPane.add(lblDireccin);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(96, 442, 313, 22);
		contentPane.add(textArea_1);
		
		JLabel lblNmeroDeTelefono = new JLabel("N\u00FAmero de telefono:");
		lblNmeroDeTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNmeroDeTelefono.setBounds(10, 478, 200, 50);
		contentPane.add(lblNmeroDeTelefono);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(162, 493, 247, 22);
		contentPane.add(textArea_2);
		
		JLabel lblNombrecasa = new JLabel("Nombre_Casa");
		lblNombrecasa.setFont(new Font("Broadway", Font.PLAIN, 17));
		lblNombrecasa.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrecasa.setBounds(106, 0, 200, 50);
		contentPane.add(lblNombrecasa);
		
		JLabel lblFotos = new JLabel("Fotos:");
		lblFotos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFotos.setBounds(10, 247, 200, 50);
		contentPane.add(lblFotos);
	}
}
