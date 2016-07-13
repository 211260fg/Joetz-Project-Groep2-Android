package groep2.joetz.com.joetz_project_groep2_test.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.loader.ItemLoader;
import groep2.joetz.com.joetz_project_groep2_test.loader.LoginLoader;
import groep2.joetz.com.joetz_project_groep2_test.model.Category;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vakantie;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Repository {

    private static List<OnItemsLoadedListener> listeners = new ArrayList<>();
    private static List<OnLoggedInListener> loginlisteners = new ArrayList<>();
    private static List<Vakantie> items = new ArrayList<>();


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

    public static void registerLoginListener(OnLoggedInListener listener){
        if(!loginlisteners.contains(listener))
            loginlisteners.add(listener);
    }

    public void removeListener(OnLoggedInListener listener){
        if(loginlisteners.contains(listener))
            loginlisteners.remove(listener);
    }

    //---------------------------//

    public static void loadItems(){
        items= new ArrayList<>();
        new ItemLoader();
    }

    //--------------------------//

    public static void loginUser(String username, String password){
        new LoginLoader(username, password);
    }

    public static void logoutUser() {
        UserSessionManager.logoutUser();
        items = new ArrayList<>();
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


    public static void onLoginSuccess(User user){
        for(OnLoggedInListener listener: loginlisteners){
            listener.onLoginSuccess();
        }
    }

    public static void onLoginFailed(){
        for(OnLoggedInListener listener: loginlisteners){
            listener.onLoginFailed();
        }
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
