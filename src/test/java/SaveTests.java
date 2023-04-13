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
 */
public class SaveTests {

    /**
     * Test if items can be successfully saved.
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemSave() throws IOException {
        final var testItems = List.of("Apple", "Cherries", "Watermelons", "Baking Soda");

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
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemAppendSave() throws IOException {
        final var testSavedItemsModel = new SavedItemsModel(
                List.of("Apple", "Cherries", "Watermelons", "Baking Soda"),
                System.currentTimeMillis()
        );

        SaveHandler.saveItems(testSavedItemsModel);
        final var saveTime = SaveHandler.appendItem("Bananas");
        final var optimisticFile = new File("./data/items.json");
        final var optimisticContent = new JSONObject()
                .put("items", List.of("Apple", "Cherries", "Watermelons", "Baking Soda", "Bananas"))
                .put("last_saved", saveTime);
        assertNotNull(optimisticFile);
        assertTrue("Testing if content is the same", optimisticContent.similar(new JSONObject(SaveHandler.readFromFile(optimisticFile))));
    }

    /**
     * test if items can be successfully removed and saved.
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemRemoval() throws IOException {
        final var testSavedItemsModel = new SavedItemsModel(
                List.of("Apple", "Cherries", "Watermelons", "Baking Soda"),
                System.currentTimeMillis()
        );

        SaveHandler.saveItems(testSavedItemsModel);
        final var saveTime = SaveHandler.removeItem("Baking Soda");
        final var optimisticFile = new File("./data/items.json");
        final var optimisticContent = new JSONObject()
                .put("items", List.of("Apple", "Cherries", "Watermelons"))
                .put("last_saved", saveTime);
        assertNotNull(optimisticFile);
        assertTrue("Testing if content is the same", optimisticContent.similar(new JSONObject(SaveHandler.readFromFile(optimisticFile))));
    }

    /**
     * Test if items can be successfully cleared and saved.
     * @throws IOException Thrown if the file cannot be accessed/created.
     */
    @Test
    public void testItemClear() throws IOException {
        final var testSavedItemsModel = new SavedItemsModel(
                List.of("Apple", "Cherries", "Watermelons", "Baking Soda"),
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
