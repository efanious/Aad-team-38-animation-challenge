package android.example.aad_team_38_animation_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView wordTitleTextview, wordDefinitionTextview;
    ImageView mCloseButton;

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
        mCloseButton = findViewById(R.id.close_button);

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
    }

    @Override
    public void onBackPressed(){
        closeDetailActivity(mCloseButton);
    }

    public void closeDetailActivity(View view) {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        finish();
    }
}
