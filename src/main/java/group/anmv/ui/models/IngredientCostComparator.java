package group.anmv.ui.models;


import java.util.Comparator;

/**
 * This comparator will be used to sort ingredients
 * in ascending order by cost.
 *
 * @author Mohitha Chindepalli
 */
public class IngredientCostComparator implements Comparator<Ingredient> {

    private double cost;

    public double getCost() {
        return cost;
    }


    public int compare(Ingredient o1, Ingredient o2) {
        return (int) (o1.getCost()-o2.getCost());
    }
}
