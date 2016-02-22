package gui;

import businessLogic.ruralManagerLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroGUI extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField insertarNombre;
    private JTextField insertarApellido;
    private JTextField insertarEmail;
    private JTextField insertarDNI;
    private JTextField insertarTelefono;
    private JPasswordField insertarPass;

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton rdbtnUsuario = null;
    private JRadioButton rdbtnPropietario = null;

    private ruralManagerLogic logica;

    protected Component frame;


    public RegistroGUI(ruralManagerLogic logica) {
        this.setSize(671, 649);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.logica = logica;
        getContentPane().setLayout(null);

        setLabels();
        setFields();
        setRadio();
        setBtn();

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("jns");
                if (!vacio()) {

                    int res = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro que quieres descartar los cambios?", null, JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION)
                        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    else
                        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                }
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


    //Función para comprobar si hay algún campo vacio
    private boolean vacio() {
        return insertarNombre.getText().isEmpty() && insertarApellido.getText().isEmpty() && !(ValidarCorreo(insertarEmail.getText()))
                && insertarDNI.getText().isEmpty() && insertarTelefono.getText().isEmpty() && (insertarPass.getPassword().length == 0);
    }


    //Funcion para validar el campo del email//
    private boolean ValidarCorreo(String hex) {

        String Patron = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

        Pattern pattern = Pattern.compile(Patron);
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();


    }


    private void setBtn() {
        //Bot�n//
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnRegistrar.setBounds(210, 532, 200, 50);
        getContentPane().add(btnRegistrar);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (vacio()) {
                    JOptionPane.showMessageDialog(frame,
                            "Hay algun campo incorrecto o vacio",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean prop = rdbtnPropietario.isSelected();
                    logica.storeUsuario(insertarEmail.getText(), insertarPass.getPassword().toString(), insertarNombre.getText(),
                            insertarApellido.getText(), insertarDNI.getText(), Integer.parseInt(insertarTelefono.getText()), prop);
                    dispose();
                }
            }
        });
    }

    private void setRadio() {
        rdbtnUsuario = new JRadioButton("Usuario");
        rdbtnUsuario.setSelected(true);
        buttonGroup.add(rdbtnUsuario);
        rdbtnUsuario.setBounds(181, 479, 114, 22);
        getContentPane().add(rdbtnUsuario);

        rdbtnPropietario = new JRadioButton("Propietario");
        buttonGroup.add(rdbtnPropietario);
        rdbtnPropietario.setBounds(355, 479, 114, 22);
        getContentPane().add(rdbtnPropietario);
    }

    private void setFields() {
        //Cajas de texto//
        insertarNombre = new JTextField();
        insertarNombre.setBounds(327, 153, 200, 22);
        getContentPane().add(insertarNombre);
        insertarNombre.setColumns(10);

        insertarApellido = new JTextField();
        insertarApellido.setColumns(10);
        insertarApellido.setBounds(327, 208, 200, 22);
        getContentPane().add(insertarApellido);

        insertarEmail = new JTextField();
        insertarEmail.setColumns(10);
        insertarEmail.setBounds(327, 261, 200, 22);
        getContentPane().add(insertarEmail);

        insertarDNI = new JTextField();
        insertarDNI.setColumns(10);
        insertarDNI.setBounds(327, 311, 200, 22);
        getContentPane().add(insertarDNI);

        insertarTelefono = new JTextField();
        insertarTelefono.setColumns(10);
        insertarTelefono.setBounds(327, 366, 200, 22);
        getContentPane().add(insertarTelefono);

        //Cajas de contrase�as//

        insertarPass = new JPasswordField();
        insertarPass.setBounds(327, 425, 200, 20);
        getContentPane().add(insertarPass);
    }

    //Función para añadir los label
    private void setLabels() {
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
    }


}
