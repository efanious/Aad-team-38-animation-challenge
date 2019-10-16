package android.example.aad_team_38_animation_challenge;

public class Word {

    private String word, definition;

    public Word() {

    }

    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
