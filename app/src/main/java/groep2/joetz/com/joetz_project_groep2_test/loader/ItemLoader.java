package groep2.joetz.com.joetz_project_groep2_test.loader;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.model.Vacation;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.rest.ItemCallback;

/**
 * Created by floriangoeteyn on 15-Mar-16.
 */

//klasse verantwoordelijk voor communicatie tussen REST en Repository
public class ItemLoader{

    private boolean loadHistory;

    public void loadItems(){
        this.loadHistory=false;
        ItemCallback callback = new ItemCallback(this);
        callback.getItems();
    }

    public void loadHistory(){
        this.loadHistory=true;
        ItemCallback callback = new ItemCallback(this);
        callback.getHistory();
    }

    public void onItemsLoaded(List<Vacation> items) {
        if(loadHistory) {
            Repository.onHistoryLoaded(items);
        }
        else {
            Repository.onItemsLoaded(items);
        }
    }

    public void onLoadFailed(){
        Repository.onLoadFailed();
    }

}
