package gui;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.xml.bind.ParseConversionEvent;
import javax.swing.ButtonGroup;

public class RegistroGUI extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField InsertarNombre;
	private JTextField InsertarApellido;
	private JTextField InsertarEmail;
	private JTextField InsertarDNI;
	private JTextField InsertarTelefono;
    private JPasswordField InsertarPass;

    private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton Usuario = null;
	private JRadioButton Propietario = null;
	
	protected Component frame;
	
	
	public RegistroGUI() {
		this.setSize(671, 649);
		getContentPane().setLayout(null);
		
		//T�tulo//
		JLabel lblRegistro = new JLabel("Registro");
		lblRegistro.setFont(new Font("Segoe Print", Font.PLAIN, 65));
		lblRegistro.setBounds(167, 11, 265, 93);
		getContentPane().add(lblRegistro);
		
		//Labels//
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNombre.setBounds(133, 153, 68, 22);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblApellido.setBounds(133, 208, 68, 22);
		getContentPane().add(lblApellido);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(133, 261, 68, 22);
		getContentPane().add(lblEmail);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDni.setBounds(133, 311, 68, 22);
		getContentPane().add(lblDni);
		
		JLabel lblNTelefono = new JLabel("N\u00BA Telefono");
		lblNTelefono.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNTelefono.setBounds(133, 366, 103, 22);
		getContentPane().add(lblNTelefono);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblContrasea.setBounds(133, 422, 103, 22);
		getContentPane().add(lblContrasea);
		
		//Cajas de texto//
		InsertarNombre = new JTextField();
		InsertarNombre.setBounds(327, 153, 200, 22);
		getContentPane().add(InsertarNombre);
		InsertarNombre.setColumns(10);
		
		InsertarApellido = new JTextField();
		InsertarApellido.setColumns(10);
		InsertarApellido.setBounds(327, 208, 200, 22);
		getContentPane().add(InsertarApellido);
		
		InsertarEmail = new JTextField();
		InsertarEmail.setColumns(10);
		InsertarEmail.setBounds(327, 261, 200, 22);
		getContentPane().add(InsertarEmail);
		
		InsertarDNI = new JTextField();
		InsertarDNI.setColumns(10);
		InsertarDNI.setBounds(327, 311, 200, 22);
		getContentPane().add(InsertarDNI);
		
		InsertarTelefono = new JTextField();
		InsertarTelefono.setColumns(10);
		InsertarTelefono.setBounds(327, 366, 200, 22);
		getContentPane().add(InsertarTelefono);
		
		//Cajas de contrase�as//

        InsertarPass = new JPasswordField();
        InsertarPass.setBounds(327, 425, 200, 20);
        getContentPane().add(InsertarPass);

        //RadioButon//
		
		JRadioButton rdbtnUsuario = new JRadioButton("Usuario");
		rdbtnUsuario.setSelected(true);
		buttonGroup.add(rdbtnUsuario);
		rdbtnUsuario.setBounds(181, 479, 114, 22);
		getContentPane().add(rdbtnUsuario);
		
		JRadioButton rdbtnPropietario = new JRadioButton("Propietario");
		buttonGroup.add(rdbtnPropietario);
		rdbtnPropietario.setBounds(355, 479, 114, 22);
		getContentPane().add(rdbtnPropietario);
		
		//Bot�n//
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRegistrar.setBounds(210, 532, 200, 50);
		getContentPane().add(btnRegistrar);
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Vacio()==true){
					JOptionPane.showMessageDialog(frame,
						    "Hay algun campo incorrecto o vacio",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
//				else{
//					boolean P = isPropietario();	
//					
//					loginLogic.CrearUsuario(InsertarNombre.getText(),InsertarContrase�a.getPassword().toString(),
//											InsertarApellido.getText(), InsertarEmail.getText(), 
//											InsertarDNI.getText(), Integer.parseInt(InsertarTelefono.getText()),P);
//				}
			}
		});

        setVisible(true);
    }
	public boolean isPropietario(){
		if(Usuario.isSelected()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean Vacio(){
			boolean V = false;
			if(InsertarNombre.getText().equals("")){
				V=true;
			}
			if(InsertarApellido.getText().equals("")){
				V=true;
			}
			if(!(ValidarCorreo(InsertarEmail.getText()))){
				V=true;
			}
			if(InsertarDNI.getText().equals("")){
				V=true;
			}
			if(InsertarTelefono.getText().equals("")){
				V=true;
			}
        if (InsertarPass.getPassword().equals("")) {
            V=true;
			}
			
			return V;
			
		}



		//Funcion para validar el campo del email//
		public boolean ValidarCorreo(String hex) {
			
			String Patron = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
			
			Pattern pattern = Pattern.compile(Patron);
			Matcher matcher = pattern.matcher(hex);
			return matcher.matches();

			
		}

		
	public static void main(String[] args) {
//		new RegistroGUI().setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try{
					RegistroGUI frame = new RegistroGUI();
						frame.setVisible(true);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
