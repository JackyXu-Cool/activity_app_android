package activity.app.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomActivity  extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        username = findViewById(R.id.usernameTxt);
        password = findViewById(R.id.passwordTxt);
    }

    public void changeToSelectPage(View view) {
        Intent intent = new Intent(this, SelectSchoolActivity.class);
        startActivity(intent);
    }

    public void loginOperation(View view) {
        if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter the required field", Toast.LENGTH_SHORT).show();
        } else {
            String usernameTxt = username.getText().toString().trim();
            String passwordTxt = password.getText().toString().trim();

            // TODO: Encrypt the password and store the user information into the bmob database

        }
    }
}
