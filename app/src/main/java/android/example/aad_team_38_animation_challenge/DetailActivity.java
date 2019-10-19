package android.example.aad_team_38_animation_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView wordTitleTextview, wordDefinitionTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String wordTitle = intent.getStringExtra("word_title");
        String wordDefinition = intent.getStringExtra("word_definition");

        // Get the views
        wordTitleTextview = findViewById(R.id.word_title);
        wordDefinitionTextview = findViewById(R.id.word_definition);

        // Bind the data
        wordTitleTextview.setText(wordTitle);
        wordDefinitionTextview.setText(wordDefinition);

        /*closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                finish();
            }
        });*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed(){
        closeDetailActivity();
    }

    public void closeDetailActivity() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
