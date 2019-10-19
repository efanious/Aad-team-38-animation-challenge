package android.example.aad_team_38_animation_challenge;

import android.provider.BaseColumns;

public final class DictionaryDatabaseContract {

    private DictionaryDatabaseContract() {}

    public static final class WordEntry implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_WORD = "word";
        public static final String COLUMN_WORD_TYPE = "type";

        //CREATE INDEX words_index1 ON words (word)
        public static final String INDEX1 = TABLE_NAME + "_index1";

        public static final String SQL_CREATE_INDEX1 = "CREATE INDEX " +
                INDEX1 + " ON " + TABLE_NAME + "(" + COLUMN_WORD + ")";
        public static final String WORD_TYPE_SEARCH = "SEARCH";
        public static final String WORD_TYPE_FAVOURITE = "FAVOURITE";

        public static final String getQName(String columnName) {
            return TABLE_NAME + "." + columnName;
        }

        //CREATE TABLE words (_id, word, type)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_WORD + " TEXT NOT NULL, " +
                        COLUMN_WORD_TYPE + " TEXT NOT NULL)";

    }
}
