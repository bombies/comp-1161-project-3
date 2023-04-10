package group.anmv.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DriverFrame extends JFrame {

    private ArrayList<String> grocerylist = new ArrayList<>(Arrays.asList("Apple", "Orange", "Bread", "Lettuce"));
    private JPanel buttonPanel;
    private JList ingredientlist;
    private DefaultListModel listModel;

    public DriverFrame(){
        super("Grocery List");
        setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        listModel = new DefaultListModel();
        populateTable(grocerylist);
        ingredientlist = new JList(listModel);
        JButton add = new JButton("Add Ingredient");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GroceryEntry(grocerylist);
            }
        });
        JButton sort = new JButton("Sort Ingredients");
        JButton close = new JButton("Close Grocery List");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(add);
        buttonPanel.add(sort);
        buttonPanel.add(close);

        this.add(ingredientlist, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateTable(ArrayList<String> grocerylist){
        for (String ingredient: grocerylist){
            listModel.addElement(ingredient);
        }
    }

    public static void driver() {
       DriverFrame frame =  new DriverFrame();
       frame.setSize(500,500);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocationByPlatform(true);
       frame.pack();
       frame.setVisible(true);
    }
}
