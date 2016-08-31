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


    private RestClient restClient;

    public LoginCallback(LoginLoader loader) {
        this.loader=loader;
        restClient = new RestClient("", "");
        /*RestClient.UserApiInterface service = restClient.getUserClient();
        Call<User> userCall = service.getUser();
        userCall.enqueue(this);*/
    }

    public void login(String email, String password){
        restClient.getUserClient().login(email, password).enqueue(this);
    }

    public void register(User user, String password){
        restClient.getUserClient().register(user.getEmail(),password, "member", "contact", "parent",
                user.getParticipant(), "emergency", "info").enqueue(this);
    }

    public void modify(User user){
        restClient.getUserClient().modify(user.getId(), "member", "contact", "parent",
                user.getParticipant(), "emergency", "info").enqueue(this);
    }


    @Override
    public void onResponse(Response<User> response) {

        if(response.isSuccess()){
            loader.onUserLoggedIn(response.body());
        }
        else{
            try {
                Log.d("auth response fail", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            loader.onLoginFailed();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        loader.onLoginFailed();
        Log.d("user login failed", t.getMessage());
    }
}
