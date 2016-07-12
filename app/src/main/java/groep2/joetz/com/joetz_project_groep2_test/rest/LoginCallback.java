package groep2.joetz.com.joetz_project_groep2_test.rest;

import android.util.Log;

import com.squareup.okhttp.Headers;

import java.io.IOException;

import groep2.joetz.com.joetz_project_groep2_test.loader.LoginLoader;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by floriangoeteyn on 04-Apr-16.
 */
public class LoginCallback implements Callback<User> {

    private LoginLoader loader;

    public LoginCallback(LoginLoader loader, String username, String password) {
        this.loader=loader;
        RestClient restClient = new RestClient(username, password);
        RestClient.UserApiInterface service = restClient.getUserClient();
        Call<User> userCall = service.getUser();
        userCall.enqueue(this);
    }


    @Override
    public void onResponse(Response<User> response) {

        if(response.isSuccess()){
            loader.onUserLoggedIn(response.body());
        }
        else{
            loader.onLoginFailed();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        loader.onLoginFailed();
        Log.d("user login failed", t.getMessage());
    }
}
