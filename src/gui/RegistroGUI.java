package gui;

import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
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
    private JPasswordField InsertarPassword;

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

        InsertarPassword = new JPasswordField();
        InsertarPassword.setBounds(327, 425, 200, 20);
        getContentPane().add(InsertarPassword);

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
						    "Hay algun campo vacio",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else{
					boolean P = isPropietario();

                    //ruralManagerLogic.CrearUsuario(InsertarNombre.getText(),InsertarPassword.getPassword().toString(),
                    //						InsertarApellido.getText(), InsertarEmail.getText(),
                    //						InsertarDNI.getText(), Integer.parseInt(InsertarTelefono.getText()),P);
                }
			}
		});
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
			if(InsertarEmail.getText().equals("")){
				V=true;
			}
			if(InsertarDNI.getText().equals("")){
				V=true;
			}
			if(InsertarTelefono.getText().equals("")){
				V=true;
			}
        if (InsertarPassword.getPassword().equals("")) {
            V=true;
			}
			
			return V;
			
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
