package android.example.aad_team_38_animation_challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.aad_team_38_animation_challenge.DictionaryDatabaseContract.WordEntry;
import android.example.aad_team_38_animation_challenge.onlineDictionary.DictionaryAdapter;
import android.example.aad_team_38_animation_challenge.onlineDictionary.MainApplication;
import android.example.aad_team_38_animation_challenge.onlineDictionary.Model.LexicalEntries;
import android.example.aad_team_38_animation_challenge.onlineDictionary.Model.Results;
import android.example.aad_team_38_animation_challenge.onlineDictionary.Model.Root;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements WordAdapter.OnWordListener, Callback<Root> {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_WORD = "android.example.aad_team_38_animation_challenge.WORD";

    private String mWord;
    private DictionaryAdapter mWordAdapter;

    private ListView mWordDefinitions;
    private CardView mBodyContainer, mTitleContainer;
    public TextView mWordTitle;
    public ProgressDialog progressDialog;
    private Menu menu;
    private static DictionaryOpenHelper mDictionaryOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        enableStrictMode();

        Intent intent = getIntent();
        mWord = intent.getStringExtra(EXTRA_WORD);

        Log.i(TAG, "DetailActivity loaded with word " + mWord);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mWordTitle = findViewById(R.id.word_title);
        mBodyContainer = findViewById(R.id.body_container);
        mTitleContainer = findViewById(R.id.title_container);

        mTitleContainer.setVisibility(View.GONE);
        mBodyContainer.setVisibility(View.GONE);

        mWord = mWord.toLowerCase();
        String capitalizedWord = String.valueOf(mWord.charAt(0)).toUpperCase() + mWord.subSequence(1, mWord.length());

        mWordTitle.setText(capitalizedWord);

        mWordDefinitions = findViewById(R.id.word_definitions);
        mWordAdapter = new DictionaryAdapter(this, Collections.<LexicalEntries>emptyList());
        mWordDefinitions.setAdapter(mWordAdapter);

        mDictionaryOpenHelper = new DictionaryOpenHelper(DetailActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWordDefinition();
    }

    private void storeRecentSearch() {

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                SQLiteDatabase db = mDictionaryOpenHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put(WordEntry.COLUMN_WORD, mWord);
                values.put(WordEntry.COLUMN_WORD_TYPE, WordEntry.WORD_TYPE_SEARCH);
                long newRowId = db.insert(WordEntry.TABLE_NAME, null, values);

                Log.i(TAG, "Searched word inserted into db " + mWord + ": " + newRowId);
                return null;
            }
        };
        task.execute();
    }

    private void getWordDefinition() {
        MainApplication.apiManager.getDictionaryEntries(mWord, this);
    }

    @Override
    public void onBackPressed(){
        closeDetailActivity();
    }

    public void closeDetailActivity() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_favourite:
                AsyncTask task;
                if(item.isChecked()) {
                    task = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            SQLiteDatabase db = mDictionaryOpenHelper.getReadableDatabase();
                            String where = WordEntry.COLUMN_WORD + " = ?" +
                                    " AND " + WordEntry.COLUMN_WORD_TYPE + " = ?";
                            String[] whereArgs = {
                                    mWord,
                                    WordEntry.WORD_TYPE_FAVOURITE
                            };
                            db.delete(WordEntry.TABLE_NAME, where, whereArgs);

                            Log.i(TAG, "Favourite word removed from db " + mWord);
                            return null;
                        }
                    };
                } else {
                    task = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            SQLiteDatabase db = mDictionaryOpenHelper.getReadableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(WordEntry.COLUMN_WORD, mWord);
                            values.put(WordEntry.COLUMN_WORD_TYPE, WordEntry.WORD_TYPE_FAVOURITE);
                            long newRowId = db.insert(WordEntry.TABLE_NAME, null, values);

                            Log.i(TAG, "Favourite word inserted into db " + mWord + ": " + newRowId);
                            return null;
                        }
                    };
                }
                toggleFavouriteWord(!item.isChecked());
                task.execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean toggleFavouriteWord(boolean checked) {

        Log.i(TAG, "Toggle " + checked);
        MenuItem item = this.menu.findItem(R.id.action_favourite);
        if(checked) {
            item.setIcon(R.drawable.ic_action_favourite_fill);
            item.setChecked(true);
        } else {
            item.setIcon(R.drawable.ic_action_favourite);
            item.setChecked(false);
        }
        return true;
    }

    @Override
    public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
        progressDialog.hide();

        if (response.isSuccessful()) {
            Root root = response.body();
            assert root != null;
            //Toast.makeText(this, "Result: " + response.body().getResults().toString(), Toast.LENGTH_LONG).show();
            Results dictionaryResult = root.getResults().get(0);

            mWordAdapter.setLexicalEntries(dictionaryResult.getLexicalEntries());

            mTitleContainer.setVisibility(View.VISIBLE);
            mBodyContainer.setVisibility(View.VISIBLE);

            storeRecentSearch();
            setupFavourite();
        } else {
            switch (response.code()) {
                case 403:
                    try {
                        Toast.makeText(this, "" + response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                case 400:
                case 404:
                    startActivity(new Intent(DetailActivity.this, NoData.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    break;
            }
        }

    }

    private void setupFavourite() {
        MyTask task = new MyTask(this);
        task.execute();
    }

    @Override
    public void onFailure(@NonNull Call<Root> call, Throwable t) {
        progressDialog.hide();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemClicked(int position) {
        // Do nothing
    }

    @Override
    protected void onDestroy() {
        progressDialog.dismiss();
        mDictionaryOpenHelper.close();
        super.onDestroy();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    private static class MyTask extends AsyncTask<Void, Void, Integer> {

        private WeakReference<DetailActivity> activityReference;

        // only retain a weak reference to the activity
        MyTask(DetailActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            DetailActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return 0;

            SQLiteDatabase db = mDictionaryOpenHelper.getReadableDatabase();
            final String[] wordColumns = {
                    WordEntry._ID
            };
            String selection = WordEntry.COLUMN_WORD_TYPE + " = ? " +
                    " AND " + WordEntry.COLUMN_WORD + " = ?";
            String[] selectionArgs = {
                    WordEntry.WORD_TYPE_FAVOURITE,
                    activity.mWord
            };

            Cursor cursor = db.query(WordEntry.TABLE_NAME, wordColumns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            return count;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.i(TAG, "In favourites " + integer);

            DetailActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            if(integer == 0) {
                activity.toggleFavouriteWord(false);
            } else {
                // Is in favourites
                activity.toggleFavouriteWord(true);
            }
        }
    }

}
