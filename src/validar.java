import javax.swing.*;
import java.awt.event.KeyEvent;

public class validar {
    public static void validameEsta (String textDni, KeyEvent evt) {
        if (textDni.length() >= 8) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Mi rey, no existen DNI con más de 8 números");
        }
    }
}