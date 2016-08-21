package groep2.joetz.com.joetz_project_groep2_test.rest;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.loader.ContactsLoader;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vacation;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Florian on 14/08/2016.
 */
public class ContactsCallback implements Callback<List<User>> {


    private ContactsLoader contactsLoader;

    private List<Call<List<Vacation>>> calls;
    private int index;
    RestClient restClient;

    public ContactsCallback(ContactsLoader contactsLoader) {
        this.contactsLoader = contactsLoader;
        try{
            HashMap<String, String> user = UserSessionManager.getUserDetails();
            restClient = new RestClient(user.get(UserSessionManager.KEY_NAME), user.get(UserSessionManager.KEY_PASSWORD));
        }
        catch(NullPointerException ignored){
        }
    }

    public void getContacts(){
        restClient.getContactsClient().getContacts().enqueue(this);
    }


    @Override
    public void onResponse(Response<List<User>> response) {
        if(response.isSuccess()){
            Log.w("response", "Successful!");
            if (contactsLoader != null)
                contactsLoader.onContactsLoaded(response.body());

        }else{
            try {

                Log.d("response unsuccessful", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            contactsLoader.onLoadFailed();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        //stuur een melding naar de loader dat geen verbinding kon gemaakt worden (meestal omdat er geen internet is)
        Log.e("no response", t.getLocalizedMessage());
        t.printStackTrace();
        contactsLoader.onLoadFailed();
    }
}
