package groep2.joetz.com.joetz_project_groep2_test.loader;

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

    private ItemCallback callback;
    private List<Vacation> items;


    public ItemLoader(){
        items =new ArrayList<>();
        this.callback = new ItemCallback(this);
        callback.getItems();
    }

    public void onItemsLoaded(List<Vacation> items) {
        this.items=items;
        Repository.onItemsLoaded(items);
    }

    public void onLoadFailed(){
        Repository.onLoadFailed();
    }

}
