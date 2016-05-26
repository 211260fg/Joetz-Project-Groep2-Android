package groep2.joetz.com.joetz_project_groep2_test.repository;

import java.util.List;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Repository {

    private static List<OnItemsLoadedListener> listeners;
    private static List<Object> items;


    //---------------------------//

    public static List<Object> getItems() {
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

    public static void onItemsLoaded(List<Object> items){
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
