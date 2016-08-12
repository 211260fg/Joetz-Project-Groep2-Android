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
    private List<Vakantie> history;
    private String function;

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

    public List<Vakantie> getHistory() {
        return history;
    }

    public String getFunction() {
        return function;
    }
}
