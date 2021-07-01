package activity.app.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import activity.app.android.util.AESCrypt;
import activity.app.android.util.PathConverter;
import io.realm.RealmList;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class RegisterActivity extends AppCompatActivity {

    int SELECT_PHOTO = 1;
    Uri uri;
    ImageView preview;

    EditText emailTxt;
    EditText passwordTxt;
    EditText usernameTxt;

    Bitmap avatar;

    App app;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Define components
        preview = findViewById(R.id.user_avatar_preview);
        emailTxt = findViewById(R.id.emailTxt_register);
        passwordTxt = findViewById(R.id.passwordTxt_register);
        usernameTxt = findViewById(R.id.usernameTxt_register);

        app = ((MyApplication) this.getApplication()).app;
    }

    public void registerOperation(View view) {
        if (emailTxt.getText().toString().trim().equals("") || passwordTxt.getText().toString().trim().equals("") || uri == null) {
            Toast.makeText(this, "Enter the required field", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = emailTxt.getText().toString();
        String hashedPassword = "";
        try {
            hashedPassword = AESCrypt.encrypt(passwordTxt.getText().toString().trim());
        } catch (Exception e) {
            Toast.makeText(this, "Please enter the password in correct format", Toast.LENGTH_SHORT).show();
        }

        // Get username
        String username = usernameTxt.getText().toString();

        // register in the realm database
        app.getEmailPassword().registerUserAsync(email, hashedPassword, it->{
            if (it.isSuccess()) {
                // Upload the image to firebase storage
                StorageReference storageRef = storage.getReference();
                StorageReference avatarRef = storageRef.child(UUID.randomUUID().toString() + ".jpg");
                String path = PathConverter.convertMediaUriToPath(this, uri);
                try {
                    InputStream stream = new FileInputStream(new File(path));
                    UploadTask uploadTask = avatarRef.putStream(stream);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("ERROR", "Fail to upload to firebase");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }
                                    return avatarRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        loginNewUser(downloadUri.toString(), username);
                                    } else {
                                        Log.e("ERROR", "fail to get url");
                                    }
                                }
                            });
                        }
                    });
                } catch(FileNotFoundException e) {
                    Log.e("ERROR", e.getMessage());
                }
            } else {
                Toast.makeText(this, it.getError().getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginNewUser(String path, String username) {
        String email = emailTxt.getText().toString();
        String hashedPassword = "";
        try {
            hashedPassword = AESCrypt.encrypt(passwordTxt.getText().toString().trim());
        } catch (Exception e) {
            Toast.makeText(this, "There is something with the password", Toast.LENGTH_SHORT).show();
        }
        Credentials credentials = Credentials.emailPassword(email, hashedPassword);
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                MongoClient mongoClient = app.currentUser().getMongoClient("mongodb-atlas");
                MongoDatabase mongoDatabase = mongoClient.getDatabase("clubM");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
                mongoCollection.insertOne(
                        new Document("user-id-field", app.currentUser().getId()).append("path", path).append("username", username).append("school", "")
                                .append("activities", new RealmList<>()).append("groups", new RealmList<>())).getAsync(r -> {
                            if (r.isSuccess()) {
                                Log.v("AUTH", "Successfully insert custom user data");
                            } else {
                                Log.e("EXAMPLE", "Unable to insert custom user data. Error: " + r.getError());
                            }
                });
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
                avatar = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                preview.setImageBitmap(avatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}