package group.anmv.ui.components.dialouges;

import javax.swing.*;

public class SuccessFrame extends JOptionPane {

    public SuccessFrame(String description) {
        showMessageDialog(null, description, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
