package gui;

import businessLogic.ruralManagerLogic;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ReservasUsuarioGUI extends JFrame {

    private static final long serialVersionUID = 1L;


    //Componentes de la interfaz de usuario
    private String[] columnNames = new String[]{
           "Rural House", "First Day", "Last Day", "Price"
    };
    private JTable table;
    private DefaultTableModel tableModel;


    //Logica de negocio de la aplicación
    private ruralManagerLogic logica;

    protected Component frame;

    /**
     * Constructor
     * @param logica
     */
    public ReservasUsuarioGUI(ruralManagerLogic logica) {
    	setTitle("Reservas Usuario");
        this.setSize(554, 322);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.logica = logica;
        getContentPane().setLayout(null);

        setLabels();
        setPane();
        setVisible(true);
    }

    //Función para añadir los label
    private void setLabels() {
        JLabel lblRegistro = new JLabel("Reservas realizadas por usted:");
        lblRegistro.setFont(new Font("Dialog", Font.BOLD, 14));
        lblRegistro.setBounds(10, 11, 265, 19);
        getContentPane().add(lblRegistro);
    }
    
    private void setPane(){
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 41, 528, 250);
        getContentPane().add(scrollPane);
        
      //Tabla
        table = new JTable();
        scrollPane.setViewportView(table);
        tableModel = new DefaultTableModel(
                null,
                columnNames);

        table.setModel(tableModel);
     
    }
}