package android.example.aad_team_38_animation_challenge.onlineDictionary.Model;

import java.util.List;

public class Results {
    private String id;

    private String language;

    private List<LexicalEntries> lexicalEntries;

    private String type;

    private String word;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return this.language;
    }
    public void setLexicalEntries(List<LexicalEntries> lexicalEntries){
        this.lexicalEntries = lexicalEntries;
    }
    public List<LexicalEntries> getLexicalEntries(){
        return this.lexicalEntries;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setWord(String word){
        this.word = word;
    }
    public String getWord(){
        return this.word;
    }
}
