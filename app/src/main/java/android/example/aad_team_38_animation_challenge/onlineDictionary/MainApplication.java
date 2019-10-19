package android.example.aad_team_38_animation_challenge.onlineDictionary;

import android.app.Application;
import android.example.aad_team_38_animation_challenge.onlineDictionary.Api.ApiManager;

public class MainApplication extends Application {

    public static ApiManager apiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        apiManager = ApiManager.getInstance();
    }
}
