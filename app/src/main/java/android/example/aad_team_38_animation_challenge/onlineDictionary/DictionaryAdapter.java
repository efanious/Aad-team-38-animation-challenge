package android.example.aad_team_38_animation_challenge.onlineDictionary;

import android.content.Context;
import android.example.aad_team_38_animation_challenge.R;
import android.example.aad_team_38_animation_challenge.onlineDictionary.Model.Entries;
import android.example.aad_team_38_animation_challenge.onlineDictionary.Model.LexicalEntries;
import android.example.aad_team_38_animation_challenge.onlineDictionary.Model.Senses;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

public class DictionaryAdapter extends BaseAdapter {
    private List<LexicalEntries> lexicalEntries;
    private Context context;

    public DictionaryAdapter(Context context, List<LexicalEntries> lexicalEntries){
        this.context = context;
        this.lexicalEntries = lexicalEntries;
    }

    public void setLexicalEntries(List<LexicalEntries> lexicalEntries){
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
                    R.layout.word_definition_item, parent, false
            );
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LexicalEntries lexicalEntry = (LexicalEntries) getItem(position);
        Entries entry = lexicalEntry.getEntries().get(0);

        Senses sense = entry.getSenses().get(0);
        viewHolder.definition.setText(sense.getDefinitions().get(0));
        viewHolder.lexicalCategory.setText("'" + lexicalEntry.getLexicalCategory().getText().toLowerCase() + "'");
        return convertView;
    }

    class ViewHolder{
        private TextView lexicalCategory;
        private TextView definition;

        public ViewHolder(View view){
            this.lexicalCategory = view.findViewById(R.id.lexical_category);
            this.definition = view.findViewById(R.id.word_definition);
        }
    }
}
