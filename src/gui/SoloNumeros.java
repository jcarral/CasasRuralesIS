package gui;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class SoloNumeros implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField _this = (JTextField) e.getSource();
        try {

            if (_this.getText().length() > 0)
                Integer.parseInt(_this.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Eso no es un número, mete un número",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            _this.setText("");
        }
    }

}