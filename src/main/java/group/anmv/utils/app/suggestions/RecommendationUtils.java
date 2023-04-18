package group.anmv.utils.app.suggestions;

import group.anmv.ui.DriverFrame;

import java.util.List;

public class RecommendationUtils {

    /**
     * Fetches and displays suggested items based on a list of items provided
     * @param driverFrame The main display frame
     * @param items The items to base suggestions on
     */
    public static void getSuggestedItems(DriverFrame driverFrame, List<String> items) {
        new SuggestedItemsLoader(driverFrame, items).execute();
    }

    /**
     * Fetches and displays suggested recipes based on a list of items provided
     * @param driverFrame The main display frame
     * @param items The items to base suggestions on
     */
    public static void getSuggestedRecipes(DriverFrame driverFrame, List<String> items) {
        new SuggestedRecipesLoader(driverFrame, items).execute();
    }
}
