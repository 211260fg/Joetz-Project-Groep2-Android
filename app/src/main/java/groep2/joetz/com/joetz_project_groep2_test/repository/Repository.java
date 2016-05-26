package groep2.joetz.com.joetz_project_groep2_test.repository;

import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.model.Vakantie;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Repository {

    private static List<OnItemsLoadedListener> listeners;
    private static List<Vakantie> items;


    //---------------------------//

    public static List<Vakantie> getItems() {
        return items;
    }

    //--------------------------//

    public void registerListener(OnItemsLoadedListener listener){
        if(!listeners.contains(listener))
            listeners.add(listener);
    }

    public void removeListener(OnItemsLoadedListener listener){
        if(listeners.contains(listener))
            listeners.remove(listener);
    }

    //---------------------------//

    public static void loadItems(){

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
