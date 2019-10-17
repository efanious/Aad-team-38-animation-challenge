package android.example.aad_team_38_animation_challenge.onlineDictionary;

import com.google.gson.annotations.SerializedName;

public class Words {
   @SerializedName("word")
    private String word;
   @SerializedName("phonetic")
    private String phonetic;
   @SerializedName("origin")
    private String origin;
   @SerializedName("meaning")
    private String meaning;

   @SerializedName("title")
   private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Words(String word, String phonetic, String origin, String meaning, String title) {
        this.word = word;
        this.phonetic = phonetic;
        this.origin = origin;
        this.meaning = meaning;
        this.title = title;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
