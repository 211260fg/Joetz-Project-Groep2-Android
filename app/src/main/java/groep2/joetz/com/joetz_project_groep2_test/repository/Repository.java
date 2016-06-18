package groep2.joetz.com.joetz_project_groep2_test.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.model.Category;
import groep2.joetz.com.joetz_project_groep2_test.model.Vakantie;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Repository {

    private static List<OnItemsLoadedListener> listeners = new ArrayList<>();
    private static List<Vakantie> items = new ArrayList<>();

    //---------------------------//

    public static void loadTestData(String json){

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Vakantie>>() {}.getType();
        List<Vakantie> testdata = gson.fromJson(json, listType);

        onItemsLoaded(testdata);
    }

    //---------------------------//

    public static List<Vakantie> getItems() {
        return items;
    }

    //--------------------------//

    public static void registerListener(OnItemsLoadedListener listener){
        if(!listeners.contains(listener))
            listeners.add(listener);
    }

    public void removeListener(OnItemsLoadedListener listener){
        if(listeners.contains(listener))
            listeners.remove(listener);
    }

    //---------------------------//

    //TODO uitwerken
    public static void loadItems(){

        onItemsLoaded(items);
    }

    //--------------------------//

    public static void onItemsLoaded(List<Vakantie> items){
        Repository.items=items;
        notifyListenersItemsLoaded();
    }

    public static void onLoadFailed(){
        notifyListenersLoadFailed();
    }

    //--------------------------//

    private static void notifyListenersItemsLoaded(){
        for(OnItemsLoadedListener listener: listeners){
            listener.onItemsLoaded();
        }
    }
    private static void notifyListenersLoadFailed(){
        for(OnItemsLoadedListener listener: listeners){
            listener.onLoadFailed();
        }
    }
    private static void notifyListenersItemAdded(){
        for(OnItemsLoadedListener listener: listeners){
            listener.onItemAdded();
        }
    }
    private static void notifyListenersItemDeleted(){
        for(OnItemsLoadedListener listener: listeners){
            listener.onItemDeleted();
        }
    }


}
