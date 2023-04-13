package group.anmv.utils.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import group.anmv.utils.save.models.SavedItemsModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class that will handle all persistent-storage
 * operations. All the methods in this class are static to
 * limit the use of heap-memory being used.
 */
public class SaveHandler {
    /**
     * The ObjectMapper that will be used to facilitate
     * serialization and deserialization of Plain-Old Java Objects (POJOs).
     * This will facilitate a developer friendly interface of updating
     * the data for the saved items.
     */
    private final static ObjectMapper mapper = new ObjectMapper();
    /**
     * The directory all data will be placed.
     */
    private final static String DIRECTORY = "./data/";
    /**
     * The file name for saved items.
     */
    private final static String SAVED_ITEMS_FILE = DIRECTORY + "items.json";

    /**
     * This method accepts a list of items and saves them to the save file.
     * Using this method will overwrite any save data that may have been in
     * a previous save file.
     * @param items The list of items to save
     * @return The time at which the save was made
     * @throws IOException Thrown if there is an error attempting to create
     *                      the save directory or writing to the file.
     */
    public static long saveItems(List<String> items) throws IOException {
        final var saveTime = System.currentTimeMillis();
        final var model = new SavedItemsModel(items, saveTime);
        final var saveDirectory = Path.of(DIRECTORY);
        if (!Files.exists(saveDirectory))
            Files.createDirectory(saveDirectory);

        final var modelJson = mapper.writeValueAsString(model);
        writeToFile(new File(SAVED_ITEMS_FILE), modelJson);
        return saveTime;
    }

    /**
     * This method accepts the SavedItemsModel object as parses
     * each attribute to a corresponding JSON entry. It then
     * saves the parsed data to the save file. This method overwrites
     * any data that may have been in a previous save file.
     * @param model The model to save
     * @throws IOException Thrown if there is an error attempting to create
     *                     the save directory or writing to the file.
     */
    public static void saveItems(SavedItemsModel model) throws IOException {
        final var saveDirectory = Path.of(DIRECTORY);
        if (!Files.exists(saveDirectory))
            Files.createDirectory(saveDirectory);

        final var modelJson = mapper.writeValueAsString(model);
        writeToFile(new File(SAVED_ITEMS_FILE), modelJson);
    }

    /**
     * This method is used to append a singular item to the
     * list of saved items.
     * @param item The item to add
     * @return The time at which the save was made
     * @throws IOException Thrown if there is an error attempting to create
     *                      the save directory or writing to the file.
     */
    public static long appendItem(String item) throws IOException {
        final var saveTime = System.currentTimeMillis();
        var currentSave = getSave();

        if (currentSave == null)
            currentSave = new SavedItemsModel(
                    new ArrayList<>(List.of(item)),
                    saveTime
            );
        else currentSave.addItem(item).updateLastSaved(saveTime);

        saveItems(currentSave);
        return saveTime;
    }

    /**
     * This method is used to clear all items from the save file.
     * @return The time at which the save was made
     * @throws IOException Thrown if there is an error attempting to create
     *                      the save directory or writing to the file.
     */
    public static long clearItems() throws IOException {
        return saveItems(List.of());
    }

    /**
     * This method removes a specific item from the list
     * @param item The item to remove
     * @return The time at which the save was made
     * @throws IOException Thrown if there is an error attempting to create
     *                      the save directory or writing to the file.
     * @throws IllegalArgumentException Thrown if there is no item that matches
     *                                  the item passed.
     */
    public static long removeItem(String item) throws IOException {
        final var currentSave = getSave();
        if (currentSave == null)
            throw new IllegalArgumentException("There is nothing to remove!");
        return saveItems(
                currentSave.getItems()
                        .stream()
                        .filter(i -> !i.equals(item))
                        .toList()
        );
    }

    /**
     * Get the currently saved information
     * @return An object-wrapper of the saved information.
     *          If there is no information null will be returned.
     */
    public static SavedItemsModel getSave() {
        try {
            final var save = readFromFile(new File(SAVED_ITEMS_FILE));
            return mapper.readValue(save, SavedItemsModel.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Write specific content to a file. This method
     * will overwrite any content previously in the file.
     * @param file The file to write content to
     * @param content The content to write to the file.
     * @throws IOException Thrown if there is an error attempting to write to the file
     */
    private static void writeToFile(final File file, final String content) throws IOException {
        if (!file.exists())
            file.createNewFile();

        FileWriter writer = new FileWriter(file, false);
        writer.write(content);
        writer.close();
    }

    /**
     * Read information from a specific file
     * @param file The file to read information from
     * @return A string representation of the data in the file.
     * @throws IOException Thrown if there is an error reading from the file.
     */
    public static String readFromFile(final File file) throws IOException {
        String ret = new String(Files.readAllBytes(file.toPath()));
        return ret.replaceAll("\t\n", "");
    }
}
