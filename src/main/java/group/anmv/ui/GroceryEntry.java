package group.anmv.ui;

import group.anmv.ui.models.Ingredients;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class to facilitate user addition of ingredients to grocery list via GUI
 * @author Nathan Smith
 */
public class GroceryEntry extends JFrame {

    ArrayList<Ingredients> grocerylist;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private GroceryEntry thisgroceryentry;

    public GroceryEntry(ArrayList<Ingredients> grocerylist) {
        super("Add a New Ingredient");
        this.grocerylist = grocerylist;
        thisgroceryentry = this;
        buttonPanel = new JPanel();
        displayPanel = new JPanel();
        displayPanel.add(new JLabel("Ingredient Name:"));
        displayPanel.add(new JTextField(20));
        JButton addIngredient = new JButton("Add Ingredient");
        JButton closeWindow = new JButton("Close");
        closeWindow.addActionListener((actionPerformed) -> {
                thisgroceryentry.setVisible(false);
        });
        buttonPanel.add(addIngredient);
        buttonPanel.add(closeWindow);
        add(displayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        this.setLocationByPlatform(true);
        pack();
        setVisible(true);
    }
}
