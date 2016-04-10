package gui;

import businessLogic.ruralManagerLogic;
import domain.RuralHouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;


public class MainFrame extends JFrame {

    //UI components
    private JMenuBar menuBar;
    private JPanel contentPanel, cReserva;
    private JButton[] botonesMenu = new JButton[7];

    //Atributos clase
    private ruralManagerLogic logica;
    private int tipo;
    private ReservarGUI reserva;

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

        this.setJMenuBar(setMenuBar());
        this.add(setContentPane());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 550);
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

    private JPanel setContentPane() {
        if (contentPanel == null) {
            contentPanel = new JPanel(new CardLayout());
            contentPanel.setBackground(new Color(224, 224, 224));

            JPanel pHome = new JPanel();
            pHome.add(setMainPanel());

            BusquedaAvanzadaGUI bus = new BusquedaAvanzadaGUI(logica);
            JPanel cBusqueda = bus.getPanel();

            AjustesGUI edit = new AjustesGUI(logica);
            JPanel cEdit = edit.getPanel();

            NewRHGUI newRh = new NewRHGUI(logica);
            JPanel cNewRh = newRh.getPanel();

            reserva = new ReservarGUI(logica);
            cReserva = reserva.getPanel();

            SetAvailabilityGUI addOferta = new SetAvailabilityGUI(logica);
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
        JLabel lblBienvenido = new JLabel("Bienvenido a ScrumMasters RuralHouses");
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

        if (tipo == CLIENTE) {

        } else {

        }

        return centerPanel;
    }
}
