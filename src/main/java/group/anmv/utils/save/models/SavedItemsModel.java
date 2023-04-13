package group.anmv.utils.save.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the wrapper object that will model
 * the data to be stored in the save file for
 * items added by the user. This model will be
 * used to facilitate the simple serialization/
 * deserialization process for saving POJOs.
 */
public class SavedItemsModel {
    private List<String> items;
    private long last_saved;

    /**
     * Default constructor to facilitate serialization
     */
    public SavedItemsModel() {
        items = null;
        last_saved = -1;
    }

    public SavedItemsModel(List<String> items, long last_saved) {
        this.items = items;
        this.last_saved = last_saved;
    }

    public List<String> getItems() {
        return items;
    }

    public long getLast_saved() {
        return last_saved;
    }

    /**
     * Add an item to the model's item list
     * @param item The item to add
     * @return This instance for object-chaining.
     */
    public SavedItemsModel addItem(String item) {
        if (items == null)
            items = new ArrayList<>();
        items.add(item);
        return this;
    }

    /**
     * Update the model's last saved time
     * @param time The time to set
     */
    public void updateLastSaved(long time) {
        last_saved = time;
    }
}
