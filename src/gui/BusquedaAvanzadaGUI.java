package gui;

import businessLogic.ruralManagerLogic;
import domain.RuralHouse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BusquedaAvanzadaGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    //Constantes
    private final int NUMERO_CAMPOS = 4;

    //Componentes de la interfaz de usuario
    private JTextField insertarMinPrecio, insertarCasa, insertarCity, insertarDir;
    private JTextField insertarMaxPrecio;
    private JPanel mainPane, dataPane, listPane, reservePane;
    private JList listRH;
    private DefaultListModel listModel;
    private JScrollPane scrollPaneList;
    private JButton btnReserves;

    //Logica de negocio de la aplicación
    private ruralManagerLogic logica;

    protected Component frame;

    /**
     * Constructor
     *
     * @param logica
     */
    public BusquedaAvanzadaGUI(ruralManagerLogic logica) {
        setTitle("Busqueda Avanazada");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.logica = logica;

        add(setMainPanel());
        setVisible(true);
    }


    private JPanel setMainPanel() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

            mainPane.add(estilosGUI.setHeaderPane("Busqueda Avanzada"));

            JPanel tituloPane = new JPanel(new FlowLayout());
            JLabel infoTitulo = new JLabel("Introduce los parámetros por los que quieres buscar casa: ");
            tituloPane.add(infoTitulo);


            mainPane.add(tituloPane);
            mainPane.add(setDataPanel());
            mainPane.add(setBtnBuscarPane());
            mainPane.add(setListPanel());
            mainPane.add(setBtnReserve());

        }
        return mainPane;
    }

    /**
     * Panel con el boton para ir a reservar la cas seleccionada
     * @return
     */
    private JPanel setBtnReserve() {
        if (reservePane == null) {
            reservePane = new JPanel(new FlowLayout());
            btnReserves = new JButton("Ir a rservar la casa seleccionada");
            btnReserves.setSize(400, 20);
            btnReserves.setEnabled(false);
            btnReserves.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //new RealizarOfertaGUI(logica, listaCasas);
                }
            });
            reservePane.add(btnReserves);
        }
        return reservePane;
    }


    //Función para añadir el boton de busqueda
    private JPanel setBtnBuscarPane() {
        //Boton//
        JPanel btnBuscarPanel = new JPanel(new FlowLayout());
        Icon edit = new ImageIcon("images/search.png");
        JButton btnBuscar = new JButton("Buscar", edit);
        btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBuscar.setSize(new Dimension(500, 40));
        btnBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int max = (insertarMaxPrecio.getText().length() == 0)?4096:Integer.parseInt(insertarMaxPrecio.getText());
                int min= (insertarMinPrecio.getText().length()== 0)?0:Integer.parseInt(insertarMinPrecio.getText());

                if (min > max) {
                    System.out.println(min + " " + max);
                    JOptionPane.showMessageDialog(frame,
                            "Hay algun campo incorrecto o vacio",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    searchList(insertarCasa.getText(), insertarCity.getText(), insertarDir.getText(), min, max);

                }
            }
        });
        btnBuscarPanel.add(btnBuscar);
        return btnBuscarPanel;
    }

    //Función para buscar la casa segun los filtros
    private void searchList(String nombre, String ciudad, String direccion, int min, int max) {

        List<RuralHouse> res = logica.searchUsingFilter(nombre, ciudad, direccion, min, max);
        listModel.clear();
        for (RuralHouse rh : res)
            listModel.addElement(rh);
    }

    private JPanel setListPanel() {
        if (listPane == null) {
            listPane = new JPanel();
            listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
            listModel = new DefaultListModel();
            listRH = new JList(listModel);
            scrollPaneList = new JScrollPane(listRH);
            listPane.setBackground(estilosGUI.bckGray);
            listPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
            JLabel lbl = new JLabel("Lista de casas propias: ");
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPane.add(lbl);
            listPane.add(scrollPaneList);
            searchList(null, null, null, 0, 0); //Todas las casas al principio
            listRH.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    btnReserves.setEnabled(true);
                }
            });
        }
        return listPane;
    }

    private JPanel setDataPanel() {
        if (dataPane == null) {
            dataPane = new JPanel(new GridLayout(5, 2));
            dataPane.setBorder(new EmptyBorder(20, 20, 20, 20));
            //Fila1: Nombre casa
            JLabel lblNombre = new JLabel("Casa:");
            lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarCasa = new JTextField(10);
            dataPane.add(lblNombre);
            dataPane.add(insertarCasa);

            //Fila2: Ciudad
            JLabel lblCity = new JLabel("Ciudad:");
            lblCity.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarCity = new JTextField(10);
            dataPane.add(lblCity);
            dataPane.add(insertarCity);

            //Fila3: Direccion
            JLabel lblDir = new JLabel("Dirección: ");
            lblDir.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarDir = new JTextField(10);
            dataPane.add(lblDir);
            dataPane.add(insertarDir);

            //Fila4: Precio minimo
            JLabel lblMin = new JLabel("Precio mínimo");
            lblMin.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarMinPrecio = new JTextField(5);
            insertarMinPrecio.addFocusListener(new SoloNumeros());
            dataPane.add(lblMin);
            dataPane.add(insertarMinPrecio);

            //Fila5: Precio máximo
            JLabel lblMax = new JLabel("Precio máximo");
            lblMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarMaxPrecio = new JTextField(5);
            insertarMaxPrecio.addFocusListener(new SoloNumeros());
            dataPane.add(lblMax);
            dataPane.add(insertarMaxPrecio);
        }
        return dataPane;
    }


    //Clase para manejar los fields con números
    class SoloNumeros implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {

        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField _this = (JTextField) e.getSource();
            try {

                if(_this.getText().length() > 0)
                    Integer.parseInt(_this.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Eso no es un número, mete un número",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                _this.setText("");
            }
        }

    }
}


