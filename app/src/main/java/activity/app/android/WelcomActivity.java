package activity.app.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import activity.app.android.util.AESCrypt;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;

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

        // If want to delete realm file, uninstall the app
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
