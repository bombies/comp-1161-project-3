package group.anmv.ui.components.dialouges;

import javax.swing.*;

/**
 * The frame that will be used to display success messages
 *
 * @author Ajani Green
 */
public class SuccessFrame extends JOptionPane {

    /**
     * The constructor that will display a new frame with a custom title and description
     * @param description The content of the frame
     */
    public SuccessFrame(String description) {
        showMessageDialog(null, description, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
