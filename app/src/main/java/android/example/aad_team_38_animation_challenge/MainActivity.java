package android.example.aad_team_38_animation_challenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WordAdapter.OnWordListener {
    private List<Word> mWordList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user has seen the onboarding screen using shared preference
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart", true);

        if (firstStart) {
            // Start the onboarding activity
            Intent onboarding_activity = new Intent(this, activity_onboarding.class);
            startActivity(onboarding_activity);
        }

        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new WordAdapter(mWordList, this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        prepareWordsData();
    }

    private void prepareWordsData() {
        Word word = new Word("indebted", "owing money: heavily indebted countries.");
        mWordList.add(word);

        word = new Word("disclaimer", "a statement that denies something, especially responsibility: the novel carries a disclaimer about the characters bearing no relation to living persons.");
        mWordList.add(word);

        word = new Word("corky", "resembling cork: corky lesions on apples.");
        mWordList.add(word);

        word = new Word("purist", "a person who insists on absolute adherence to traditional rules or structures, especially in language or style: the production has yet to offend Gilbert and Sullivan purists | [as modifier] : purist fans of the original comic strip.");
        mWordList.add(word);

        word = new Word("inept", "having or showing no skill; clumsy: the referee's inept handling of the match.");
        mWordList.add(word);

        word = new Word("contradiction", "a combination of statements, ideas, or features which are opposed to one another: the proposed new system suffers from a set of internal contradictions.");
        mWordList.add(word);

        word = new Word("invisibility", "inability to be seen: I feel like I'm wearing a cloak of invisibility.");
        mWordList.add(word);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemClicked(int position) {
        Word word = mWordList.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("word_title", word.getWord());
        intent.putExtra("word_definition", word.getDefinition());
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Done?")
                .setIcon(R.drawable.close_icon)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
