package gui;

import businessLogic.ApplicationFacadeInterfaceWS;
import businessLogic.ruralManagerLogic;
import com.toedter.calendar.JCalendar;
import domain.Offer;
import domain.RuralHouse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.*;
import java.util.List;


public class QueryAvailabilityGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JTextField tfFechaInicial = new JTextField(15);
    private JLabel jLabel3 = new JLabel();
    private JTextField tfNoches = new JTextField(5);
    private JButton btnAceptar = new JButton();
    private JButton btnCancelar = new JButton();

    private JButton btnFicha;
    // Code for JCalendar
    private JCalendar calendario = new JCalendar();
    private Calendar calendarMio = null;
    private JLabel jLabel4 = new JLabel();
    private JScrollPane scrollPane = new JScrollPane();
    private JComboBox comboBox;
    private JTable table;
    private DefaultTableModel tableModel;
    private final JLabel labelNoOffers = new JLabel("");
    private String[] columnNames = new String[]{
            "Offer#", "Rural House", "First Day", "Last Day", "Price"
    };
    private JPanel mainPane, comboPane, calendarPane, detailsPane, dataPane, panelBtn;


    private ruralManagerLogic logica;
    private static configuration.ConfigXML c;
    private Vector<RuralHouse> rhs;


    public QueryAvailabilityGUI(ruralManagerLogic logica) {
        super("Buscar casas rurales");
        this.logica = logica;
        rhs = logica.getAllRuralHouses();

        setSize(500, 550);
        setResizable(false);
        setLocationRelativeTo(null);

        add(setMainPanel());

        setVisible(true);

    }


    private JPanel setMainPanel(){
        if(mainPane == null){
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

            mainPane.add(estilosGUI.setHeaderPane("Buscar casa rural"));
            mainPane.add(setComboPanel());
            mainPane.add(setCalendarPane());
            mainPane.add(setDetailsPane());
            mainPane.add(setDataPane());
            mainPane.add(setBtnPane());

        }
        return mainPane;
    }

    private JPanel setComboPanel(){

        if(comboPane == null){
            comboPane = new JPanel(new FlowLayout());

            //Label
            comboPane.add(new JLabel("Selecciona casa: "));

            //ComboBox
            comboBox = new JComboBox(rhs);
            comboBox.setModel(new DefaultComboBoxModel(rhs));
            comboPane.add(comboBox);

            //Boton Ficha
            Icon file = new ImageIcon("images/sheet.png");
            btnFicha = new JButton("Ver ficha", file);
            btnFicha.setBounds(new Rectangle(320, 22, 100, 30));
            btnFicha.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new FichaGUI((RuralHouse) comboBox.getSelectedItem());
                }
            });
            comboPane.add(btnFicha);
        }

        return comboPane;
    }

    private JPanel setCalendarPane(){

        if(calendarPane == null){
            calendarPane = new JPanel();

            //Jlabel
            calendarPane.add(new JLabel("Selecciona primer dia: "));

            //Insertar calendario
            calendarPane.add(calendario);

            calendario.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent propertychangeevent) {
                    if (propertychangeevent.getPropertyName().equals("locale")) {
                        calendario.setLocale((Locale) propertychangeevent.getNewValue());
                        DateFormat dateformat = DateFormat.getDateInstance(1, calendario.getLocale());
                        tfFechaInicial.setText(dateformat.format(calendarMio.getTime()));
                    } else if (propertychangeevent.getPropertyName().equals("calendar")) {
                        calendarMio = (Calendar) propertychangeevent.getNewValue();
                        DateFormat dateformat1 = DateFormat.getDateInstance(1, calendario.getLocale());
                        tfFechaInicial.setText(dateformat1.format(calendarMio.getTime()));
                        calendario.setCalendar(calendarMio);
                    }
                }
            });
        }
        return calendarPane;
    }

    private JPanel setDetailsPane(){

        if(detailsPane == null){
            detailsPane = new JPanel(new FlowLayout());

            //TextField para el numero de noches
            detailsPane.add(new JLabel("Numero de noches: "));
            tfNoches.setText("0");
            detailsPane.add(tfNoches);

            tfNoches.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {
                }

                public void focusLost(FocusEvent e) {
                    tfNoches_focusLost();
                }
            });

            //Fecha inicial
            tfFechaInicial.setEditable(false);
            detailsPane.add(new JLabel("Fecha inical: "));
            detailsPane.add(tfFechaInicial);


        }
        return detailsPane;
    }

    private JPanel setDataPane(){

        if(dataPane == null){
            dataPane = new JPanel();

            dataPane.add(scrollPane);

            //Tabla
            table = new JTable();
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //int i=table.getSelectedRow();
                    //int houseNumber = (int) tableModel.getValueAt(i,1);
                    //Date firstDate=new Date(((java.util.Date)tableModel.getValueAt(i,2)).getTime());
                    //Date lastDate=new Date(((java.util.Date)tableModel.getValueAt(i,3)).getTime());

                    //BookRuralHouseGUI b=new BookRuralHouseGUI(houseNumber,firstDate,lastDate);
                    //b.setVisible(true);
                }
            });


            scrollPane.setViewportView(table);
            tableModel = new DefaultTableModel(
                    null,
                    columnNames);

            table.setModel(tableModel);
            dataPane.add(labelNoOffers);
        }

        return dataPane;
    }

    private JPanel setBtnPane(){

        if(panelBtn== null){

            panelBtn = new JPanel(new FlowLayout());

            btnAceptar.setText("Aceptar");
            btnAceptar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnAceptar_actionPerformed(e);
                }
            });
            btnCancelar.setText("Cerrar");
            btnCancelar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnCancelar_actionPerformed(e);
                }
            });

            panelBtn.add(btnAceptar);
            panelBtn.add(btnCancelar);
        }
        return panelBtn;
    }


    private void btnCancelar_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }


    private void tfNoches_focusLost() {
        try {
            new Integer(tfNoches.getText());
            jLabel4.setText("");
        } catch (NumberFormatException ex) {
            jLabel4.setText("Error: Introduce a number");
        }
    }

    private Date trim(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    private void btnAceptar_actionPerformed(ActionEvent e) {
        // House object
        RuralHouse rh = (RuralHouse) comboBox.getSelectedItem();
        // First day
        //Date firstDay=new Date(calendario.getCalendar().getTime().getTime());
        //Remove the hour:minute:second:ms from the date
        Date firstDay = trim(new Date(calendario.getCalendar().getTime().getTime()));
        //firstDay=Date.valueOf(firstDay.toString());
        final long diams = 1000 * 60 * 60 * 24;
        long nights = diams * Integer.parseInt(tfNoches.getText());
        // Last day
        Date lastDay = new Date((long) (firstDay.getTime() + nights));

        try {
            //ApplicationFacadeInterfaceWS facade = MainGUI.getBusinessLogic();

            Vector<Offer> v = rh.getOffers(firstDay, lastDay);


            Enumeration<Offer> en = v.elements();
            Offer of;
            tableModel.setDataVector(null, columnNames);
            if (!en.hasMoreElements())
                labelNoOffers.setText("There are no offers at these dates");
            else {
                labelNoOffers.setText("Select an offer if you want to book");

                while (en.hasMoreElements()) {
                    of = en.nextElement();
                    System.out.println("Offer retrieved: " + of.toString());
                    Vector row = new Vector();
                    row.add(of.getOfferNumber());
                    //row.add(of.getRuralHouse().getHouseNumber());

                    // Dates are stored in db4o as java.util.Date objects instead of java.sql.Date objects
                    // They have to be converted into java.sql.Date objects before
                    Date firstDaySqlDate = new Date(of.getFirstDay().getTime());
                    Date lastDaySqlDate = new Date(of.getLastDay().getTime());
                    row.add(firstDaySqlDate);
                    row.add(lastDaySqlDate);
                    row.add(of.getPrice());


                    tableModel.addRow(row);
                }
            }


        } catch (Exception e1) {

            labelNoOffers.setText(e1.getMessage());
        }
    }
}