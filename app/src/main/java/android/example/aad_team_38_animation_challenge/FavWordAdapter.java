package android.example.aad_team_38_animation_challenge;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.example.aad_team_38_animation_challenge.DictionaryDatabaseContract.WordEntry;

public class FavWordAdapter extends RecyclerView.Adapter<FavWordAdapter.ViewHolder> {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    private Cursor mCursor;
    private int mWordPos;
    private int mIdPos;

    FavWordAdapter(Context context, Cursor cursor){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_row, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder{
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

//            deleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // TODO: add the delete favorite word method here
//                }
//            });

            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
            view.setAnimation(animation);
        }
    }



}
