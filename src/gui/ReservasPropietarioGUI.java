package gui;

import businessLogic.ruralManagerLogic;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ReservasPropietarioGUI extends JFrame {

    private static final long serialVersionUID = 1L;


    //Componentes de la interfaz de usuario
    private String[] columnNames = new String[]{
           "Rural House", "First Day", "Last Day", "Price", "User"
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
    public ReservasPropietarioGUI(ruralManagerLogic logica) {
    	setTitle("Reservas Propietario");
        this.setSize(554, 322);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.logica = logica;
        getContentPane().setLayout(null);

        setLabels();
        setPane();
        fillTable();
        setVisible(true);
    }

    private void fillTable(){
        Vector<Vector<String>> res = logica.reservedRHInfo();

        for(Vector<String> v : res){
            tableModel.addRow(v);
        }
    }

    //Función para añadir los label
    private void setLabels() {
        JLabel lblRegistro = new JLabel("Reservas realizadas a sus casas:");
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
        table.setEnabled(false);
        scrollPane.setViewportView(table);
        tableModel = new DefaultTableModel(
                null,
                columnNames);

        table.setModel(tableModel);
     
    }
}