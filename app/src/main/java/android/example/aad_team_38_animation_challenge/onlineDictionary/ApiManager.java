package android.example.aad_team_38_animation_challenge.onlineDictionary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static final String APP_ID = "276f67d2";
    private static final String APP_KEY = "11c24c1a34f6a94feb01ee09f963f85c";

    private static ApiManager apiManager;
    private final IDictionaryApi service;

    private ApiManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://od-api.oxforddictionaries.com/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(IDictionaryApi.class);
    }

    public static ApiManager getInstance(){
        if (apiManager == null){
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void getDictionaryEntries(String word, Callback<DictionaryInfo> callback){
        Call<DictionaryInfo> dictionaryEntries = service.getDictionaryEntries(APP_ID, APP_KEY, word);
        dictionaryEntries.enqueue(callback);
    }
}
