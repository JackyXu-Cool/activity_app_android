package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectSchoolActivity extends AppCompatActivity {

    String[] schoolArray = {"Boston University","Georgia Institute of Technology",
    "Northwestern University", "University of Wisconsin", "Standford University", "University of Washington",
            "Syracuse University"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.single_item, R.id.listViewItem, schoolArray);
        listView = (ListView) findViewById(R.id.schoolListView);
        listView.setAdapter(adapter);
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