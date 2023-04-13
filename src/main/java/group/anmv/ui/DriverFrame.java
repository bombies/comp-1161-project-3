package group.anmv.ui;

import group.anmv.models.Ingredients;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DriverFrame extends JFrame {

    private ArrayList<Ingredients> grocerylist = new ArrayList<Ingredients>(Arrays.asList(new Ingredients("Bread", 100)));
    private JPanel buttonPanel;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public DriverFrame(){
        super("Grocery List");
        setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        JButton add = new JButton("Add Ingredient");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GroceryEntry(grocerylist);
            }
        });
        JButton sort = new JButton("Sort Ingredients");
        JButton recommendIngredient = new JButton("Recommend Ingredients");
        JButton recommendRecipe = new JButton("Recommend Recipes");
        JButton close = new JButton("Close Grocery List");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(add);
        buttonPanel.add(sort);
        buttonPanel.add(recommendRecipe);
        buttonPanel.add(recommendIngredient);
        buttonPanel.add(close);
        if(grocerylist!=null) {
            String[] columnNames = {"Ingredient Name", "Cost"};
            tableModel = new DefaultTableModel(columnNames, 0);
            populateTable(grocerylist);
            scrollPane = new JScrollPane(new JTable(tableModel));
            this.add(scrollPane, BorderLayout.CENTER);
        }
        else{
            JPanel emptyScreen = new JPanel();
            emptyScreen.add(new JLabel("Your Grocery List is empty! Press the Add ingredient button to add an ingredient."));
            this.add(emptyScreen, BorderLayout.CENTER);
        }

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateTable(ArrayList<Ingredients> grocerylist){
        for (Ingredients ingredient: grocerylist){
            Object[] item = {ingredient.getName(), ingredient.getCost()};
            tableModel.addRow(item);
        }
    }

    public static void driver() {
       DriverFrame frame =  new DriverFrame();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocationByPlatform(true);
       frame.pack();
       frame.setVisible(true);
    }
}
