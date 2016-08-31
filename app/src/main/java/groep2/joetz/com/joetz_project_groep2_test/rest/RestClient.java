package groep2.joetz.com.joetz_project_groep2_test.rest;

/**
 * Created by floriangoeteyn on 26-May-16.
 */

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vacation;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;


public class RestClient {

    private static Retrofit client;
    private String baseurl=Values.BASE_URL2;

    public RestClient(String username, String password){
        this.baseurl=Values.BASE_URL2;
        client = createClient();
    }

    public RestClient(){
        baseurl=Values.BASE_URL2;
        client = createClient();
    }

    public RestClient(String baseurl){
        this.baseurl=baseurl;
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

        okClient.setProtocols(Arrays.asList(Protocol.HTTP_1_1));

        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverter(String.class, new ToStringConverter())
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public List<Call<List<Vacation>>> getItemCalls(){
        List<Call<List<Vacation>>> calls = new ArrayList<>();
        calls.add(getItemClient().getVacations());
        return calls;
    }





    public interface ItemApiInterface{

        @GET(Values.URL_VACATIONS)
        Call<List<Vacation>> getVacations();

        //@GET(Values.URL_USERS+"/{userId}/history")
        //Call<List<Vacation>> getHistory(@Path("userId") String userId);
        @GET("/s/ic5qs9dycmjj4fx/historydata.json?dl=0")
        Call<List<Vacation>> getHistory();

    }

    public interface UserApiInterface{

        @GET(Values.URL_USER)
        Call<User> getUser();

        @FormUrlEncoded
        @POST(Values.URL_USER)
        Call<User> getUser(@Field("email") String email);


        @FormUrlEncoded
        @POST(Values.URL_LOGIN)
        Call<User> login(@Field("email") String email, @Field("password") String password);


        @FormUrlEncoded
        @POST(Values.URL_REGISTER)
        Call<User> register(@Field("email") String email, @Field("password") String password,
                                    @Field("member") String member, @Field("contact") String contact,
                                    @Field("parent") String parent, @Field("participant") User.Participant participant,
                                    @Field("emergencyContact") String emergencyContact,
                                    @Field("additionalInfo") String additionalInformation);

        @FormUrlEncoded
        @POST(Values.URL_MODIFY)
        Call<User> modify(@Field("userId") String userId,
                            @Field("member") String member, @Field("contact") String contact,
                            @Field("parent") String parent, @Field("participant") User.Participant participant,
                            @Field("emergencyContact") String emergencyContact,
                            @Field("additionalInformation") String additionalInformation);

    }

    public interface ContactsApiInterface{
        @GET(Values.URL_CONTACTS2)
        Call<List<User>> getContacts();
    }


}
