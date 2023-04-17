package group.anmv.ui;

import group.anmv.ui.components.EditFrame;
import group.anmv.ui.components.ErrorFrame;
import group.anmv.ui.components.SuggestionsFrame;
import group.anmv.ui.models.Ingredient;
import group.anmv.ui.components.RemoveFrame;
import group.anmv.ui.models.IngredientCostComparator;
import group.anmv.ui.models.IngredientNameComparator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class to facilitate the creation of main GUI window
 * @author Nathan Smith
 */
public class DriverFrame extends JFrame {

    private ArrayList<Ingredient> groceryList = new ArrayList<>(List.of(new Ingredient("Bread", 100, 12)));
    private JPanel buttonPanel;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;


    /**
     * Constructor for main GUI window
     */
    public DriverFrame() {
        super("Grocery List");
        setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        JButton add = new JButton("Add Ingredient");
        add.addActionListener((actionPerformed) -> {
                new GroceryEntry(groceryList, this);
        });
        JButton sortCost = new JButton("Sort Ingredients By Cost");
        JButton sortName = new JButton("Sort Ingredients By Name");
        JButton recommendIngredient = new JButton("Recommend Ingredients");
        JButton recommendRecipe = new JButton("Recommend Recipes");
        JButton removeIngredient=new JButton("Remove an Ingredient");
        JButton editIngredient=new JButton("Edit an Ingredient");
        JButton close = new JButton("Close Grocery List");

        close.addActionListener((actionPerformed) -> {
                System.exit(0);

        });

        recommendIngredient.addActionListener((e) -> {
            if (groceryList.size() == 0)
                new ErrorFrame("I can't suggest any items if you have none in your list!");
            else {
//                final var suggestedItems = RecommendationUtils.getSuggestedItems(
//                        groceryList.stream()
//                                .map(Ingredient::getName)
//                                .toList()
//                );
                new SuggestionsFrame(List.of("Apples", "Bananas", "Flour"));
            }

        });



        sortCost.addActionListener((e) -> {
            tableModel.setRowCount(0);
            Collections.sort(groceryList,new IngredientCostComparator());
            populateTable(groceryList);
        });

        sortName.addActionListener((e) -> {
            tableModel.setRowCount(0);
            Collections.sort(groceryList,new IngredientNameComparator());
            populateTable(groceryList);
        });

        removeIngredient.addActionListener((e)-> {
           new RemoveFrame(groceryList, this);
        });

        editIngredient.addActionListener((e)->{
            new EditFrame(groceryList, this);
        });

        buttonPanel.add(add);
        buttonPanel.add(sortCost);
        buttonPanel.add(sortName);
        buttonPanel.add(recommendRecipe);
        buttonPanel.add(recommendIngredient);
        buttonPanel.add(removeIngredient);
        buttonPanel.add(editIngredient);
        buttonPanel.add(close);
        if(groceryList != null) {
            String[] columnNames = {"Ingredient Name", "Cost", "Quantity", "Ingredient Total Cost"};
            tableModel = new DefaultTableModel(columnNames, 0);
            populateTable(groceryList);
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



    /**
     * Method that allows for populating table on first start-up
     * @param grocerylist list of Ingredients objects to be added to the GUI table
     */
    public void populateTable(ArrayList<Ingredient> grocerylist) {
        for (Ingredient ingredient : grocerylist){
            Object[] item = {ingredient.getName(), ingredient.getCost(), ingredient.getQuantity(), ingredient.calcTotalCost()};
            tableModel.addRow(item);
        }
    }


    /**
     * Method that instantiates the main GUI window to be used by user
     */
    public static void driver() {
       DriverFrame frame =  new DriverFrame();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocationByPlatform(true);
       frame.pack();
       frame.setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }


}
