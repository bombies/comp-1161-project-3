package group.anmv.utils.app.suggestions;

import java.util.List;

public class RecommendationUtils {

    public static void getSuggestedItems(List<String> items) {
        new SuggestedItemsLoader(items).execute();
    }
}
