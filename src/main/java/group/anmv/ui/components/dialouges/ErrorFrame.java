package group.anmv.ui.components.dialouges;

import javax.swing.*;

/**
 * The frame that will be used to display error messages
 *
 * @author Ajani Green
 */
public class ErrorFrame extends JOptionPane {

    /**
     * The constructor that will display a new frame with a custom title and description
     * @param title The title of the frame
     * @param description The content of the frame
     */
    public ErrorFrame(String title, String description) {
        showMessageDialog(null, description, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * The constructor that will display only custom content
     * @param description The content of the frame
     */
    public ErrorFrame(String description) {
        showMessageDialog(null, description, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
