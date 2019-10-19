package android.example.aad_team_38_animation_challenge.onlineDictionary;

import java.util.List;

public class DictionaryResult {
    private List<LexicalEntry> lexicalEntries;
    private String word;

    public List<LexicalEntry> getLexicalEntries(){
        return lexicalEntries;
    }
    public String getWord(){
        return word;
    }
}
