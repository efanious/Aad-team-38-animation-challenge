package android.example.aad_team_38_animation_challenge.onlineDictionary;

import android.content.Context;
import android.example.aad_team_38_animation_challenge.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

public class DictionaryAdapter extends BaseAdapter {
    private List<LexicalEntry> lexicalEntries;
    private Context context;

    public DictionaryAdapter(Context context, List<LexicalEntry> lexicalEntries){
        this.context = context;
        this.lexicalEntries = lexicalEntries;
    }

    public void setLexicalEntries(List<LexicalEntry> lexicalEntries){
        this.lexicalEntries = lexicalEntries;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lexicalEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return lexicalEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.words_list, parent, false
            );
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LexicalEntry lexicalEntry = (LexicalEntry) getItem(position);
        Entry entry = lexicalEntry.getEntries().get(0);

        Sense sense = entry.getSenses().get(0);
        viewHolder.definition.setText(sense.getDefinitions().get(0));
        viewHolder.lexicalCategory.setText(lexicalEntry.getLexicalCategory());
        return convertView;
    }

    class ViewHolder{
        private TextView lexicalCategory;
        private TextView definition;

        public ViewHolder(View view){
            this.lexicalCategory = view.findViewById(R.id.lexical_category);
            this.definition = view.findViewById(R.id.definition);
        }
    }
}
