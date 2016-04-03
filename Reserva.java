package gui;

import javax.swing.JFrame;

import java.awt.Component;
import java.awt.TextArea;
import java.awt.Label;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.WindowConstants;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import domain.RuralHouse;
import businessLogic.ruralManagerLogic;
import exceptions.UsuarioRepetido;
import pagos.Tarjeta;

public class Reserva extends JFrame {
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtNmeroDeDocumento;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnVisa;
	private JRadioButton rdbtnMastercard;
	private JRadioButton rdbtnPayPal;
	
	private final int NUMERO_CAMPOS = 3;
	protected Component frame;
	
	private Tarjeta tarjeta;
	//private PayPal paypal;
	
	public Reserva(RuralHouse rural,String firstDate, String lastDate, String price) {
		setResizable(false);
		setBounds(500, 500, 500, 500);
		RuralHouse rh = rural;
		String fd = firstDate;
		String ld = lastDate;
		String p = price;
		
		getContentPane().setLayout(null);
        setLabels();
        setFields();
        setRadio();
        setBtn(rh, p, fd, ld);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

                if (numeroCamposLibres() < NUMERO_CAMPOS) {

                    int res = JOptionPane.showConfirmDialog(null,
                            "Estás seguro que quieres descartar los cambios?", null, JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION)
                        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    else
                        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                } else
                    dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }

        });
        setVisible(true);

	}
	private void setLabels(){
		JLabel lblNombre = new JLabel("Nombre *");
		lblNombre.setBounds(37, 55, 46, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellidos = new JLabel("Apellidos *");
		lblApellidos.setBounds(37, 94, 58, 14);
		getContentPane().add(lblApellidos);
		
		JLabel lblTipoDeDocumento = new JLabel("Tipo de documento *");
		lblTipoDeDocumento.setBounds(37, 143, 98, 14);
		getContentPane().add(lblTipoDeDocumento);
	}
	
	private void setFields(){
		txtNombre = new JTextField();
		txtNombre.setText("Nombre");
		txtNombre.setBounds(184, 52, 225, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellidos = new JTextField();
		txtApellidos.setText("Apellidos");
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(184, 91, 225, 20);
		getContentPane().add(txtApellidos);
	

		
		txtNmeroDeDocumento = new JTextField();
		txtNmeroDeDocumento.setText("N\u00FAmero de documento");
		txtNmeroDeDocumento.setBounds(276, 143, 133, 20);
		getContentPane().add(txtNmeroDeDocumento);
		txtNmeroDeDocumento.setColumns(10);
	}
	private void setJList(){
		JList list = new JList();
		list.setBounds(185, 143, 80, 20);
		getContentPane().add(list);
	}
	
	private void setCheckbox(){
		Checkbox checkbox = new Checkbox("Tengo alg\u00FAn tipo de descuento");
		checkbox.setBounds(37, 198, 177, 22);
		getContentPane().add(checkbox);
	}
	
	private void setRadio(){
		JRadioButton rdbtnVisa = new JRadioButton("Visa");
		rdbtnVisa.setSelected(true);
		buttonGroup.add(rdbtnVisa);
		rdbtnVisa.setBounds(37, 299, 98, 20);
		getContentPane().add(rdbtnVisa);
		
		JRadioButton rdbtnMastercar = new JRadioButton("MasterCar");
		buttonGroup.add(rdbtnMastercar);
		rdbtnMastercar.setBounds(167, 299, 98, 20);
		getContentPane().add(rdbtnMastercar);
		
		JRadioButton rdbtnPayPal = new JRadioButton("Pay Pal");
		buttonGroup.add(rdbtnPayPal);
		rdbtnPayPal.setBounds(311, 299, 98, 20);
		getContentPane().add(rdbtnPayPal);
	}
	
	private void setBtn(final RuralHouse rural, final String price, final String fechainicio, final String fechafin){
	
		JButton btnReservar = new JButton("Reservar");
		btnReservar.setBounds(176, 352, 89, 23);
		getContentPane().add(btnReservar);
		btnReservar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (numeroCamposLibres() > 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Rellene todos los campos por favor",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                   
                    try {
//                    	boolean vis = rdbtnVisa.isSelected();
//                    	boolean mas = rdbtnMastercard.isSelected();
//                    	boolean pay = rdbtnPayPal.isSelected();
                		String p = price;
                		RuralHouse rh = rural;
                		String inicio= fechainicio;
                		String fin= fechafin;
                		
                        if(true){
                        	Tarjeta t = new Tarjeta(p, rh, inicio, fin);
                        	t.setVisible(true);
						}else {
					//		new PayPal();
						}
                        dispose();
                    } catch (Error e) {
                        JOptionPane.showMessageDialog(null, "Se ha producido un error, intentelo de nuevo");
                    }

                }
            }
        });
	}
	
    private int numeroCamposLibres() {
        int num = 0;
        if (txtNombre.getText().isEmpty())
            num++;
        if (txtApellidos.getText().isEmpty())
            num++;
        if (txtNmeroDeDocumento.getText().isEmpty())
            num++;
        return num;
    }
}
