package activity.app.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void changeToSelectPage(View view) {
        Intent intent = new Intent(this, SelectSchoolActivity.class);
        startActivity(intent);
    }
}
