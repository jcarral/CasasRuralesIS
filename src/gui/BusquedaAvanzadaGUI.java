package gui;

import businessLogic.ruralManagerLogic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BusquedaAvanzadaGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    //Constantes
    private final int NUMERO_CAMPOS = 4;

    //Componentes de la interfaz de usuario
    private JTextField insertarCasa;
    private JTextField insertarCiudad;
    private JTextField insertarMinPrecio;
    private JTextField insertarMaxPrecio;

    
    //Logica de negocio de la aplicación
    private ruralManagerLogic logica;

    protected Component frame;

    /**
     * Constructor
     * @param logica
     */
    public BusquedaAvanzadaGUI(ruralManagerLogic logica) {
    	setTitle("Busqueda Avanazada");
        this.setSize(388, 429);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.logica = logica;
        getContentPane().setLayout(null);

        setLabels();
        setFields();
        setBtn();

        setVisible(true);
    }
    //Función para añadir el boton de busqueda
    private void setBtn() {
        //Boton//
    	Icon edit = new ImageIcon("images/search.png");
        JButton btnBuscar = new JButton("Buscar",edit);
        btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBuscar.setBounds(109, 331, 138, 40);
        getContentPane().add(btnBuscar);


        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (insertarMinPrecio.getText().compareTo(insertarMaxPrecio.getText())>0 ) {
                    JOptionPane.showMessageDialog(frame,
                            "Hay algun campo incorrecto o vacio",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    //TODO: Arregla esto tio que aquí llevas siempre al mismo sitio, tienes que enviar al usuario una lista de casas que cumplan los requisitos

                	 new QueryAvailabilityGUI(logica);
                }
            }
        });
    }
    
    //Función para añadir los textfields
    private void setFields() {
        //Cajas de texto//
        insertarCasa = new JTextField();
        insertarCasa.setBounds(159, 163, 200, 22);
        getContentPane().add(insertarCasa);
        insertarCasa.setColumns(10);

        insertarCiudad = new JTextField();
        insertarCiudad.setColumns(10);
        insertarCiudad.setBounds(159, 196, 200, 22);
        getContentPane().add(insertarCiudad);
        
        insertarMinPrecio = new JTextField();
        insertarMinPrecio.setBounds(159, 230, 68, 20);
        getContentPane().add(insertarMinPrecio);
        insertarMinPrecio.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Integer.parseInt(insertarMinPrecio.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Eso no es un número, mete un número",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        insertarMaxPrecio = new JTextField();
        insertarMaxPrecio.setBounds(159, 263, 68, 20);
        getContentPane().add(insertarMaxPrecio);
        insertarMaxPrecio.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Integer.parseInt(insertarMaxPrecio.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Eso no es un número, mete un número",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //Función para añadir los label
    private void setLabels() {
        JLabel lblRegistro = new JLabel("Completa los campos para su busqueda:");
        lblRegistro.setFont(new Font("Dialog", Font.BOLD, 17));
        lblRegistro.setBounds(10, 78, 338, 93);
        getContentPane().add(lblRegistro);

        //Labels//
        JLabel lblNombre = new JLabel("Casa:");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNombre.setBounds(20, 161, 68, 22);
        getContentPane().add(lblNombre);

        JLabel lblApellido = new JLabel("Ciudad:");
        lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblApellido.setBounds(20, 194, 68, 22);
        getContentPane().add(lblApellido);

        JLabel lblEmail = new JLabel("Minimo precio:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmail.setBounds(20, 227, 93, 22);
        getContentPane().add(lblEmail);

        JLabel lblDni = new JLabel("Maximo precio:");
        lblDni.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblDni.setBounds(20, 260, 110, 22);
        getContentPane().add(lblDni);

    }
    
}
