package android.example.aad_team_38_animation_challenge.onlineDictionary.Model;

import java.util.List;

public class Senses {
    private List<String> definitions;

    private String id;

    public void setDefinitions(List<String> definitions){
        this.definitions = definitions;
    }
    public List<String> getDefinitions(){
        return this.definitions;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

}
