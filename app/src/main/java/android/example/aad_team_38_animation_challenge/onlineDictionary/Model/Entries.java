package android.example.aad_team_38_animation_challenge.onlineDictionary.Model;

import java.util.List;

public class Entries {
    private String homographNumber;

    private List<Senses> senses;

    public void setHomographNumber(String homographNumber){
        this.homographNumber = homographNumber;
    }
    public String getHomographNumber(){
        return this.homographNumber;
    }
    public void setSenses(List<Senses> senses){
        this.senses = senses;
    }
    public List<Senses> getSenses(){
        return this.senses;
    }

}
