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


import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.mongodb.App;

public class UserProfileActivity extends AppCompatActivity {

    App app;

    TextView usernameDisplay;
    CircleImageView profileImage; // Set up toolbars
    ImageButton schoolListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        usernameDisplay = findViewById(R.id.username_profile);
        profileImage = findViewById(R.id.user_avatar_on_profile);
        schoolListBtn = findViewById(R.id.schoolListBtn);

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
                String username = customData.getString("username");
                usernameDisplay.setText(username);
                String url = customData.getString("path");
                Glide.with(UserProfileActivity.this).load(url).centerCrop().into(profileImage);
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

