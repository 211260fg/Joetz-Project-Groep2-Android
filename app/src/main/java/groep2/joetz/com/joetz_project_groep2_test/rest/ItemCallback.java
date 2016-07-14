package groep2.joetz.com.joetz_project_groep2_test.rest;

import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.loader.ItemLoader;
import groep2.joetz.com.joetz_project_groep2_test.model.Vakantie;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by floriangoeteyn on 17-Mar-16.
 */
public class ItemCallback implements Callback<List<Vakantie>> {

    private ItemLoader itemLoader;

    private List<Call<List<Vakantie>>> calls;
    private int index;
    RestClient restClient;

    public ItemCallback(ItemLoader itemLoader) {
        this.itemLoader = itemLoader;
        try{
            HashMap<String, String> user = UserSessionManager.getUserDetails();
            restClient = new RestClient(user.get(UserSessionManager.KEY_NAME), user.get(UserSessionManager.KEY_EMAIL));
        }
        catch(NullPointerException ignored){
        }
    }

    public void getItems(){
        calls = new ArrayList<>();
        index = 0;
        try {
            calls= restClient.getItemCalls();
        }
        catch(NullPointerException ne){
            ne.printStackTrace();
        }
        startCallback();
    }


    private void startCallback() {
        if (!(index >= calls.size())) {
            calls.get(index).enqueue(this);
            index++;
        }
    }


    @Override
    public void onResponse(Response<List<Vakantie>> response) {
        if(response.isSuccess()){
            Log.w("response", "Successful!");
            if (itemLoader != null)
                itemLoader.onItemsLoaded(response.body());

        }else{
            try {
                Log.d("response unsuccessful", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            itemLoader.onLoadFailed();
        }

        startCallback();
    }

    @Override
    public void onFailure(Throwable t) {
        //stuur een melding naar de loader dat geen verbinding kon gemaakt worden (meestal omdat er geen internet is)
        Log.e("no response", t.getLocalizedMessage());
        t.printStackTrace();
        itemLoader.onLoadFailed();
        startCallback();
    }

}