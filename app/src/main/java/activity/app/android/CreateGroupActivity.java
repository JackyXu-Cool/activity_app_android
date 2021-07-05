package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class CreateGroupActivity extends AppCompatActivity {

    int SELECT_PHOTO = 1;
    Uri uri;
    ImageView preview;
    Bitmap groupImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        preview = findViewById(R.id.group_image_preview);
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

    // TODO: save group info to realm and group image to firebase
}