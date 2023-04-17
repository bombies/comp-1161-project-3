package group.anmv.ui.models;


import java.util.Comparator;

public class IngredientNameComparator implements Comparator<Ingredient> {

    private String name;

    public String getName() {
        return name;
    }


    public int compare(Ingredient o1, Ingredient o2) {
        return (int) (o1.getName().compareTo(o2.getName()));
    }
}
