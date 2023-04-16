package group.anmv.ui.components;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class LoadingFrame extends JDialog {

    /**
     * The constructor that will display the loading
     * frame with a custom description
     * @param description The text to display
     */
    public LoadingFrame(String description) {
        super(null, description, ModalityType.DOCUMENT_MODAL);
        setVisible(true);
    }

    public LoadingFrame(String description, Consumer<WindowEvent> handleClose) {
        super(null, description, ModalityType.DOCUMENT_MODAL);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                handleClose.accept(e);
            }
        });
        setVisible(true);
    }
}
