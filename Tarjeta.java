package pagos;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import domain.RuralHouse;
import businessLogic.ReservaManagerLogic;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

public class Tarjeta extends JFrame{
	private JTextField tfMes;
	private JTextField tfAño;
	private JTextField tfCVC2;
	private JTextField tFtarjeta;
	
	public Tarjeta(String p, RuralHouse rh, String inicio, String fin) {
		getContentPane().setLayout(null);
		
		JLabel puntitos1 = new JLabel("..........................................................................................");
		puntitos1.setBounds(10, 77, 367, 14);
		getContentPane().add(puntitos1);
		
		JLabel lblDatosDeLa = new JLabel("Datos de la compra");
		lblDatosDeLa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDatosDeLa.setBounds(10, 102, 139, 14);
		getContentPane().add(lblDatosDeLa);
		
		JLabel lblImporte = new JLabel("Importe:");
		lblImporte.setBounds(10, 145, 72, 14);
		getContentPane().add(lblImporte);
		
		JLabel LbImporte = new JLabel(p);
		LbImporte.setBounds(109, 145, 197, 14);
		getContentPane().add(LbImporte);
		
		JLabel lblCasaRural = new JLabel("Casa Rural:");
		lblCasaRural.setBounds(10, 170, 72, 14);
		getContentPane().add(lblCasaRural);		
		
		JLabel lbCasa= new JLabel(rh.getNombre());
		lbCasa.setBounds(109, 170, 197, 14);
		getContentPane().add(lbCasa);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 195, 72, 14);
		getContentPane().add(lblFecha);
		
		JLabel lbFecha = new JLabel("Desde "+inicio+" hasta "+fin);
		lbFecha.setBounds(109, 195, 197, 14);
		getContentPane().add(lbFecha);
		
		JLabel lblPagoConTarjeta = new JLabel("Pago con tarjeta");
		lblPagoConTarjeta.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPagoConTarjeta.setBounds(10, 232, 139, 14);
		getContentPane().add(lblPagoConTarjeta);
		
		JLabel lblNTarjeta = new JLabel("N\u00BA Tarjeta:");
		lblNTarjeta.setBounds(10, 275, 72, 14);
		getContentPane().add(lblNTarjeta);
		
		tFtarjeta = new JTextField();
		tFtarjeta.setBounds(109, 270, 146, 25);
		getContentPane().add(tFtarjeta);
		tFtarjeta.setColumns(10);
		
		JLabel lblCaducidad = new JLabel("Caducidad:");
		lblCaducidad.setBounds(10, 316, 72, 14);
		getContentPane().add(lblCaducidad);
		
		JLabel lblMes = new JLabel("Mes:");
		lblMes.setBounds(109, 316, 46, 14);
		getContentPane().add(lblMes);
		
		tfMes = new JTextField();
		tfMes.setBounds(153, 310, 54, 26);
		getContentPane().add(tfMes);
		tfMes.setColumns(10);
		
		JLabel lblAño = new JLabel("A\u00F1o:");
		lblAño.setBounds(217, 316, 46, 14);
		getContentPane().add(lblAño);
		
		tfAño = new JTextField();
		tfAño.setBounds(252, 310, 54, 26);
		getContentPane().add(tfAño);
		tfAño.setColumns(10);
		
		JLabel lblCvc = new JLabel("CVC2");
		lblCvc.setBounds(10, 357, 46, 14);
		getContentPane().add(lblCvc);
		
		tfCVC2 = new JTextField();
		tfCVC2.setBounds(109, 351, 54, 26);
		getContentPane().add(tfCVC2);
		tfCVC2.setColumns(10);


		
		JButton btnPagar = new JButton("Pagar");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//aqui hay que llamar a guardar
			}
		});
		btnPagar.setBounds(10, 407, 89, 26);
		getContentPane().add(btnPagar);
		

		
		JLabel puntitos2 = new JLabel("..........................................................................................");
		puntitos2.setBounds(10, 460, 367, 14);
		getContentPane().add(puntitos2);
	}
}
