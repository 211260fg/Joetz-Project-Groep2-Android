package groep2.joetz.com.joetz_project_groep2_test.fragments;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public interface OnFragmentInteractionListener {
    // TODO: gebruik fragment tag ipv enum
    void onFragmentInteraction(InteractedFragment interactedFragment, int pos);

    enum InteractedFragment{
        AUTHENTICATION, CONTACTS, VACATIONS
    }

}
