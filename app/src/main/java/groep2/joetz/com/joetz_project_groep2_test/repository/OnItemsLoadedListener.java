package groep2.joetz.com.joetz_project_groep2_test.repository;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public interface OnItemsLoadedListener {
    void onItemsLoaded();
    void onLoadFailed();
    void onItemAdded();
    void onItemDeleted();
}
