package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import activity.app.android.util.AESCrypt;
import activity.app.android.util.PathConverter;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;


public class RegisterActivity extends AppCompatActivity {

    int SELECT_PHOTO = 1;
    Uri uri;
    ImageView preview;

    EditText usernameTxt;
    EditText passwordTxt;

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Define components
        preview = findViewById(R.id.user_avatar_preview);
        usernameTxt = findViewById(R.id.emailTxt_register);
        passwordTxt = findViewById(R.id.passwordTxt_register);
        
        app = new App(new AppConfiguration.Builder("activity_app-znbjb").build());
    }

    // TODO: Connect avatar file path with user
    // TODO: Connect activity list and affiliated groups to user
    public void registerOperation(View view) {
        if (usernameTxt.getText().toString().trim().equals("") || passwordTxt.getText().toString().trim().equals("") || uri == null) {
            Toast.makeText(this, "Enter the required field", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = usernameTxt.getText().toString();
        String hashedPassword = "";
        try {
            hashedPassword = AESCrypt.encrypt(passwordTxt.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Please enter the password in correct format", Toast.LENGTH_SHORT).show();
        }

        // path of the avatar
        String path = PathConverter.convertMediaUriToPath(this, uri);

        // register in the realm database
        app.getEmailPassword().registerUserAsync(email, hashedPassword, it->{
            if (it.isSuccess()) {
                // Log in the newly created user and open user profile page
                loginNewUser();
            } else {
                Toast.makeText(this, it.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginNewUser() {
        String email = usernameTxt.getText().toString();
        String hashedPassword = "";
        try {
            hashedPassword = AESCrypt.encrypt(passwordTxt.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "There is something with the password", Toast.LENGTH_SHORT).show();
        }
        Credentials credentials = Credentials.emailPassword(email, hashedPassword);
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                // Automatically open user profile page
                Intent intent = new Intent(this, UserProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Successfuly register a new user but fail to log in", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void switchToLogin(View view) {
        Intent intent = new Intent(this, WelcomActivity.class);
        startActivity(intent);
    }

    public void selectFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // startActivityForResult() defines that when this activity exits, onActivityResult() will be called
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                preview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}