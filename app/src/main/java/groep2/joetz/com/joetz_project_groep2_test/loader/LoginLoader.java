package groep2.joetz.com.joetz_project_groep2_test.loader;

import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.rest.LoginCallback;

/**
 * Created by floriangoeteyn on 18-May-16.
 */
public class LoginLoader {

    LoginCallback callback;

    public LoginLoader(){
        this.callback = new LoginCallback(this);
    }

    public void login(String username, String password){
        callback.login(username, password);
    }

    public void register(User user, String password){
        callback.register(user, password);
    }

    public void modify(User user){
        callback.modify(user);
    }
    public void onUserLoggedIn(User user){
        Repository.onLoginSuccess(user);
    }

    public void onLoginFailed(){
        Repository.onLoginFailed();
    }

}
