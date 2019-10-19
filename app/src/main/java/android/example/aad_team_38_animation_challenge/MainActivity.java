package android.example.aad_team_38_animation_challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.aad_team_38_animation_challenge.onlineDictionary.DictionaryAdapter;
import android.example.aad_team_38_animation_challenge.onlineDictionary.DictionaryInfo;
import android.example.aad_team_38_animation_challenge.onlineDictionary.DictionaryResult;
import android.example.aad_team_38_animation_challenge.onlineDictionary.LexicalEntry;
import android.example.aad_team_38_animation_challenge.onlineDictionary.MainApplication;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements WordAdapter.OnWordListener, View.OnClickListener,
        Callback<DictionaryInfo> {
    private static final String TAG = "MainActivity";
    private List<Word> mWordList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    //online dictionary
    private DictionaryAdapter adapter;
    private EditText searchEditText;
    private String word;
    private TextView wordTextView;
    private ListView dictionaryEntriesListView;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //online dictionary initialized
        dictionaryEntriesListView = findViewById(R.id.list_view);
        adapter = new DictionaryAdapter(this, Collections.<LexicalEntry>emptyList());
        dictionaryEntriesListView.setAdapter(adapter);

        findViewById(R.id.search).setOnClickListener(this);

        searchEditText = findViewById(R.id.words_search);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.hide();

        wordTextView = findViewById(R.id.words_word);




        // Check if user has seen the onboarding screen using shared preference
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart", true);

        if (firstStart) {
            // Start the onboarding activity
            Intent onboarding_activity = new Intent(this, activity_onboarding.class);
            startActivity(onboarding_activity);
        }




//        mAdapter = new WordAdapter(mWordList, this);
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        mRecyclerView.setAdapter(mAdapter);

//        prepareWordsData();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                word = searchEditText.getText().toString().toLowerCase();
                progressDialog.show();
                MainApplication.apiManager.getDictionaryEntries(word, this);
                break;
        }
    }

    @Override
    public void onResponse(@NonNull Call<DictionaryInfo> call,@NonNull Response<DictionaryInfo> response) {
        progressDialog.hide();

        if (response.isSuccessful()){
            DictionaryInfo body = response.body();
            assert body != null;
            DictionaryResult dictionaryResult = body.getResults().get(0);

            wordTextView.setText(dictionaryResult.getWord());
            adapter.setLexicalEntries(dictionaryResult.getLexicalEntries());

            wordTextView.setVisibility(View.VISIBLE);
            dictionaryEntriesListView.setVisibility(View.VISIBLE);
        }else {
            dictionaryEntriesListView.setVisibility(View.GONE);
            wordTextView.setVisibility(View.GONE);
            switch (response.code()){
                case 403:
                    try {
                        Toast.makeText(this, "" + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 400:
                case 404:
                    Toast.makeText(this, "Invalid Word" + response.errorBody(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    @Override
    public void onFailure(@NonNull Call<DictionaryInfo> call, Throwable t) {
        progressDialog.hide();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Done?")
                .setIcon(R.drawable.close_icon)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
