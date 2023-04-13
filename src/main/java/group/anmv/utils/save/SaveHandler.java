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

public class SaveHandler {
    private final static ObjectMapper mapper = new ObjectMapper();
    private final static String DIRECTORY = "./data/";
    private final static String SAVED_ITEMS_FILE = DIRECTORY + "items.json";

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

    public static void saveItems(SavedItemsModel model) throws IOException {
        final var saveDirectory = Path.of(DIRECTORY);
        if (!Files.exists(saveDirectory))
            Files.createDirectory(saveDirectory);

        final var modelJson = mapper.writeValueAsString(model);
        writeToFile(new File(SAVED_ITEMS_FILE), modelJson);
    }

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

    public static long clearItems() throws IOException {
        return saveItems(List.of());
    }

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

    public static SavedItemsModel getSave() {
        try {
            final var save = readFromFile(new File(SAVED_ITEMS_FILE));
            return mapper.readValue(save, SavedItemsModel.class);
        } catch (IOException e) {
            return null;
        }
    }

    private static void writeToFile(final File file, final String content) throws IOException {
        if (!file.exists())
            file.createNewFile();

        FileWriter writer = new FileWriter(file, false);
        writer.write(content);
        writer.close();
    }

    public static String readFromFile(final File file) throws IOException {
        String ret = new String(Files.readAllBytes(file.toPath()));
        return ret.replaceAll("\t\n", "");
    }
}
