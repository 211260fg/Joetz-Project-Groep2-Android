package groep2.joetz.com.joetz_project_groep2_test.rest;

/**
 * Created by floriangoeteyn on 26-May-16.
 */

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vacation;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;


public class RestClient {

    private static Retrofit client;
    private String baseurl="https://api.myjson.com/bins";

    public RestClient(String username, String password){
        client = createClient();
    }

    public RestClient(){
        client = createClient();
    }

    public UserApiInterface getUserClient(){
        return client.create(UserApiInterface.class);
    }

    public ItemApiInterface getItemClient(){
        return client.create(ItemApiInterface.class);
    }

    public ContactsApiInterface getContactsClient(){
        return client.create(ContactsApiInterface.class);
    }

    private Retrofit createClient(){

        OkHttpClient okClient = new OkHttpClient();

        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                /*final String credentials = username + ":" + password;
                final String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                final String basic = "Basic " + encodedCredentials;
                */
                Request request = chain.request();
                request = request.newBuilder()
                        //.addHeader("Authorization", basic)
                        .build();
                Response response = chain.proceed(request);

                //Log.w("Retrofit@Response", response.body().string());

                return response;
            }
        });

        return new Retrofit.Builder()
                .baseUrl(Values.BASE_URL)
                .addConverter(String.class, new ToStringConverter())
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public List<Call<List<Vacation>>> getItemCalls(){
        List<Call<List<Vacation>>> calls = new ArrayList<>();
        calls.add(getItemClient().getVakanties());
        return calls;
    }





    public interface ItemApiInterface{

        @GET(Values.URL_VACATIONS)
        Call<List<Vacation>> getVakanties();
    }

    public interface UserApiInterface{
        @GET(Values.URL_USER)
        Call<User> getUser();
    }

    public interface ContactsApiInterface{
        @GET(Values.URL_CONTACTS)
        Call<List<User>> getContacts();
    }


}
