package android.example.aad_team_38_animation_challenge;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import static android.example.aad_team_38_animation_challenge.DictionaryDatabaseContract.*;

public class RecentSearchAdapter extends BaseAdapter {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    private Cursor mCursor;
    private int mWordPos;
    private int mIdPos;

    public RecentSearchAdapter(Context context, Cursor cursor){
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

    public void changeCursor(Cursor cursor) {
        if(mCursor != null && mCursor != cursor)
            mCursor.close();

        mCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        // return mCursor.get;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.word_row, parent, false
            );
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        mCursor.moveToPosition(position);
        viewHolder.word.setText(mCursor.getString(mWordPos));
        return convertView;
    }

    class ViewHolder{
        private TextView word;

        public ViewHolder(View view){
            this.word = view.findViewById(R.id.word);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_WORD, word.getText().toString());
                    mContext.startActivity(intent);
                }
            });
        }
    }



}
