package android.example.aad_team_38_animation_challenge;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.aad_team_38_animation_challenge.DictionaryDatabaseContract.WordEntry;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.view.View;

public class FavoriteWord extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_FAV_WORDS = 0;
    private RecyclerView mRecyclerView;
    private DictionaryOpenHelper mDicOpenHelper;
    private Cursor mWordCursor;
    private FavWordAdapter mFavWordRecyclerAdapter;
    private LayoutManager mWordsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_word);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Favorites");

        mDicOpenHelper = new DictionaryOpenHelper(this);

        getLoaderManager().initLoader(LOADER_FAV_WORDS, null, this);

        mRecyclerView = findViewById(R.id.rv_fav_words);
        mWordsLayoutManager = new LinearLayoutManager(this);
        mFavWordRecyclerAdapter = new FavWordAdapter(this, null);
        mRecyclerView.setLayoutManager(mWordsLayoutManager);
        mRecyclerView.setAdapter(mFavWordRecyclerAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        CursorLoader loader = null;
        if(id == LOADER_FAV_WORDS) loader = createLoaderNotes();
        return loader;
    }

    private CursorLoader createLoaderNotes() {
        return new CursorLoader(this){
            @Override
            public Cursor loadInBackground() {
                SQLiteDatabase db =mDicOpenHelper.getReadableDatabase();

                String selection = WordEntry.COLUMN_WORD_TYPE + " = ?";
                String[] selectionArgs = {WordEntry.WORD_TYPE_FAVOURITE};

                final String[] columns = {WordEntry.COLUMN_WORD};
                final String wordOrderBy = WordEntry._ID;
                return db.query(WordEntry.TABLE_NAME, columns, selection,
                        selectionArgs, null, null, wordOrderBy); }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == LOADER_FAV_WORDS) mFavWordRecyclerAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == LOADER_FAV_WORDS){
            if(mWordCursor != null) mWordCursor.close();
        }
    }

}
