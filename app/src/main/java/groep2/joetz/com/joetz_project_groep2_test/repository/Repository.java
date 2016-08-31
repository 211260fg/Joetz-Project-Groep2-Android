package groep2.joetz.com.joetz_project_groep2_test.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.loader.ContactsLoader;
import groep2.joetz.com.joetz_project_groep2_test.loader.ItemLoader;
import groep2.joetz.com.joetz_project_groep2_test.loader.LoginLoader;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vacation;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Repository {

    private static List<OnItemsLoadedListener> itemlisteners = new ArrayList<>();
    private static List<OnLoggedInListener> loginlisteners = new ArrayList<>();
    private static List<OnContactsLoadedListener> contactslisteners = new ArrayList<>();

    private static List<Vacation> items = new ArrayList<>();
    private static List<Vacation> history = new ArrayList<>();
    private static List<User> contacts = new ArrayList<>();

    private static List<Vacation> filtered_age = new ArrayList<>();
    private static List<Vacation> filtered_period = new ArrayList<>();
    private static List<Vacation> filtered_extra = new ArrayList<>();

    private static ItemLoader itemLoader;
    private static LoginLoader loginLoader;
    private static ContactsLoader contactsLoader;


    //---------------------------//

    static {
        itemLoader = new ItemLoader();
        loginLoader = new LoginLoader();
        contactsLoader = new ContactsLoader();
    }

    //---------------------------//

    public static List<Vacation> getItems() {
        return items;
    }

    public static List<Vacation> getHistory() {
        return history;
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

    public static void removeListener(OnItemsLoadedListener listener){
        if(itemlisteners.contains(listener))
            itemlisteners.remove(listener);
    }

    //---------------------------//

    public static void registerLoginListener(OnLoggedInListener listener){
        if(!loginlisteners.contains(listener))
            loginlisteners.add(listener);
    }

    public static void removeListener(OnLoggedInListener listener){
        if(loginlisteners.contains(listener))
            loginlisteners.remove(listener);
    }

    //---------------------------//

    public static void registerContactsListener(OnContactsLoadedListener listener){
        if(!contactslisteners.contains(listener))
            contactslisteners.add(listener);
    }

    public static void removeListener(OnContactsLoadedListener listener){
        if(contactslisteners.contains(listener))
            contactslisteners.remove(listener);
    }

    //---------------------------//

    public static void loadItems(){
        items= new ArrayList<>();
        initFilters();
        itemLoader.loadItems();
    }

    //---------------------------//

    public static void loadHistory(){
        history= new ArrayList<>();
        itemLoader.loadHistory();
    }


    //--------------------------//

    public static void loadContacts(){
        contacts= new ArrayList<>();
        contactsLoader.loadContacts();
    }

    //--------------------------//

    public static void registerUser(User user, String password){
        loginLoader.register(user, password);
    }

    public static void loginUser(String username, String password){
        loginLoader.login(username, password);
    }

    public static void modifyUser(User user){
        loginLoader.modify(user);
    }

    public static void logoutUser() {
        UserSessionManager.logoutUser();
        items = new ArrayList<>();
        initFilters();
    }

    //--------------------------//

    public static void onHistoryLoaded(List<Vacation> items){
        Log.d("history repository", "size: "+items.size());
        Repository.history=items;
        notifyListenersItemsLoaded();
    }

    //--------------------------//

    public static void onItemsLoaded(List<Vacation> items){
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


    //--------------------------//

    private static void initFilters(){
        filtered_age=new ArrayList<>();
        filtered_extra=new ArrayList<>();
        filtered_period=new ArrayList<>();
    }

    public static void filter(int leeftijd, String periode, String extra){

        List<Vacation> tempList = new ArrayList<>();

        if(leeftijd>-1){
            for(Vacation v: filtered_age){
                if(v.getMinAge()>leeftijd||v.getMaxAge()<leeftijd){
                    items.add(v);
                    tempList.add(v);
                }
            }
            for(Vacation v: items){
                if(v.getMinAge()>leeftijd||v.getMaxAge()<leeftijd){
                    filtered_age.add(v);
                }
            }
            filtered_age.removeAll(tempList);
            items.removeAll(filtered_age);

        }else if(periode!=null){

        }else if(extra!=null){

        }
        notifyListenersItemsLoaded();
    }


}
