package groep2.joetz.com.joetz_project_groep2_test.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by floriangoeteyn on 12-Jul-16.
 */
public class User {

    private String _id;
    private String email;
    private Object participant;
    private String profileimage;

    private String firstname="";
    private String lastname="";

    public User(String email, String firstname, String lastname, String profileimage) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profileimage = profileimage;
    }

    public User(String email, Participant participant){
        this.email=email;
        this.participant=participant;
    }

    public String getId() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    /*public Participant getParticipant() {
        return participant;
    }*/

    public String getProfileimage() {
        if(profileimage==null)
            return "http://thesocialmediamonthly.com/wp-content/uploads/2015/08/photo.png";
        return profileimage;
    }

    public String getFirstname() {
        if(participant!=null&&participant instanceof Participant)
            return ((Participant) (participant)).getVoornaam();
        else return firstname;
    }

    public String getLastname() {
        if(participant!=null&&participant instanceof Participant)
            return ((Participant) (participant)).getNaam();
        else return lastname;
    }

    public Participant getParticipant() {
        if(participant instanceof Participant)
            return (Participant) (participant);
        return null;
    }

    /*private String _id;
    private String email;
    private String firstname;
    private String lastname;
    private String profileimage;
    private List<Vacation> history;
    private String function = "user";

    public User(String email, String firstname, String lastname, String profileimage) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profileimage = profileimage;
    }

    public String getId() {
        return _id;
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
        if(profileimage==null)
            return "http://thesocialmediamonthly.com/wp-content/uploads/2015/08/photo.png";
        return profileimage;
    }

    public List<Vacation> getHistory() {
        if(history==null)
            history=new ArrayList<>();

        return history;
    }

    public String getFunction() {
        return function;
    }*/


    public static class Participant{
        private Long nationalIdentificationNumber;
        private String voornaam;
        private String naam;
        private String geboortedatum;
        private String straat;
        private String huisnummerEnbus;
        private String gemeente;
        private String postcode;

        public Participant(String voornaam, String familienaam){
            this.voornaam=voornaam;
            this.naam=familienaam;
        }

        public Long getNationalIdentificationNumber() {
            return nationalIdentificationNumber;
        }

        public String getVoornaam() {
            return voornaam;
        }

        public String getNaam() {
            return naam;
        }

        public String getGeboortedatum() {
            return geboortedatum;
        }

        public String getStraat() {
            return straat;
        }

        public String getHuisnummerEnbus() {
            return huisnummerEnbus;
        }

        public String getGemeente() {
            return gemeente;
        }

        public String getPostcode() {
            return postcode;
        }
    }
}
