package android.example.aad_team_38_animation_challenge.onlineDictionary;

import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestInterface {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.datamuse.com/";



    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public interface GetDataService{
        @GET("words?ml=duck&sp=b*&max=20")
        Call<List<Words>> getJSON();
    }

}
