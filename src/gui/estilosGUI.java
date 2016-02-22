package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by joseba on 22/2/16.
 */
public class estilosGUI {

    //COLORES UTILIZADOS PARA LAS INTERFACES

    public static final Color bckColor = new Color(230, 230, 237);
    public static final Color bckColorDark = new Color(83, 92, 95);

    /**
     * Genera un header para las interfaces
     *
     * @param titulo En caso de no encontrar la imagen pone el titulo
     * @return JPanel con el header
     */
    public static JPanel setHeaderPane(String titulo) {
        JPanel headerPane = new JPanel();
        BufferedImage header;

        headerPane.setBackground(bckColor);
        JLabel headerLabel;
        try {

            header = ImageIO.read(new File("images/header.png"));
            headerLabel = new JLabel(new ImageIcon(header));
        } catch (IOException e) {
            e.printStackTrace();
            headerLabel = new JLabel(titulo);
            headerLabel.setFont(new Font("Segoe Print", Font.PLAIN, 35));

        }
        headerPane.add(headerLabel);

        return headerPane;
    }
}
