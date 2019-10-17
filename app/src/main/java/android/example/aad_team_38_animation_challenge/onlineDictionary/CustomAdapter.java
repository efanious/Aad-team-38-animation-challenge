package android.example.aad_team_38_animation_challenge.onlineDictionary;

import android.content.Context;
import android.example.aad_team_38_animation_challenge.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    private List<Words> mWords;
    private Context mContext;

    public CustomAdapter(Context context, List<Words> mWords){
        this.mContext = context;
        this.mWords = mWords;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.words_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.txtWord.setText(mWords.get(position).getWord());


    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        TextView txtWord;

        public CustomViewHolder(@NonNull View itemView) {

            super(itemView);
            mView = itemView;

            txtWord = mView.findViewById(R.id.txtWord);
        }
    }
}
