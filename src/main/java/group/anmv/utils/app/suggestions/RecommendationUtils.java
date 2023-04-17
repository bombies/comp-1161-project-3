package group.anmv.utils.app.suggestions;

import group.anmv.ui.DriverFrame;

import java.util.List;

public class RecommendationUtils {

    public static void getSuggestedItems(DriverFrame driverFrame, List<String> items) {
        new SuggestedItemsLoader(driverFrame, items).execute();
    }

    public static void getSuggestedRecipes(DriverFrame driverFrame, List<String> items) {
        new SuggestedRecipesLoader(driverFrame, items).execute();
    }
}
