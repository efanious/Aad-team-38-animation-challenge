package android.example.aad_team_38_animation_challenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.aad_team_38_animation_challenge.onlineDictionary.DictionaryAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.example.aad_team_38_animation_challenge.DictionaryDatabaseContract.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOAD_RECENT_WORDS = 1;
    private List<Word> mWordList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    //online dictionary
    private DictionaryAdapter adapter;
    private EditText mSearch;
    private String mWord;
    private ListView mRecentSearch;
    private RecentSearchAdapter mRecentSearchAdapter;

    private DictionaryOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enableStrictMode();

        findViewById(R.id.search).setOnClickListener(this);

        mSearch = findViewById(R.id.words_search);
        mRecentSearch = findViewById(R.id.recent_search);

        // Check if user has seen the onboarding screen using shared preference
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart", true);

        if (firstStart) {
            // Start the onboarding activity
            Intent onboarding_activity = new Intent(this, activity_onboarding.class);
            startActivity(onboarding_activity);
        }

        mDbOpenHelper = new DictionaryOpenHelper(this);
        loadRecentSearch();
    }

    private void loadRecentSearch() {
        mRecentSearchAdapter = new RecentSearchAdapter(this, null);
        mRecentSearch.setAdapter(mRecentSearchAdapter);

        getSupportLoaderManager().restartLoader(LOAD_RECENT_WORDS, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearch.setText("");
        getSupportLoaderManager().restartLoader(LOAD_RECENT_WORDS, null, this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                if (!getConnectivityStatus(MainActivity.this)){
                    Intent intent = new Intent(MainActivity.this, NoNetwork.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    mWord = mSearch.getText().toString().toLowerCase();
                    if(mWord.isEmpty()) {
                        startActivity(new Intent(MainActivity.this, NoData.class));
                    } else {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_WORD, mWord);
                        startActivity(intent);
                    }
                }
                break;
        }
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
                    finishAffinity();
                }
            })
            .setNegativeButton("No", null)
            .show();
    }

    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    public static void enableStrictMode() {
        if(BuildConfig.DEBUG) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader loader = null;
        if(id == LOAD_RECENT_WORDS) {
            loader = new CursorLoader(this) {
                @Override
                public Cursor loadInBackground() {
                    SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
                    final String[] wordColumns = {
                            WordEntry.COLUMN_WORD
                    };
                    final String wordOrderBy = WordEntry._ID + " DESC";
                    String selection = WordEntry.COLUMN_WORD_TYPE + " = ?";
                    String[] selectionArgs = {
                            WordEntry.WORD_TYPE_SEARCH
                    };

                    return db.query(true, WordEntry.TABLE_NAME, wordColumns, selection, selectionArgs, null, null, wordOrderBy, "8");
                }
            };

        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        if(id == LOAD_RECENT_WORDS) {
            mRecentSearchAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        int id = loader.getId();
        if(id == LOAD_RECENT_WORDS) {
            mRecentSearchAdapter.changeCursor(null);
        }
    }
}
