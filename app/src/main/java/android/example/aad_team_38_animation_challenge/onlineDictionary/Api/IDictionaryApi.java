package android.example.aad_team_38_animation_challenge.onlineDictionary.Api;

import android.example.aad_team_38_animation_challenge.onlineDictionary.Model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IDictionaryApi {
    @GET("entries/en/{word}")
    Call<Root> getDictionaryEntries(@Header("app_id")String id,
                                    @Header("app_key")String key,
                                    @Path("word")String word);
}
