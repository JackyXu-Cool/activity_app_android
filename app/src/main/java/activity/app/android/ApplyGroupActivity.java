package activity.app.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.bson.Document;
import org.bson.types.ObjectId;

import activity.app.android.model.Group;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.sync.SyncConfiguration;

public class ApplyGroupActivity extends AppCompatActivity{
    // Declare useful elemnt from the page
    Button displayDocs;
    Button displayHighlights;
    Button displayMoments;
    ImageView avatar;
    ViewFlipper slidingContentSelector;
    LinearLayout progressView;
    LinearLayout groupContent;
    LinearLayout slidingButtons;
    SlidingUpPanelLayout panel;
    App app;
    MaterialButton applyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_group);
        app = ((MyApplication) this.getApplication()).app;

        // Define view elements
        groupContent = findViewById(R.id.group);
        progressView = findViewById(R.id.progressbar_view);
        slidingButtons = findViewById(R.id.slidingButtons);
        panel = findViewById(R.id.slidingpanel_group_whole);
        applyBtn = findViewById(R.id.apply);

        // Set up toolbars
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.returnbutton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Instantiate the buttons on sliding panel
        displayDocs = (Button)(slidingButtons.getChildAt(0));
        displayHighlights = (Button)(slidingButtons.getChildAt(1));
        displayMoments = (Button)(slidingButtons.getChildAt(2));
        slidingContentSelector = findViewById(R.id.slidingDisplaySelector);
        avatar = findViewById(R.id.avatar);

        displayDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!displayDocs.isSelected()) {
                    slidingContentSelector.setDisplayedChild(0);
                    displayDocs.setSelected(true);
                    displayHighlights.setSelected(false);
                    displayMoments.setSelected(false);
                }
            }
        });

        displayHighlights.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!displayHighlights.isSelected()) {
                    slidingContentSelector.setDisplayedChild(1);
                    displayDocs.setSelected(false);
                    displayHighlights.setSelected(true);
                    displayMoments.setSelected(false);
                }
            }
        });

        displayMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!displayMoments.isSelected()) {
                    slidingContentSelector.setDisplayedChild(2);
                    displayDocs.setSelected(false);
                    displayHighlights.setSelected(false);
                    displayMoments.setSelected(true);
                }
            }
        });

        // Set up sliding button groups functionality
        sliding_buttongroups_setup();
        slidingContentSelector.setDisplayedChild(1);

        // check if the button needs to be disabled
        checkUserhasJoined();

        new Task().execute();
    }

    // Fetch data from Bmob database and then apply those data to views on this page
    class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            progressView.setVisibility(View.VISIBLE);
            groupContent.setVisibility(View.GONE);
            avatar.setVisibility(View.GONE);

            setGroupCover(getIntent().getStringExtra("group_url"));
            setGroupIntro(getIntent().getStringExtra("group_intro"));
            setGroupCreatedDate(getIntent().getStringExtra("group_date"));
            setGroupName(getIntent().getStringExtra("group_name"));
            setGroupSize(getIntent().getIntExtra("group_size", 0));
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressView.setVisibility(View.GONE);
            groupContent.setVisibility(View.VISIBLE);
            avatar.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            while (avatar.getDrawable() == null) {
                try {
                    panel.setTouchEnabled(false);
                    Thread.sleep(1000);
                    panel.setTouchEnabled(true);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        // Private method that set the created date of this group
        private void setGroupCreatedDate(String date) {
            TextView txtCreatedDate = findViewById(R.id.groupDateText);
            txtCreatedDate.setText("Created on：" + date);
        }

        // Private method that set up group's cover
        private void setGroupCover(String url){
            Glide.with(ApplyGroupActivity.this).load(url).
                    apply(new RequestOptions().override(600, 400)).centerCrop().into(avatar);
        }

        // Private method that set up group's introduction
        private void setGroupIntro(String intro){
            TextView txtGroupIntro = findViewById(R.id.group_intro_content);
            txtGroupIntro.setText(intro);
        }

        // private method that set up group name
        private void setGroupName(String name) {
            TextView txtGroupName = findViewById(R.id.group_name);
            txtGroupName.setText(name);
        }

        // private method that set up group size
        private void setGroupSize(int size) {
            TextView txtGroupSize = findViewById(R.id.groupNumberTxt);
            txtGroupSize.setText("Number of members: " + size);
        }
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

    // Basic set up for the button groups on the top of the sliding panel
    private void sliding_buttongroups_setup() {
        String[] buttonNames = getResources().getStringArray(R.array.group_info_buttons);
        displayDocs.setText(buttonNames[0]);
        displayHighlights.setText(buttonNames[1]);
        displayHighlights.setSelected(true);
        displayMoments.setText(buttonNames[2]);
    }

    // Disable the apply button if the user is already in this group
    public void checkUserhasJoined() {
        SyncConfiguration config = new SyncConfiguration.Builder(
                app.currentUser(),
                "clubM_data")
                .allowWritesOnUiThread(true)
                .build();
        Realm backgroundThreadRealm = Realm.getInstance(config);
        Group group = backgroundThreadRealm.where(Group.class).equalTo("_id", getIntent().getStringExtra("group_id")).findFirst();
        if (group.getGroupMembers().contains(new ObjectId(app.currentUser().getId()))) {
            applyBtn.setEnabled(false);
            applyBtn.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }
    }

    // User join the group
    public void joinGroup(View view) {
        String group_id = getIntent().getStringExtra("group_id");
        // update group list in users collection
        MongoClient mongoClient = app.currentUser().getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("clubM");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
        mongoCollection.updateOne(new Document("user-id-field", app.currentUser().getId()), new Document("$push", new Document("groups", group_id))).getAsync(t -> {
            if (t.isSuccess()) {
                long count = t.get().getModifiedCount();
                if (count == 1) {
                    Log.v("Success", "successfully updated a document.");
                } else {
                    Log.v("ERROR", "did not update a document.");
                }
                finish();
            } else {
                Log.e("ERROR", "Fail to update information");
            }
        });
        // update members list in group collection
        SyncConfiguration config = new SyncConfiguration.Builder(
                app.currentUser(),
                "clubM_data")
                .allowWritesOnUiThread(true)
                .build();
        Realm backgroundThreadRealm = Realm.getInstance(config);
        backgroundThreadRealm.executeTransaction(r -> {
            Group chosenGroup = r.where(Group.class).equalTo("_id", group_id).findFirst();
            chosenGroup.addMembers(new ObjectId(app.currentUser().getId()));
        });
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}