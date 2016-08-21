package groep2.joetz.com.joetz_project_groep2_test.model;

import java.util.List;

/**
 * Created by floriangoeteyn on 12-Jul-16.
 */
public class User {

    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String profileimage;
    private List<Vacation> history;
    private String function;

    public User(String email, String firstname, String lastname, String profileimage) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profileimage = profileimage;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public List<Vacation> getHistory() {
        return history;
    }

    public String getFunction() {
        return function;
    }
}
