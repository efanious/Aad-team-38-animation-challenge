package android.example.aad_team_38_animation_challenge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyViewHolder> {
    private List<Word> wordList;
    public OnWordListener mOnWordListener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView word, definition;
        OnWordListener OnWordListener;


        public MyViewHolder(View view, OnWordListener onWordListener) {
            super(view);
            word = view.findViewById(R.id.word);
            definition = view.findViewById(R.id.word_definition);
            this.OnWordListener = onWordListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnWordListener.OnItemClicked(getAdapterPosition());
        }
    }

    public interface OnWordListener {
        void OnItemClicked(int position);
    }


    public WordAdapter(List<Word> wordList, OnWordListener onWordListener) {
        this.wordList = wordList;
        this.mOnWordListener = onWordListener;
    }


    @NonNull
    @Override
    public WordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_row, parent, false);
        return new MyViewHolder(itemView, mOnWordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.MyViewHolder holder, int position) {
        Word word = wordList.get(position);
        holder.word.setText(word.getWord());
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }
}
