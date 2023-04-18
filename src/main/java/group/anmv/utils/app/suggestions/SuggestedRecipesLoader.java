package group.anmv.utils.app.suggestions;

import group.anmv.api.GPTService;
import group.anmv.ui.DriverFrame;
import group.anmv.ui.components.dialouges.ErrorFrame;
import group.anmv.ui.components.suggestions.SuggestionsFrame;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class allows for the multithreaded asynchronous
 * fetching and displaying of suggested recipes. It utilizes
 * the SwingWorker class to ensure the main thread isn't blocked
 * by the lengthy fetch operation.
 *
 * @author Ajani Green
 */
public class SuggestedRecipesLoader extends SwingWorker<List<String>, Object> {
    private final List<String> items;
    private final SuggestionsFrame frame;

    public SuggestedRecipesLoader(DriverFrame driverFrame, List<String> items) {
        super();
        this.items = items;
        this.frame = new SuggestionsFrame(driverFrame);
    }

    @Override
    protected List<String> doInBackground() throws Exception {
        frame.setLoading();
        final var prompt = """
                Given the following list of items:\s
                """
                + items.toString() +
                """
                suggest some recipes that I could use. Do not respond with any additional courtesies or anything, just a list of recipes and a description
                """;
        final var assistant = "The list should include the recipe name and the description";
        final var asyncResponse = GPTService.instance().getResponse(prompt, assistant);
        return asyncResponse.thenApply(response -> {
            final var suggestions = response
                    .split("[0-9]{1,2}\\.\\s");

            return Arrays.stream(suggestions)
                    .filter(suggestion -> !items.contains(suggestion))
                    .toList();
        }).whenComplete((responses, err) -> {
            if (err != null)
                new ErrorFrame(err.getMessage());
        }).get();
    }

    @Override
    protected void done() {
        try {
            frame.setRecipes(get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            new ErrorFrame(e.getMessage());
        }
    }
}
