import group.anmv.ui.models.Ingredient;
import group.anmv.utils.save.SaveHandler;
import group.anmv.utils.save.models.SavedItemsModel;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class contains all the tests for
 * the SaveHandler implementation.
 *
 * @author Ajani Green
 */
public class SaveTests {

    /**
     * Test if items can be successfully saved.
     *
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemSave() throws IOException {
        final var testItems = List.of(
                new Ingredient("Apple", 10, 3),
                new Ingredient("Cherries", 10, 4),
                new Ingredient("Watermelon", 10, 5),
                new Ingredient("Baking Soda", 10, 5)
        );

        final var saveTime = SaveHandler.saveItems(testItems);
        final var optimisticFile = new File("./data/items.json");
        final var optimisticContent = new JSONObject()
                .put("items", testItems)
                .put("last_saved", saveTime);

        assertNotNull(optimisticFile);
        assertTrue("Testing if content is the same", optimisticContent.similar(new JSONObject(SaveHandler.readFromFile(optimisticFile))));
    }

    /**
     * Test if items can be successfully appended and saved.
     *
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemAppendSave() throws IOException {
        final var testSavedItemsModel = new SavedItemsModel(
                List.of(
                        new Ingredient("Apple", 10, 2),
                        new Ingredient("Cherries", 10, 3),
                        new Ingredient("Watermelon", 10, 4),
                        new Ingredient("Baking Soda", 10, 5)
                ),
                System.currentTimeMillis()
        );

        SaveHandler.saveItems(testSavedItemsModel);
        final var saveTime = SaveHandler.appendItem(new Ingredient("Bananas", 10, 5));
        final var optimisticFile = new File("./data/items.json");
        final var optimisticContent = new JSONObject()
                .put("items", List.of(
                        new Ingredient("Apple", 10, 2),
                        new Ingredient("Cherries", 10, 4),
                        new Ingredient("Watermelon", 10, 5),
                        new Ingredient("Baking Soda", 10, 6),
                        new Ingredient("Bananas", 10, 7)
                ))
                .put("last_saved", saveTime);
        assertNotNull(optimisticFile);
        assertTrue("Testing if content is the same", optimisticContent.similar(new JSONObject(SaveHandler.readFromFile(optimisticFile))));
    }

    /**
     * test if items can be successfully removed and saved.
     *
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemRemoval() throws IOException {
        final var testSavedItemsModel = new SavedItemsModel(
                List.of(
                        new Ingredient("Apple", 10, 3),
                        new Ingredient("Cherries", 10,4 ),
                        new Ingredient("Watermelon", 10, 5),
                        new Ingredient("Baking Soda", 10, 6)
                ),
                System.currentTimeMillis()
        );

        SaveHandler.saveItems(testSavedItemsModel);
        final var saveTime = SaveHandler.removeItem(new Ingredient("Baking Soda", 10, 4));
        final var optimisticFile = new File("./data/items.json");
        final var optimisticContent = new JSONObject()
                .put("items", List.of(
                                new Ingredient("Apple", 10, 3 ),
                                new Ingredient("Cherries", 10, 4),
                                new Ingredient("Watermelon", 10, 4)
                        )
                )
                .put("last_saved", saveTime);
        assertNotNull(optimisticFile);
        assertTrue("Testing if content is the same", optimisticContent.similar(new JSONObject(SaveHandler.readFromFile(optimisticFile))));
    }

    /**
     * Test if items can be successfully cleared and saved.
     *
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemClear() throws IOException {
        final var testSavedItemsModel = new SavedItemsModel(
                List.of(
                        new Ingredient("Apple", 10,2 ),
                        new Ingredient("Cherries", 10,2 ),
                        new Ingredient("Watermelon", 10, 3),
                        new Ingredient("Baking Soda", 10, 4)
                ),
                System.currentTimeMillis()
        );

        SaveHandler.saveItems(testSavedItemsModel);
        final var saveTime = SaveHandler.clearItems();
        final var optimisticFile = new File("./data/items.json");
        final var optimisticContent = new JSONObject()
                .put("items", List.of())
                .put("last_saved", saveTime);
        assertNotNull(optimisticFile);
        assertTrue("Testing if content is the same", optimisticContent.similar(new JSONObject(SaveHandler.readFromFile(optimisticFile))));
    }
}
