package groep2.joetz.com.joetz_project_groep2_test.repository;

import groep2.joetz.com.joetz_project_groep2_test.model.User;

/**
 * Created by floriangoeteyn on 12-Jul-16.
 */
public interface OnLoggedInListener {

    public void onLoginSuccess(User user);

    public void onLoginFailed();

}
