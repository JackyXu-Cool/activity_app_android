package activity.app.android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import activity.app.android.model.Group;
import activity.app.android.util.GroupListAdapter;

public class GroupListActivity extends AppCompatActivity {

    TextView school;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        // Set up toolbars
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.returnbutton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup the school name (title of this page)
        school = findViewById(R.id.schoolName);
        school.setText(getIntent().getStringExtra("school_name"));

        // TODO: set up group list with real group data
        // TODO: Adjust the toolbar margin (it's too big right now)
        list = findViewById(R.id.groupList);
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group("CSGO TEAM", "A group for playing csgo", "https://cdn.akamai.steamstatic.com/steam/apps/730/capsule_616x353.jpg?t=1612812939"));
        GroupListAdapter adapter = new GroupListAdapter(this, groups);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle event for "cancel" button: return to the previous page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}