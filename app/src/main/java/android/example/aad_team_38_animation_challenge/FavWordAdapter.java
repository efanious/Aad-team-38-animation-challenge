package android.example.aad_team_38_animation_challenge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import static android.example.aad_team_38_animation_challenge.DictionaryDatabaseContract.WordEntry;

public class FavWordAdapter extends RecyclerView.Adapter<FavWordAdapter.ViewHolder> {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    private Cursor mCursor;
    private int mWordPos;
    private int mIdPos;
    private int id;
    private DictionaryOpenHelper mDicOpenHelper;

    FavWordAdapter(Context context, Cursor cursor){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDicOpenHelper = new DictionaryOpenHelper(mContext);

        mCursor = cursor;
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor == null)
            return;
        //Get column indexes from mCursor
        mWordPos = mCursor.getColumnIndex(WordEntry.COLUMN_WORD);
        mIdPos = mCursor.getColumnIndex(WordEntry._ID);
    }

    void changeCursor(Cursor cursor) {
        if(mCursor != null && mCursor != cursor)
            mCursor.close();

        mCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_word_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.word.setText(mCursor.getString(mWordPos));
    }

    @Override
    public long getItemId(int position) {
        // return mCursor.get;
        return position;
    }

    @Override
    public int getItemCount() {
        if(mCursor == null)
            return 0;
        return mCursor.getCount();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView word;
        private ImageButton deleteButton;

        ViewHolder(View view){
            super(view);
            this.word = view.findViewById(R.id.word);
            this.deleteButton = view.findViewById(R.id.btn_delete_fav_word);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_WORD, word.getText().toString());
                    mContext.startActivity(intent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteRow(Integer.valueOf(mCursor.getString(mIdPos)), word.getText().toString());
                }
            });

            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.list_slide_in_left);
            view.setAnimation(animation);
        }

        void deleteRow(final int rowId, String word){
            new AlertDialog.Builder(mContext)
                    .setTitle("Done?")
                    .setIcon(R.drawable.close_icon)
                    .setMessage("Are you sure you want to delete \"" + word + "\"")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteFromRow(rowId);
                            Intent intent = new Intent(mContext, FavoriteWord.class);
                            mContext.startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        void deleteFromRow(int id) {
            String selection = WordEntry._ID + " = ?";
            String[] selectionArgs = {Integer.toString(id)};
            SQLiteDatabase db = mDicOpenHelper.getWritableDatabase();
            db.delete(WordEntry.TABLE_NAME, selection, selectionArgs);
        }
    }

}
