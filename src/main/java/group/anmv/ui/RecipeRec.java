package group.anmv.ui;

import javax.swing.*;
import java.awt.*;

public class RecipeRec extends JFrame {

    public RecipeRec(String[] response) {
        super("Recipe Recommendations");
        setLayout(new BorderLayout());
        JPanel dispPanel = new JPanel();
        //TODO fix this layout
        for (String recipe : response) {
            dispPanel.add(new JLabel(recipe));
        }
        JScrollPane scroll = new JScrollPane(dispPanel);
        this.setLocationByPlatform(true);
        add(scroll, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
}
