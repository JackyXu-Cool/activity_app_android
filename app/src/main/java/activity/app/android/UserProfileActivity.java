package activity.app.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.bson.Document;

import java.io.File;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.mongodb.App;

public class UserProfileActivity extends AppCompatActivity {

    App app;

    TextView usernameDisplay;
    CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        usernameDisplay = findViewById(R.id.username_profile);
        profileImage = findViewById(R.id.user_avatar_on_profile);

        app = ((MyApplication) this.getApplication()).app;

        setUpInformation();
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
        Document customData = app.currentUser().getCustomData();
        String username = customData.getString("username");
        usernameDisplay.setText(username);
        String url = customData.getString("path");
        Glide.with(UserProfileActivity.this).load(url).centerCrop().into(profileImage);
    }
}

