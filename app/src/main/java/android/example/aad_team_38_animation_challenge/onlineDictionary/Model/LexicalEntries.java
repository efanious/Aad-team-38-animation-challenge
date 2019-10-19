package android.example.aad_team_38_animation_challenge.onlineDictionary.Model;

import java.util.List;

public class LexicalEntries {
    private List<Entries> entries;

    private String language;

    private LexicalCategory lexicalCategory;

    private String text;

    public void setEntries(List<Entries> entries){
        this.entries = entries;
    }
    public List<Entries> getEntries(){
        return this.entries;
    }
    public void setLanguage(String language){
        this.language = language;
    }
    public String getLanguage(){
        return this.language;
    }
    public void setLexicalCategory(LexicalCategory lexicalCategory){
        this.lexicalCategory = lexicalCategory;
    }
    public LexicalCategory getLexicalCategory(){
        return this.lexicalCategory;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }

}
