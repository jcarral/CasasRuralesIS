package gui;

import businessLogic.ruralManagerLogic;
import com.toedter.calendar.JCalendar;
import domain.Offer;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.*;
import java.util.List;


public class SetAvailabilityGUI extends JPanel implements PanelCard{
    private static final long serialVersionUID = 1L;

    private JComboBox jComboBox1;
    private JLabel lblListaCasas = new JLabel();
    private JLabel lblPrimerDia = new JLabel();
    private JTextField jTextField1 = new JTextField(5);
    private JLabel lblUltimoDia = new JLabel();
    private JTextField jTextField2 = new JTextField(5);
    private JLabel lblPrecio = new JLabel();
    private JTextField tfPrice = new JTextField(5);
    private JButton btnAceptar = new JButton();
    private DefaultComboBoxModel comboModel;

    // Code for JCalendar
    private JCalendar calInicio = new JCalendar();
    private JCalendar calFin = new JCalendar();
    private Calendar calendarInicio = null;
    private Calendar calendarFin = null;
    private JButton btnCancelar = new JButton();
    private JLabel jLabel5 = new JLabel();
    private final JLabel lblNewLabel = new JLabel("");

    //Logica de negocio
    ruralManagerLogic logica;

    public SetAvailabilityGUI(ruralManagerLogic logica) {

        this.logica = logica;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(new Dimension(450, 400));
        try {
            Vector<RuralHouse> v = new Vector(logica.getUsersRuralHouses());
            jbInit(v);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                comboModel.removeAllElements();
                tfPrice.setText("");
                List<RuralHouse> res = logica.getAllRuralHouses();
                for (RuralHouse rh : res)
                    comboModel.addElement(rh);
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }


    public JPanel getPanel() {
        return this;
    }

    private void jbInit(Vector<RuralHouse> v) throws Exception {

        this.setPreferredSize(new Dimension(500, 453));

        jComboBox1 = new JComboBox();
        comboModel = new DefaultComboBoxModel(v);
        jComboBox1.setModel(comboModel);
        lblListaCasas.setText("List of houses:");

        jLabel5.setForeground(Color.red);
        this.add(setCalendarPanel());
        this.add(jLabel5, null);
        this.add(setPanelData());


        this.add(lblListaCasas, null);
        this.add(jComboBox1, null);
        this.add(setBtnPanel());

        this.add(lblNewLabel);
    }

    private JPanel setBtnPanel() {
        JPanel panelBtn = new JPanel();
        //Boton aceptar
        btnAceptar.setText("Aceptar");

        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAceptar_actionPerformed(e);
            }
        });

        //Boton cancelar
        btnCancelar.setText("Cancelar");

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCancelar_actionPerformed(e);
            }
        });

        panelBtn.add(btnAceptar);
        panelBtn.add(btnCancelar);
        return panelBtn;
    }

    private JPanel setCalendarPanel() {
        JPanel panelCalendar = new JPanel();

        calInicio.setPreferredSize(new Dimension(200, 200));
        calFin.setPreferredSize(new Dimension(200, 200));
        // Code for  JCalendar
        this.calInicio.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent propertychangeevent) {
                if (propertychangeevent.getPropertyName().equals("locale")) {
                    calInicio.setLocale((Locale) propertychangeevent.getNewValue());
                    DateFormat dateformat = DateFormat.getDateInstance(1, calInicio.getLocale());
                    jTextField1.setText(dateformat.format(calendarInicio.getTime()));
                } else if (propertychangeevent.getPropertyName().equals("calendar")) {
                    calendarInicio = (Calendar) propertychangeevent.getNewValue();
                    DateFormat dateformat1 = DateFormat.getDateInstance(1, calInicio.getLocale());
                    jTextField1.setText(dateformat1.format(calendarInicio.getTime()));
                    calInicio.setCalendar(calendarInicio);
                }
            }
        });

        this.calFin.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent propertychangeevent) {
                if (propertychangeevent.getPropertyName().equals("locale")) {
                    calFin.setLocale((Locale) propertychangeevent.getNewValue());
                    DateFormat dateformat = DateFormat.getDateInstance(1, calFin.getLocale());
                    jTextField2.setText(dateformat.format(calendarFin.getTime()));
                } else if (propertychangeevent.getPropertyName().equals("calendar")) {
                    calendarFin = (Calendar) propertychangeevent.getNewValue();
                    DateFormat dateformat1 = DateFormat.getDateInstance(1, calFin.getLocale());
                    jTextField2.setText(dateformat1.format(calendarFin.getTime()));
                    calFin.setCalendar(calendarFin);
                }
            }
        });

        panelCalendar.add(calInicio);
        panelCalendar.add(calFin);
        return panelCalendar;

    }

    private JPanel setPanelData() {
        JPanel panelData = new JPanel(new GridLayout(3, 2));

        panelData.setPreferredSize(new Dimension(400, 75));
        lblPrimerDia.setText("Primer dia :");
        jTextField1.setEditable(false);
        lblUltimoDia.setText("Ultimo dia :");

        jTextField2.setEditable(false);
        lblPrecio.setText("Precio:");

        tfPrice.setText("0");

        tfPrice.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                tfPrice_focusLost();
            }
        });

        panelData.add(lblPrimerDia);
        panelData.add(jTextField1);
        panelData.add(lblUltimoDia);
        panelData.add(jTextField2);
        panelData.add(lblPrecio);
        panelData.add(tfPrice);

        return panelData;
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
        RuralHouse ruralHouse = ((RuralHouse) comboModel.getSelectedItem());

        // The next instruction creates a java.util.Date object from the date selected in the JCalendar object
        // and removes the hour, minute, second and ms from the date

        Date firstDay = trim(new Date(calInicio.getCalendar().getTime().getTime()));

        Date lastDay = trim(new Date(calFin.getCalendar().getTime().getTime()));

        try {

            //It could be to trigger an exception if the introduced string is not a number
            float price = Float.parseFloat(tfPrice.getText());
            Offer o = logica.createOffer(ruralHouse, firstDay, lastDay, price);
            if (o == null)
                jLabel5.setText("Bad dates or there exists an overlapping offer");
            else {
                JOptionPane.showMessageDialog(null, "Oferta creada correctamente");
                this.setVisible(false);
            }

        } catch (java.lang.NumberFormatException e1) {
            jLabel5.setText(tfPrice.getText() + " is not a valid price");
        } catch (OverlappingOfferExists e1) {
            jLabel5.setText("There exists an overlapping offer");
        } catch (BadDates e1) {
            jLabel5.setText("Last day is before first day in the offer");

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void btnCancelar_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    private void tfPrice_focusLost() {
        try {
            new Integer(tfPrice.getText());
            jLabel5.setText("");
        } catch (NumberFormatException ex) {
            jLabel5.setText("Error: Introduce a number");
        }
    }
}