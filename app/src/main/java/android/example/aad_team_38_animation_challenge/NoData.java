package android.example.aad_team_38_animation_challenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoData extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_data);

        findViewById(R.id.retryNoData).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(NoData.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}
