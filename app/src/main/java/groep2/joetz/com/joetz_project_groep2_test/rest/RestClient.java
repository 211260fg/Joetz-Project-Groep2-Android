package groep2.joetz.com.joetz_project_groep2_test.rest;

/**
 * Created by floriangoeteyn on 26-May-16.
 */

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.model.Vakantie;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
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


    public List<Call<List<Vakantie>>> getItemCalls(){
        List<Call<List<Vakantie>>> calls = new ArrayList<>();
        calls.add(getItemClient().getVakanties());
        return calls;
    }


    public interface ItemApiInterface{

        @GET(Values.URL_VACATIONS)
        Call<List<Vakantie>> getVakanties();
    }

    public interface UserApiInterface{
        @GET(Values.URL_USER)
        Call<User> getUser();
    }


}
