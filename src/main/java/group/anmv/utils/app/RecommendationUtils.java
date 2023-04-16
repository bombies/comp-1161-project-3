package group.anmv.utils.app;

import group.anmv.api.GPTService;
import group.anmv.ui.RecipeRec;

import java.util.Arrays;
import java.util.List;

public class RecommendationUtils {

    public static List<String> getSuggestedItems(List<String> items) {
        final var prompt = """
                Given the following list of items:\s
                """
                + items.toString() +
                """ 
                 suggest some more similar items I could add to this shopping list.
                                
                Don't respond with additional courtesies or anything just the items in a comma separated fashion.
                """;
        final var assistant = "The list should be formatted in such a way that all the elements are encapsulated in square brackets and comma separated";
        final var response = GPTService.instance().getResponse(prompt, assistant);

        final var suggestions = response
                .replaceAll("[\\[\\]]", "")
                .split(",\\s*");

        return Arrays.stream(suggestions)
                .filter(suggestion -> !items.contains(suggestion))
                .toList();
    }

    public static void getSuggestedRecipes(List<String> ingredients) {
        final var prompt = """
                Given the following list of items:\s
                """
                + ingredients.toString() +
                """
                suggest some recipes that I could use. Do not respond with any additional courtesies or anything, just a list of recipes and a description
                """;
        final var assistant = "The list should include the recipe name and the description";
        final var response = GPTService.instance().getResponse(prompt, assistant);
        final var suggestions = response.split("[0-9]{1,2}.\\s");
        new RecipeRec(suggestions);
    }
}
