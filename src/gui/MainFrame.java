package gui;

import businessLogic.ruralManagerLogic;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import domain.Offer;
import domain.Reserva;
import domain.RuralHouse;
import exceptions.OfertaNoExiste;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainFrame extends JFrame {

    //UI components
    private JMenuBar menuBar;
    private JPanel contentPanel;
    private JButton[] botonesMenu = new JButton[7];
    private JButton btntabla, btnMisOfertas;
    private DefaultListModel model;
    private JLabel lblBienvenido;

    //Cards panels
    private BusquedaAvanzadaGUI bus;
    private AjustesGUI edit;
    private SetAvailabilityGUI addOferta;

    //Atributos clase
    private ruralManagerLogic logica;
    private int tipo;
    private ReservarGUI reserva;
    private NewRHGUI newRh;

    public static ResourceBundle strings;
    private HashMap<String, Locale> idiomas;

    //Constantes
    private final int INICIO = 0, BUSCAR = 1, RESERVAR = 2, ADDCASA = 3, ADDOFFER = 4, AJUSTES = 5, SALIR = 6;
    private final int CLIENTE = 0, PROPIETARIO = 1;
    private final String[] menu = {"Inicio", "Buscar", "Reservar", "+Casa", "+Oferta", "Ajustes", "Salir"};



    MainFrame(ruralManagerLogic logica, int tipo) {
        super("ScrumMasters RuralHouses");
        this.logica = logica;
        this.tipo = tipo;

        ImageIcon icon = new ImageIcon("images/icon.png");
        this.setIconImage(icon.getImage());
        languageSettings();

        this.setJMenuBar(setMenuBar());

        this.add(setContainerPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 590);
        this.setResizable(false);



        this.setVisible(true);
    }

    /**
     * Función para cambiar desde una clase externa el cardlayout
     * @param lista
     */
    public void changeToReserves(List<RuralHouse> lista) {
        reserva.setList(lista);

        CardLayout c1 = (CardLayout) contentPanel.getLayout();
        c1.show(contentPanel, menu[RESERVAR]);

    }

    public ResourceBundle getStrings() {
        return strings;
    }

    private JPanel setContainerPanel(){
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        mainPane.add(setLanguagesPane());
        mainPane.add(setContentPane());
        return mainPane;
    }

    private JPanel setLanguagesPane(){
        JPanel lanPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        String[] listaIdiomas = {"castellano", "ingles","chino", "frances", "turco", "swahili", "euskera"};

        JComboBox combo = new JComboBox(listaIdiomas);;
        lanPane.add(combo);
        lanPane.setBackground(new Color(240, 240, 240));

        combo.addActionListener(e->{
            System.out.println(combo.getSelectedItem());
            strings = ResourceBundle.getBundle("languages/Idioma", idiomas.get((String) combo.getSelectedItem()));
            bus.reloadFields();
            edit.reloadFields();
            newRh.reloadFields();
            reserva.reloadFields();
            addOferta.reloadFields();
            reloadMain();
        });

        return lanPane;
    }
    private JPanel setContentPane() {
        if (contentPanel == null) {
            contentPanel = new JPanel(new CardLayout());
            contentPanel.setBackground(new Color(224, 224, 224));

            JPanel pHome = new JPanel();
            pHome.add(setMainPanel());

            bus = new BusquedaAvanzadaGUI(logica);
            JPanel cBusqueda = bus.getPanel();

            edit = new AjustesGUI(logica);
            JPanel cEdit = edit.getPanel();

            newRh = new NewRHGUI(logica);
            JPanel cNewRh = newRh.getPanel();

            reserva = new ReservarGUI(logica);
            JPanel cReserva = reserva.getPanel();

            addOferta = new SetAvailabilityGUI(logica);
            JPanel cOferta = addOferta.getPanel();

            contentPanel.add(pHome, menu[INICIO]);
            contentPanel.add(cNewRh, menu[ADDCASA]);
            contentPanel.add(cBusqueda, menu[BUSCAR]);
            contentPanel.add(cEdit, menu[AJUSTES]);
            contentPanel.add(cReserva, menu[RESERVAR]);
            contentPanel.add(cOferta, menu[ADDOFFER]);
        }
        return contentPanel;
    }

    //Función auxiliar para crear el menu
    private JMenuBar setMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();

            for (int i = 0; i < menu.length; i++) {
                inicializarMenu(i);
            }
            setExitBtn();
        }
        return menuBar;
    }

    //FUnción auxiliar para inicializar cada boton del menu
    private void inicializarMenu(int n) {

        String pathBtn = "images/menu/" + menu[n].toLowerCase() + ".png";
        String pathBtnHover = "images/menu/" + menu[n].toLowerCase() + "hover.png";

        botonesMenu[n] = new JButton(menu[n]);
        Icon iconoBtn = new ImageIcon(pathBtn);
        botonesMenu[n].setHorizontalTextPosition(SwingConstants.CENTER);
        botonesMenu[n].setVerticalTextPosition(SwingConstants.BOTTOM);
        botonesMenu[n].setIcon(iconoBtn);
        botonesMenu[n].setBorderPainted(false);
        botonesMenu[n].setFocusPainted(false);
        botonesMenu[n].setContentAreaFilled(false);
        botonesMenu[n].setRolloverEnabled(true);
        botonesMenu[n].setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonesMenu[n].setRolloverIcon(new ImageIcon((pathBtnHover)));
        botonesMenu[n].addActionListener(e -> {
            editCard(e);
        });
        if (tipo == CLIENTE && (n == ADDCASA || n == ADDOFFER)) {
            botonesMenu[n].setEnabled(false);
        }
        menuBar.add(botonesMenu[n]);
    }

    //Funcion auxiliar para gestionar el comportamiento del boton de salir de forma independiente
    private void setExitBtn() {
        botonesMenu[SALIR].addActionListener(e -> {
            new LoginGUI(logica);
            dispose();
        });
    }

    //Función para cambiar los panels del cardlayout
    private void editCard(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (btn.equals(botonesMenu[RESERVAR])) {
            reserva.setList(logica.getAllRuralHouses());
        }

        CardLayout c1 = (CardLayout) contentPanel.getLayout();
        c1.show(contentPanel, btn.getText());

    }


    private JPanel setMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(520, 460));
        lblBienvenido = new JLabel(strings.getString("main.bienvenido"));
        lblBienvenido.setHorizontalAlignment(JLabel.CENTER);
        lblBienvenido.setFont(new Font("Tahoma", Font.PLAIN, 24));

        mainPanel.add(setCenterContent(), BorderLayout.CENTER);

        Icon footer = new ImageIcon("images/footer.png");
        JLabel lblFooter = new JLabel(footer);
        mainPanel.add(lblBienvenido, BorderLayout.PAGE_START);
        mainPanel.add(lblFooter, BorderLayout.PAGE_END);
        return mainPanel;
    }


    private JPanel setCenterContent() {
        JPanel centerPanel = new JPanel();


        if (tipo == PROPIETARIO) {
            btntabla = new JButton(strings.getString("main.btnReservas"));
            btntabla.addActionListener(e->{
                new ReservasPropietarioGUI(logica);
            });
            centerPanel.add(btntabla);
        }
        btnMisOfertas = new JButton(strings.getString("main.btnVerMisReservas"));
        btnMisOfertas.addActionListener(e->{
            new MisReservas(logica);
        });

        ImageIcon icono = new ImageIcon("images/web.png");
        JButton btnWeb = new JButton(icono);
        btnWeb.setBorderPainted(false);
        btnWeb.setFocusPainted(false);
        btnWeb.setContentAreaFilled(false);
        btnWeb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnWeb.addActionListener(e->{
            try {

                String url ="http://hadscasas.herokuapp.com";

                Desktop dt = Desktop.getDesktop();
                URI uri = new URI(url);
                dt.browse(uri.resolve(uri));


            } catch (URISyntaxException ex) {
                Logger.getLogger(ManagementAssertion.Setting.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ManagementAssertion.Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        centerPanel.add(btnMisOfertas);
        centerPanel.add(btnWeb);
        return centerPanel;
    }


    private void reloadMain(){
        btntabla.setText(strings.getString("main.btnReservas"));
        btnMisOfertas.setText(strings.getString("main.btnVerMisReservas"));
        lblBienvenido.setText(strings.getString("main.bienvenido"));
    }



    private void languageSettings(){

        idiomas = new HashMap<>();


        idiomas.put("castellano", new Locale("es", "ES"));
        idiomas.put("ingles", new Locale("en", "US"));
        idiomas.put("chino", new Locale("zh", "CN"));
        idiomas.put("frances", new Locale("fr", "FR"));
        idiomas.put("turco", new Locale("tr", "TR"));
        idiomas.put("swahili", new Locale("sw", "KE"));
        idiomas.put("euskera", new Locale("eu", "EU"));

        strings = ResourceBundle.getBundle("languages/Idioma", Locale.getDefault());
    }
}
