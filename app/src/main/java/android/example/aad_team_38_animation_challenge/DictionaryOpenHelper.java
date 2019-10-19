package android.example.aad_team_38_animation_challenge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.example.aad_team_38_animation_challenge.DictionaryDatabaseContract.WordEntry;

import androidx.annotation.Nullable;

public class DictionaryOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Dictionary38.db";
    private static final int DATABASE_VERSION = 1;

    public DictionaryOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WordEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(WordEntry.SQL_CREATE_INDEX1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        if(oldV < 2) {
            // Execute new queries on db upgrade
        }
    }
}