package activity.app.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import activity.app.android.util.AESCrypt;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class WelcomActivity  extends AppCompatActivity {

    EditText emailAddress;
    EditText password;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        emailAddress = findViewById(R.id.emailTxt);
        password = findViewById(R.id.passwordTxt);
        app = ((MyApplication) this.getApplication()).app;
    }

    public void changeToSelectPage(View view) {
        Intent intent = new Intent(this, SelectSchoolActivity.class);
        startActivity(intent);
        finish();
    }

    public void switchToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginOperation(View view) {
        if (emailAddress.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter the required field", Toast.LENGTH_SHORT).show();
        } else {
            String emailTxt = emailAddress.getText().toString().trim();

            String hashedPassword = "";
            try {
                hashedPassword = AESCrypt.encrypt(password.getText().toString().trim());
            } catch (Exception e) {
                Toast.makeText(this, "There is something with the password", Toast.LENGTH_SHORT).show();
            }

            // TODO: fetch User from database based on username and compare the password
            Credentials credentials = Credentials.emailPassword(emailTxt, hashedPassword);
            app.loginAsync(credentials, it -> {
                if (it.isSuccess()) {
                    // Automatically open user profile page
                    Intent intent = new Intent(this, UserProfileActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Fail to log in", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
