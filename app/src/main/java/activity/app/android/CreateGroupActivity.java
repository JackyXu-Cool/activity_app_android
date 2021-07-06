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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import activity.app.android.model.Group;
import activity.app.android.util.PathConverter;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class CreateGroupActivity extends AppCompatActivity {

    int SELECT_PHOTO = 1;
    Uri uri;
    ImageView preview;
    Bitmap groupImage;
    App app;
    EditText groupNameTxt;
    EditText groupIntroTxt;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        preview = findViewById(R.id.group_image_preview);
        groupNameTxt = findViewById(R.id.group_name_edittext);
        groupIntroTxt = findViewById(R.id.group_intro_edittext);

        app = ((MyApplication) this.getApplication()).app;
    }

    public void backFromCreateGroupPage(View view) {
        finish();
    }

    public void selectGroupImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                groupImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                preview.setImageBitmap(groupImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createGroup(View view) {
        String groupName = groupNameTxt.getText().toString();
        String groupIntro = groupIntroTxt.getText().toString();
        String schoolName = app.currentUser().getCustomData().getString("school");

        // Upload the image to firebase storage
        StorageReference storageRef = storage.getReference();
        StorageReference avatarRef = storageRef.child("groups/" + UUID.randomUUID().toString() + ".jpg");
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
                                SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), "clubM_data")
                                                            .allowQueriesOnUiThread(true)
                                                            .allowWritesOnUiThread(true)
                                                            .build();
                                Realm.getInstanceAsync(config, new Realm.Callback() {
                                    @Override
                                    public void onSuccess(Realm realm) {
                                        realm.executeTransaction(r -> {
                                            r.insert(new Group(groupName, groupIntro, downloadUri.toString(), schoolName));
                                        });
                                        realm.close();
                                    }
                                });
                                finish();
                                Toast.makeText(CreateGroupActivity.this, "Successfully create a new group", Toast.LENGTH_SHORT).show();
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
    }
}