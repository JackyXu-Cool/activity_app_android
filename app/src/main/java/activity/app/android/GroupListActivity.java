package activity.app.android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import activity.app.android.model.Group;
import activity.app.android.util.GroupListAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class GroupListActivity extends AppCompatActivity {

    TextView school;
    ListView list;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        app = ((MyApplication) this.getApplication()).app;

        // Set up toolbars
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.returnbutton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup the school name (title of this page)
        school = findViewById(R.id.schoolName);
        school.setText(getIntent().getStringExtra("school_name"));

        // Set up the group display
        list = findViewById(R.id.groupList);
        ArrayList<Group> groups = new ArrayList<>();
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), "clubM_data")
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm backgroundThreadRealm = Realm.getInstance(config);
        RealmResults<Group> fetchedGroup = backgroundThreadRealm.where(Group.class).findAll();
        for (Group g: fetchedGroup) {
            groups.add(g);
        }
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