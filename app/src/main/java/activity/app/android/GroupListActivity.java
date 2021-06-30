package activity.app.android;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import activity.app.android.model.Group;
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

    // Private class groupListAdapter
    private class GroupListAdapter implements ListAdapter {
        ArrayList<Group> groupList;
        Context context;
        public GroupListAdapter(Context context, ArrayList<Group> groupList) {
            this.groupList = groupList;
            this.context=context;
        }
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }
        @Override
        public boolean isEnabled(int position) {
            return true;
        }
        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }
        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }
        @Override
        public int getCount() {
            return groupList.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public boolean hasStableIds() {
            return false;
        }@Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public int getViewTypeCount() {
            return groupList.size();
        }
        @Override
        public boolean isEmpty() {
            return false;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Group group = groupList.get(position);
            if(convertView==null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ApplyGroupActivity.class);
                        intent.putExtra("group_intro", group.getGroupIntroduction());
                        intent.putExtra("group_url", group.getCoverURL());
                        intent.putExtra("group_name", group.getGroupName());
                        intent.putExtra("group_size", group.getGroupMembers().size());
                        intent.putExtra("group_id", group.getId());
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                        intent.putExtra("group_date", dateFormat.format(group.getCreatedDate()));
                        startActivity(intent);
                    }
                });
                TextView title = convertView.findViewById(R.id.group_title);
                ImageView img = convertView.findViewById(R.id.group_image);
                title.setText(group.getGroupName());

                Glide.with(context)
                        .load(group.getCoverURL())
                        .into(img);
            }
            return convertView;
        }
    }
}