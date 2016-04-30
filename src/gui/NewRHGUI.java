package gui;

import businessLogic.ruralManagerLogic;
import exceptions.UsuarioNoExiste;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by joseba on 25/2/16.
 */
public class NewRHGUI extends JPanel implements PanelCard{

    //Constantes
    private final int NUMERO_CAMPOS = 5;

    //Logica de negocio de la aplicación
    private ruralManagerLogic logica;

    //Componentes de la interfaz de usuario
    private JPanel mainPane, infoPane, btnPane;
    private JTextField tfNombre, tfCiudad, tfDir, tfNumTel, tfHabs, tfBan;
    private JTextArea taDesc;
    private JButton btnAceptar;
    private JLabel lblNombre, lblCiudad, lblTel, lblDir, lblHabitaciones, lblBanios, lblDescripcion;

    private String imagePath;
    private ResourceBundle strings;

    /**
     * Constructor
     *
     * @param logica
     */
    NewRHGUI(ruralManagerLogic logica) {

        this.logica = logica;
        strings = MainFrame.strings;
        add(setMainPane());
        setVisible(true);
        renderingHandler();
    }

    public JPanel getPanel() {
        return this;
    }

    @Override
    public void reloadFields() {
        strings = MainFrame.strings;

        lblNombre.setText(strings.getString("nueva.lblNombre"));
        lblCiudad.setText(strings.getString("nueva.lblCiudad"));
        lblTel.setText(strings.getString("nueva.lblTel"));
        lblDir.setText(strings.getString("nueva.lblDir"));
        lblHabitaciones.setText(strings.getString("nueva.lblHabitaciones"));
        lblBanios.setText(strings.getString("nueva.lblBanios"));
        lblDescripcion.setText(strings.getString("nueva.lblDescripcion"));
        btnAceptar.setText(strings.getString("nueva.btnAceptar"));

    }

    //JPanel principal
    private JPanel setMainPane() {
        if (mainPane == null) {
            mainPane = new JPanel();
            mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

            mainPane.add(setInfoPane());
            mainPane.add(setBtnPane());
        }
        return mainPane;
    }

    //JPanel con los componentes para añadir los datos
    private JPanel setInfoPane() {

        if (infoPane == null) {
            infoPane = new JPanel();
            infoPane.setPreferredSize(new Dimension(400, 400));
            infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.PAGE_AXIS));

            lblNombre = new JLabel(strings.getString("nueva.lblNombre"));
            infoPane.add(lblNombre);
            tfNombre = new JTextField(17);
            infoPane.add(tfNombre);

            lblCiudad = new JLabel(strings.getString("nueva.lblCiudad"));
            infoPane.add(lblCiudad);
            tfCiudad = new JTextField(17);
            infoPane.add(tfCiudad);

            lblTel = new JLabel(strings.getString("nueva.lblTel"));
            infoPane.add(lblTel);
            tfNumTel = new JTextField(17);
            tfNumTel.addFocusListener(new SoloNumeros());
            infoPane.add(tfNumTel);

            lblDir = new JLabel(strings.getString("nueva.lblDir"));
            infoPane.add(lblDir);
            tfDir = new JTextField(17);
            infoPane.add(tfDir);

            lblHabitaciones = new JLabel(strings.getString("nueva.lblHabitaciones"));
            infoPane.add(lblHabitaciones);
            tfHabs = new JTextField(17);
            tfHabs.addFocusListener(new SoloNumeros());
            infoPane.add(tfHabs);

            lblBanios = new JLabel(strings.getString("nueva.lblBanios"));
            infoPane.add(lblBanios);
            tfBan = new JTextField(17);
            tfBan.addFocusListener(new SoloNumeros());
            infoPane.add(tfBan);

            lblDescripcion = new JLabel(strings.getString("nueva.lblDescripcion"));
            infoPane.add(lblDescripcion);

            taDesc = new JTextArea(17, 5);
            JScrollPane scrollText = new JScrollPane(taDesc);
            scrollText.getViewport().setPreferredSize(new Dimension(400, 200));

            infoPane.add(scrollText);


        }
        return infoPane;
    }

    //TODO: Futura implementación para añadir imagenes
    private JButton getImage() {
        JButton chooserBtn = new JButton("Introducir imagen");
        chooserBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Introduce una imagen de la casa");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                String filepath = chooser.getSelectedFile().toString();


            } else {
                System.out.println("No Selection ");
            }
        });

        return chooserBtn;
    }

    //TODO: Futura implementacion para convertir la imagen añadida
    private byte[] convertImage(String path) {

        File image = new File(path);
        try {
            BufferedImage buf = ImageIO.read(image);
            WritableRaster raster = buf.getRaster();
            DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
            return data.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    //JPanel con el boton para aceptar los datos
    private JPanel setBtnPane() {

        if (btnPane == null) {
            btnPane = new JPanel();
            btnAceptar = new JButton(strings.getString("nueva.btnAceptar"));
            btnAceptar.addActionListener(e -> {

                        if (numeroCamposLibres() == 0) {
                            try {
                                logica.storeRH(tfNombre.getText(), tfCiudad.getText(), tfDir.getText(), Integer.parseInt(tfNumTel.getText()),
                                        taDesc.getText(), Integer.parseInt(tfHabs.getText()), Integer.parseInt(tfBan.getText()));
                                JOptionPane.showMessageDialog(null, strings.getString("nueva.guardada"));

                            } catch (UsuarioNoExiste ex) {
                                JOptionPane.showMessageDialog(null, strings.getString("nueva.error.generico"));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, strings.getString("nueva.error.camposVacios"));
                        }
                    }
            );

            btnPane.add(btnAceptar);
        }
        return btnPane;
    }

    //Función para limpiar los campos al salir
    private void clearFields() {
        tfHabs.setText("");
        tfBan.setText("");
        tfCiudad.setText("");
        tfDir.setText("");
        tfNombre.setText("");
        tfNumTel.setText("");
        taDesc.setText("");

    }


    private void renderingHandler() {
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                clearFields();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    private int numeroCamposLibres() {
        int num = 0;
        if (tfDir.getText().isEmpty())
            num++;
        if (tfNumTel.getText().isEmpty())
            num++;
        if (tfCiudad.getText().isEmpty())
            num++;
        if (tfNombre.getText().isEmpty())
            num++;
        if (taDesc.getText().isEmpty())
            num++;

        return num;
    }
}
