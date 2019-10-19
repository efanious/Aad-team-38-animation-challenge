package android.example.aad_team_38_animation_challenge.onlineDictionary.Model;

import java.util.List;

public class Root {
    private String id;

    private Metadata metadata;

    private List<Results> results;

    private String word;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setMetadata(Metadata metadata){
        this.metadata = metadata;
    }
    public Metadata getMetadata(){
        return this.metadata;
    }
    public void setResults(List<Results> results){
        this.results = results;
    }
    public List<Results> getResults(){
        return this.results;
    }
    public void setWord(String word){
        this.word = word;
    }
    public String getWord(){
        return this.word;
    }
}
