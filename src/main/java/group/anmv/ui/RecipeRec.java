package group.anmv.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RecipeRec extends JFrame {

    public RecipeRec(String[] response) {
        super("Recipe Recommendations");
        setLayout(new BorderLayout());
        JPanel dispPanel = new JPanel();
        dispPanel.setLayout(new GridLayout(0, 1));

        for (String recipe : response)
            dispPanel.add(new JLabel(recipe + "\n"));

        JScrollPane scroll = new JScrollPane(dispPanel);
        setLocationByPlatform(true);
        add(scroll, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }
}
