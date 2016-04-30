package gui;

import businessLogic.ruralManagerLogic;
import domain.RuralHouse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class BusquedaAvanzadaGUI extends JPanel implements PanelCard{

    private static final long serialVersionUID = 1L;

    //Constantes
    private final int MIN_PRECIO = 0, MAX_PRECIO = 4096;

    //Componentes de la interfaz de usuario
    private JTextField insertarMinPrecio, insertarCasa, insertarCity, insertarDir, insertarHabitaciones, insertarBanios;
    private JTextField insertarMaxPrecio;
    private JPanel mainPane, dataPane, listPane, reservePane;
    private JList listRH;
    private DefaultListModel listModel;
    private JScrollPane scrollPaneList;
    private JButton btnReserves, btnFicha, btnBuscar;
    private JLabel lblCity, lblLista, lblNombre, lblMin, lblMax, lblHabitaciones, lblBanios, lblDir;

    //Logica de negocio de la aplicación
    private ruralManagerLogic logica;

    private ResourceBundle strings;

    protected Component frame;

    /**
     * Constructor
     *
     * @param logica
     */
    public BusquedaAvanzadaGUI(ruralManagerLogic logica) {

        this.logica = logica;
        MainFrame miframe = (MainFrame) SwingUtilities.getRoot(this);
        strings = MainFrame.strings;
        add(setMainPanel());

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                listModel.clear();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    public JPanel getPanel() {
        return this;
    }

    @Override
    public void reloadFields() {
        strings = MainFrame.strings;

        lblCity.setText(strings.getString("busqueda.lblCiudad"));
        lblLista.setText(strings.getString("busqueda.lblLista"));
        lblNombre.setText(strings.getString("busqueda.lblNombre"));
        lblBanios.setText(strings.getString("busqueda.lblBanios"));
        lblHabitaciones.setText(strings.getString("busqueda.lblHabitaciones"));
        lblMax.setText(strings.getString("busqueda.lblMax"));
        lblMin.setText(strings.getString("busqueda.lblMin"));
        lblDir.setText(strings.getString("busqueda.lblDir"));
        btnFicha.setText(strings.getString("busqueda.btnFicha"));
        btnReserves.setText(strings.getString("busqueda.btnReservas"));
        btnBuscar.setText(strings.getString("busqueda.btnBuscar"));

    }

    private JPanel setMainPanel() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

            JPanel tituloPane = new JPanel(new FlowLayout());
            JLabel infoTitulo = new JLabel(strings.getString("busqueda.titulo"));
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
     *
     * @return
     */
    private JPanel setBtnReserve() {
        if (reservePane == null) {
            reservePane = new JPanel(new FlowLayout());

            btnFicha = new JButton(strings.getString("busqueda.btnFicha"));
            btnFicha.setEnabled(false);
            btnFicha.addActionListener(e -> {
                RuralHouse rh = (RuralHouse) listModel.getElementAt(listRH.getSelectedIndex());
                new FichaGUI(rh);
            });

            btnReserves = new JButton(strings.getString("busqueda.btnReservas"));
            btnReserves.setSize(400, 20);
            btnReserves.setEnabled(false);
            btnReserves.addActionListener(e -> {

                        RuralHouse rh = (RuralHouse) listModel.getElementAt(listRH.getSelectedIndex());
                        Vector<RuralHouse> vectorCasa = new Vector<>();
                        vectorCasa.add(rh);
                        MainFrame frame = (MainFrame) SwingUtilities.getRoot(this);
                        Vector v = new Vector();
                        v.add(listModel.getElementAt(listRH.getSelectedIndex()));
                        frame.changeToReserves(v);

                    }
            );
            reservePane.add(btnFicha);
            reservePane.add(btnReserves);
        }
        return reservePane;
    }


    //Función para añadir el boton de busqueda
    private JPanel setBtnBuscarPane() {
        //Boton//
        JPanel btnBuscarPanel = new JPanel(new FlowLayout());
        Icon edit = new ImageIcon("images/search.png");
        btnBuscar = new JButton(strings.getString("busqueda.btnBuscar"), edit);
        btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBuscar.setPreferredSize(new Dimension(500, 30));
        btnBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBuscar.addActionListener(e -> {

            int max = (insertarMaxPrecio.getText().length() == 0) ? MAX_PRECIO : Integer.parseInt(insertarMaxPrecio.getText());
            int min = (insertarMinPrecio.getText().length() == 0) ? MIN_PRECIO : Integer.parseInt(insertarMinPrecio.getText());
            int numHabs = (insertarHabitaciones.getText().length() == 0) ? 0 : Integer.parseInt(insertarHabitaciones.getText());
            int numBan = (insertarBanios.getText().length() == 0) ? 0 : Integer.parseInt(insertarBanios.getText());

            if (min > max) {

                JOptionPane.showMessageDialog(frame,
                        strings.getString("busqueda.alert"),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                searchList(insertarCasa.getText(), insertarCity.getText(), insertarDir.getText(), min, max, numHabs, numBan);

            }

        });
        btnBuscarPanel.add(btnBuscar);
        return btnBuscarPanel;
    }

    //Función para buscar la casa segun los filtros
    private void searchList(String nombre, String ciudad, String direccion, int min, int max, int habitaciones, int banios) {

        List<RuralHouse> res = logica.searchUsingFilter(nombre, ciudad, direccion, min, max, habitaciones, banios);
        listModel.clear();
        if (res == null) {
            return;
        }
        for (RuralHouse rh : res) {
            listModel.addElement(rh);
        }

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
            lblLista = new JLabel(strings.getString("busqueda.lblLista"));
            lblLista.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPane.add(lblLista);
            listPane.add(scrollPaneList);
            searchList(null, null, null, 0, 0, 0, 0); //Todas las casas al principio
            listRH.addListSelectionListener(e -> {
                        btnReserves.setEnabled(true);
                        btnFicha.setEnabled(true);
                    }
            );
        }
        return listPane;
    }

    private JPanel setDataPanel() {
        if (dataPane == null) {

            dataPane = new JPanel(new GridLayout(7, 2));
            dataPane.setPreferredSize(new Dimension(500, 200));
            dataPane.setBorder(new EmptyBorder(20, 20, 20, 20));
            //Fila1: Nombre casa
            lblNombre = new JLabel(strings.getString("busqueda.lblNombre"));
            lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarCasa = new JTextField(10);
            dataPane.add(lblNombre);
            dataPane.add(insertarCasa);

            //Fila2: Ciudad
            lblCity = new JLabel(strings.getString("busqueda.lblCiudad"));
            lblCity.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarCity = new JTextField(10);
            dataPane.add(lblCity);
            dataPane.add(insertarCity);

            //Fila3: Direccion
            lblDir = new JLabel(strings.getString("busqueda.lblDir"));
            lblDir.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarDir = new JTextField(10);
            dataPane.add(lblDir);
            dataPane.add(insertarDir);

            //Fila4: Precio minimo
            lblMin = new JLabel(strings.getString("busqueda.lblMin"));
            lblMin.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarMinPrecio = new JTextField(5);
            insertarMinPrecio.addFocusListener(new SoloNumeros());
            dataPane.add(lblMin);
            dataPane.add(insertarMinPrecio);

            //Fila5: Precio máximo
            lblMax = new JLabel(strings.getString("busqueda.lblMax"));
            lblMax.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarMaxPrecio = new JTextField(5);
            insertarMaxPrecio.addFocusListener(new SoloNumeros());
            dataPane.add(lblMax);
            dataPane.add(insertarMaxPrecio);

            //Fila6: Numero habitaciones
            lblHabitaciones = new JLabel(strings.getString("busqueda.lblHabitaciones"));
            lblHabitaciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarHabitaciones = new JTextField(5);
            insertarHabitaciones.addFocusListener(new SoloNumeros());
            dataPane.add(lblHabitaciones);
            dataPane.add(insertarHabitaciones);

            //Fila7: Numero baños
            lblBanios = new JLabel(strings.getString("busqueda.lblBanios"));
            lblBanios.setFont(new Font("Tahoma", Font.PLAIN, 15));
            insertarBanios = new JTextField(5);
            insertarBanios.addFocusListener(new SoloNumeros());
            dataPane.add(lblBanios);
            dataPane.add(insertarBanios);
        }
        return dataPane;
    }


}


