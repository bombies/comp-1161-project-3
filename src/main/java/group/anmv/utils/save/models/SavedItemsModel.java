package group.anmv.utils.save.models;

import java.util.ArrayList;
import java.util.List;

public class SavedItemsModel {
    private List<String> items;
    private long last_saved;

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

    public SavedItemsModel addItem(String item) {
        if (items == null)
            items = new ArrayList<>();
        items.add(item);
        return this;
    }

    public void updateLastSaved(long time) {
        last_saved = time;
    }
}
