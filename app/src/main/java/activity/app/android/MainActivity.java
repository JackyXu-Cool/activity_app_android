package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Handle the event that start another activity
     * @param view start button
     */
    public void changeToGroupListPage(View view) {
        Intent intent = new Intent(this, ApplyGroupActivity.class);
        intent.putExtra("Group_ID", "587c3f1af4");
        startActivity(intent);
    }
}