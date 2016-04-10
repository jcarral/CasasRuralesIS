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

    private String imagePath;

    /**
     * Constructor
     *
     * @param logica
     */
    NewRHGUI(ruralManagerLogic logica) {

        this.logica = logica;
        add(setMainPane());
        setVisible(true);
        renderingHandler();
    }

    public JPanel getPanel() {
        return this;
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
            infoPane.add(new JLabel("Nombre de la casa: "));
            tfNombre = new JTextField(17);
            infoPane.add(tfNombre);

            infoPane.add(new JLabel("Ciudad: "));
            tfCiudad = new JTextField(17);
            infoPane.add(tfCiudad);

            infoPane.add(new JLabel("Numero de telefono: "));
            tfNumTel = new JTextField(17);
            tfNumTel.addFocusListener(new SoloNumeros());
            infoPane.add(tfNumTel);

            infoPane.add(new JLabel("Dirección: "));
            tfDir = new JTextField(17);
            infoPane.add(tfDir);

            infoPane.add(new JLabel("Número de habitaciones: "));
            tfHabs = new JTextField(17);
            tfHabs.addFocusListener(new SoloNumeros());
            infoPane.add(tfHabs);

            infoPane.add(new JLabel("Número de baños: "));
            tfBan = new JTextField(17);
            tfBan.addFocusListener(new SoloNumeros());
            infoPane.add(tfBan);

            infoPane.add(new JLabel("Descripción sobre la casa rural"));

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
            btnAceptar = new JButton("Aceptar");
            btnAceptar.addActionListener(e -> {

                        if (numeroCamposLibres() == 0) {
                            try {
                                logica.storeRH(tfNombre.getText(), tfCiudad.getText(), tfDir.getText(), Integer.parseInt(tfNumTel.getText()),
                                        taDesc.getText(), Integer.parseInt(tfHabs.getText()), Integer.parseInt(tfBan.getText()));
                                JOptionPane.showMessageDialog(null, "Datos guardados correctamente");

                            } catch (UsuarioNoExiste ex) {
                                JOptionPane.showMessageDialog(null, "No se han podido guardar los datos, intentalos más tarde");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No has rellenado todos los campos");
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
