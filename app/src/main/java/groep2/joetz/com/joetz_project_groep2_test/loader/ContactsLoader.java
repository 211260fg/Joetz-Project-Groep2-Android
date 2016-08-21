package groep2.joetz.com.joetz_project_groep2_test.loader;

import java.util.ArrayList;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vacation;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.rest.ContactsCallback;

/**
 * Created by Florian on 14/08/2016.
 */
public class ContactsLoader {
    private ContactsCallback callback;
    private List<Vacation> items;


    public ContactsLoader(){
        items =new ArrayList<>();
        this.callback = new ContactsCallback(this);
        callback.getContacts();
    }

    public void onContactsLoaded(List<User> contacts) {
        Repository.onContactsLoaded(contacts);
    }

    public void onLoadFailed(){
        Repository.onContactsLoadFailed();
    }

}