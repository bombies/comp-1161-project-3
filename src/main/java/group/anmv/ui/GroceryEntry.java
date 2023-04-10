package group.anmv.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GroceryEntry extends JFrame {

    ArrayList<String> grocerylist;
    private JPanel buttonPanel;
    private JPanel displayPanel;
    private GroceryEntry thisgroceryentry;

    public GroceryEntry(ArrayList<String> grocerylist){
        super("Add a New Ingredient");
        this.grocerylist = grocerylist;
        thisgroceryentry = this;
        buttonPanel = new JPanel();
        displayPanel = new JPanel();
        displayPanel.add(new JLabel("Ingredient Name:"));
        displayPanel.add(new JTextField(20));
        JButton addIngredient = new JButton("Add Ingredient");
        JButton closeWindow = new JButton("Close");
        closeWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisgroceryentry.setVisible(false);
            }
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
