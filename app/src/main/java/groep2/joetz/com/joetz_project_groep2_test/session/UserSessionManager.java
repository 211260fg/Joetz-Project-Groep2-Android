package groep2.joetz.com.joetz_project_groep2_test.session;

import android.content.Context;
import android.content.Intent;


import com.google.gson.Gson;

import java.util.HashMap;

import groep2.joetz.com.joetz_project_groep2_test.activities.AuthenticationActivity;
import groep2.joetz.com.joetz_project_groep2_test.model.User;

/**
 * Created by floriangoeteyn on 04-Apr-16.
 */
public class UserSessionManager {

    //Secured Preferences reference
    private static SecurePreferences secpref;

    private Gson gson;

    // Context
    static Context _context;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";


    private static final String KEY_CURRENT_USER = "currentuser";


    // Constructor
    public UserSessionManager(Context context) {
        _context = context;
        secpref = new SecurePreferences(context, PREFER_NAME, "secret", true);
    }


    //Create login session
    public void createUserLoginSession(String name, String email) {
        // Storing login value as TRUE
        secpref.put(IS_USER_LOGIN, true);

        // Storing name in pref
        secpref.put(KEY_NAME, name);

        // Storing icon_email in pref
        secpref.put(KEY_PASSWORD, email);

        // commit changes
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, AuthenticationActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Starting Login Activity
            _context.startActivity(i);

            return false;
        }
        return true;
    }


    /**
     * Get stored session data
     */
    public static HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<>();

        // user name
        user.put(KEY_NAME, secpref.getString(KEY_NAME));


        // user icon_email id
        user.put(KEY_PASSWORD, secpref.getString(KEY_PASSWORD));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public static void logoutUser() {

        // Clearing all user data from Shared Preferences
        secpref.clear();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, AuthenticationActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return secpref.getBoolean(IS_USER_LOGIN);
        //return false;
    }


    public static void saveCurrentUser(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        secpref.put(KEY_CURRENT_USER, json);
    }

    public static User getCurrentUser(){
        Gson gson = new Gson();
        String json = secpref.getString(KEY_CURRENT_USER);
        User currentuser = gson.fromJson(json, User.class);

        return currentuser;
    }
}
