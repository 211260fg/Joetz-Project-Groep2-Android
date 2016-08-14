package groep2.joetz.com.joetz_project_groep2_test.repository;

import java.util.ArrayList;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.loader.ContactsLoader;
import groep2.joetz.com.joetz_project_groep2_test.loader.ItemLoader;
import groep2.joetz.com.joetz_project_groep2_test.loader.LoginLoader;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vakantie;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Repository {

    private static List<OnItemsLoadedListener> itemlisteners = new ArrayList<>();
    private static List<OnLoggedInListener> loginlisteners = new ArrayList<>();
    private static List<OnContactsLoadedListener> contactslisteners = new ArrayList<>();

    private static List<Vakantie> items = new ArrayList<>();
    private static List<User> contacts = new ArrayList<>();

    //---------------------------//

    public static List<Vakantie> getItems() {
        return items;
    }

    public static List<User> getContacts() {
        return contacts;
    }

    public static User getCurrentUser() {
        return UserSessionManager.getCurrentUser();
    }

    //--------------------------//

    public static void registerListener(OnItemsLoadedListener listener){
        if(!itemlisteners.contains(listener))
            itemlisteners.add(listener);
    }

    public void removeListener(OnItemsLoadedListener listener){
        if(itemlisteners.contains(listener))
            itemlisteners.remove(listener);
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

    public static void registerContactsListener(OnContactsLoadedListener listener){
        if(!contactslisteners.contains(listener))
            contactslisteners.add(listener);
    }

    public void removeListener(OnContactsLoadedListener listener){
        if(contactslisteners.contains(listener))
            contactslisteners.remove(listener);
    }

    //---------------------------//

    public static void loadItems(){
        items= new ArrayList<>();
        new ItemLoader();
    }

    //--------------------------//

    public static void loadContacts(){
        contacts= new ArrayList<>();
        new ContactsLoader();
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


    public static void onContactsLoaded(List<User> contacts){
        Repository.contacts=contacts;
        notifyListenersContactsLoaded();
    }

    public static void onContactsLoadFailed(){
        notifyListenersContactsLoadFailed();
    }

    //--------------------------//


    public static void onLoginSuccess(User user){
        //UserSessionManager.saveCurrentUser(user);
        for(OnLoggedInListener listener: loginlisteners){
            listener.onLoginSuccess(user);
        }
    }

    public static void onLoginFailed(){
        for(OnLoggedInListener listener: loginlisteners){
            listener.onLoginFailed();
        }
    }


    //--------------------------//

    private static void notifyListenersItemsLoaded(){
        for(OnItemsLoadedListener listener: itemlisteners){
            listener.onItemsLoaded();
        }
    }
    private static void notifyListenersLoadFailed(){
        for(OnItemsLoadedListener listener: itemlisteners){
            listener.onLoadFailed();
        }
    }
    private static void notifyListenersItemAdded(){
        for(OnItemsLoadedListener listener: itemlisteners){
            listener.onItemAdded();
        }
    }
    private static void notifyListenersItemDeleted(){
        for(OnItemsLoadedListener listener: itemlisteners){
            listener.onItemDeleted();
        }
    }


    //--------------------------//

    private static void notifyListenersContactsLoaded(){
        for(OnContactsLoadedListener listener: contactslisteners){
            listener.onContactsLoaded();
        }
    }
    private static void notifyListenersContactsLoadFailed(){
        for(OnContactsLoadedListener listener: contactslisteners){
            listener.onLoadFailed();
        }
    }


}
