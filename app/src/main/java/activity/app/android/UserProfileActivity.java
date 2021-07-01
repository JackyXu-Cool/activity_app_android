package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.bson.Document;
import org.bson.types.ObjectId;


import activity.app.android.model.Group;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class UserProfileActivity extends AppCompatActivity {

    App app;

    TextView usernameDisplay;
    CircleImageView profileImage; // Set up toolbars
    ImageButton schoolListBtn;
    TextView numberOfActivities;
    TextView numberOfGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        usernameDisplay = findViewById(R.id.username_profile);
        profileImage = findViewById(R.id.user_avatar_on_profile);
        schoolListBtn = findViewById(R.id.schoolListBtn);
        numberOfActivities = findViewById(R.id.number_of_activities_profile);
        numberOfGroups = findViewById(R.id.number_of_groups_profile);

        app = ((MyApplication) this.getApplication()).app;

        setUpInformation();
        schoolListBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                app.currentUser().refreshCustomData(task -> {
                    if (task.isSuccess()) {
                        String school = task.get().getString("school");
                        if (school == null || school.length() == 0) {
                            Intent intent = new Intent(UserProfileActivity.this, SelectSchoolActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(UserProfileActivity.this, GroupListActivity.class);
                            intent.putExtra("school_name", school);
                            startActivity(intent);
                        }
                    } else {
                        Log.e("ERROR", "Fail to fetch refresh data");
                    }
                });
            }
        });
    }

    public void logoutOperation(View view) {
        app.currentUser().logOutAsync(result -> {
            if (result.isSuccess()) {
                Log.v("AUTH", "successfully logged out");
                Intent intent = new Intent(this, WelcomActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setUpInformation() {
        app.currentUser().refreshCustomData(task -> {
            if (task.isSuccess()) {
                Document customData = task.get();
                // Set up username and load image
                String username = customData.getString("username");
                usernameDisplay.setText(username);
                String url = customData.getString("path");
                Glide.with(UserProfileActivity.this).load(url).centerCrop().into(profileImage);
                // Set up number of groups and activities
                int groupSize = customData.getList("groups", String.class).size();
                int activitySize = customData.getList("activities", String.class).size();
                // should convert the text into a string, otherwise it will call setText(int), where int is a resource id
                numberOfGroups.setText(groupSize + " ");
                numberOfActivities.setText(activitySize + " ");
            } else {
                Log.e("Error", "Fail to latest data");
            }
        });
    }

    public void editProfileOperation(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}

