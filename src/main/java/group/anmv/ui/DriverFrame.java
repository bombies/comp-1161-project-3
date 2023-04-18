package group.anmv.ui;

import group.anmv.ui.components.mutations.EditFrame;
import group.anmv.ui.components.mutations.EntryFrame;
import group.anmv.ui.components.dialouges.ErrorFrame;
import group.anmv.ui.components.mutations.RemoveFrame;
import group.anmv.ui.models.Ingredient;
import group.anmv.ui.models.IngredientCostComparator;
import group.anmv.ui.models.IngredientNameComparator;
import group.anmv.utils.app.suggestions.RecommendationUtils;
import group.anmv.utils.save.SaveHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class to facilitate the creation of main GUI window
 *
 * @author Nathan Smith
 */
public class DriverFrame extends JFrame {

    private ArrayList<Ingredient> groceryList = new ArrayList<>(SaveHandler.ins().getSave().getItems());
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
        JButton sortCost = new JButton("Sort Ingredients By Cost");
        JButton sortName = new JButton("Sort Ingredients By Name");
        JButton recommendIngredient = new JButton("Recommend Ingredients");
        JButton recommendRecipe = new JButton("Recommend Recipes");
        JButton removeIngredient = new JButton("Remove an Ingredient");
        JButton editIngredient = new JButton("Edit an Ingredient");
        JButton close = new JButton("Close Grocery List");

        add.addActionListener((actionPerformed) -> new EntryFrame(groceryList, this));

        close.addActionListener((actionPerformed) -> System.exit(0));

        recommendIngredient.addActionListener((e) -> {
            if (groceryList.size() == 0)
                new ErrorFrame("I can't suggest any items if you have none in your list!");
            else {
                RecommendationUtils.getSuggestedItems(this,
                        groceryList.stream()
                                .map(Ingredient::getName)
                                .toList()
                );
            }
        });

        recommendRecipe.addActionListener((actionPerformed) -> {
            if (groceryList.size() == 0)
                new ErrorFrame("I can't suggest any recipes if you have none in your list!");
            else {
                RecommendationUtils.getSuggestedRecipes(this,
                        groceryList.stream()
                                .map(Ingredient::getName)
                                .toList()
                );
            }
        });

        sortCost.addActionListener((e) -> {
            tableModel.setRowCount(0);
            groceryList.sort(new IngredientCostComparator());
            populateTable(groceryList);
        });

        sortName.addActionListener((e) -> {
            tableModel.setRowCount(0);
            groceryList.sort(new IngredientNameComparator());
            populateTable(groceryList);
        });

        removeIngredient.addActionListener((e) -> new RemoveFrame(groceryList, this));

        editIngredient.addActionListener((e) -> new EditFrame(groceryList, this));

        buttonPanel.add(add);
        buttonPanel.add(sortCost);
        buttonPanel.add(sortName);
        buttonPanel.add(recommendRecipe);
        buttonPanel.add(recommendIngredient);
        buttonPanel.add(removeIngredient);
        buttonPanel.add(editIngredient);
        buttonPanel.add(close);

        if (groceryList != null) {
            String[] columnNames = {"Ingredient Name", "Cost", "Quantity", "Ingredient Total Cost"};
            tableModel = new DefaultTableModel(columnNames, 0);
            populateTable(groceryList);
            scrollPane = new JScrollPane(new JTable(tableModel));
            this.add(scrollPane, BorderLayout.CENTER);
        } else {
            JPanel emptyScreen = new JPanel();
            emptyScreen.add(new JLabel("Your Grocery List is empty! Press the Add ingredient button to add an ingredient."));
            this.add(emptyScreen, BorderLayout.CENTER);
        }

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Add a new ingredient to the table
     * @param ingredient The ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
        groceryList.add(ingredient);
        populateTable();
    }

    /**
     * Remove an ingredient from the table
     * @param ingredient The ingredient to remove
     */
    public void removeIngredient(Ingredient ingredient) {
        groceryList.remove(ingredient);
        populateTable();
    }

    /**
     * Edit a specific ingredient in the table
     * @param name The name of the ingredient to edit
     * @param ingredient The new content of the ingredient
     */
    public void editIngredient(String name, Ingredient ingredient) {
        groceryList.removeIf(i -> i.getName().equalsIgnoreCase(name));
        groceryList.add(ingredient);
        populateTable();
    }


    /**
     * Method that allows for populating table on first start-up
     *
     * @param groceryList list of Ingredients objects to be added to the GUI table
     */
    public void populateTable(ArrayList<Ingredient> groceryList) {
        for (Ingredient ingredient : groceryList) {
            Object[] item = {ingredient.getName(), ingredient.getCost(), ingredient.getQuantity(), ingredient.calcTotalCost()};
            tableModel.addRow(item);
        }
    }

    /**
     * Re-render the table with the current
     * information in the grocery list
     */
    public void populateTable() {
        tableModel.setRowCount(0);

        for (Ingredient ingredient : groceryList) {
            Object[] item = {ingredient.getName(), ingredient.getCost(), ingredient.getQuantity(), ingredient.calcTotalCost()};
            tableModel.addRow(item);
        }

        revalidate();
        repaint();
    }


    /**
     * Method that instantiates the main GUI window to be used by user
     */
    public static void driver() {
        DriverFrame frame = new DriverFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }


}
