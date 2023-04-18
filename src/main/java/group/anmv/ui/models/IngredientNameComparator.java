package group.anmv.ui.models;


import java.util.Comparator;

/**
 * This comparator will be used to sort ingredients
 * in ascending order by name.
 *
 * @author Mohitha Chindepalli
 */
public class IngredientNameComparator implements Comparator<Ingredient> {

    private String name;

    public String getName() {
        return name;
    }


    public int compare(Ingredient o1, Ingredient o2) {
        return (int) (o1.getName().compareTo(o2.getName()));
    }
}
